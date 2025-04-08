package edu.fullstack.demo.controller;

import edu.fullstack.demo.dao.UtilisateurDao;
import edu.fullstack.demo.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/password")
public class PasswordResetController {

    @Autowired
    private UtilisateurDao utilisateurDao;  // Your User repository

    @Autowired
    private JavaMailSender emailSender;  // Spring's JavaMailSender

    @Autowired
    BCryptPasswordEncoder encoder;

    @PostMapping("/reset-request")
    public String requestPasswordReset(@RequestBody String email) {
        Optional<Utilisateur> utilisateur = utilisateurDao.findByEmail(email);

        if (utilisateur.isPresent()) {
            // Génerer un token aléatoire
            String resetToken = UUID.randomUUID().toString();

            utilisateur.get().setResetToken(resetToken);
            utilisateurDao.save(utilisateur.get());

            String resetUrl = "http://localhost:4200/changer-mdp?token=" + resetToken;

            // Envoyer l'email
            sendPasswordResetEmail(email, resetUrl);

            return "Email envoyé.";
        }

        return "Pas d'utilisateur trouvé avec cette adresse mail.";
    }

    private void sendPasswordResetEmail(String email, String resetUrl) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ballamoussa57@gmail.com");
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Pour réinitialiser votre mot de passe, cliquez sur le lien ci-dessous:\n" + resetUrl);

        emailSender.send(message);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Optional<Utilisateur> utilisateur = utilisateurDao.findByResetToken(token);

        if (utilisateur.isPresent()) {
            Utilisateur utilisateurActuel = utilisateur.get();
            utilisateurActuel.setPassword(encoder.encode(newPassword));  // Mot de passe encodé
            utilisateurActuel.setResetToken(null);  // Vider le token après l'avoir utilisé
            utilisateurDao.save(utilisateurActuel);
            return "Mot de passe réinitialisé.";
        }

        return "Token expiré ou invalide.";
    }
}
