package edu.fullstack.demo.controller;

import edu.fullstack.demo.dao.UtilisateurDao;
import edu.fullstack.demo.model.Utilisateur;
import edu.fullstack.demo.security.JwtUtils;
import edu.fullstack.demo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class ConnexionController {

    @Autowired
    UtilisateurDao utilisateurDao;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("/inscription")
    public ResponseEntity<Utilisateur> inscription(@RequestBody Utilisateur utilisateur) {

        utilisateur.setPassword(encoder.encode(utilisateur.getPassword()));

        utilisateurDao.save(utilisateur);

        return new ResponseEntity<>(utilisateur, HttpStatus.NO_CONTENT);

    }

    @PostMapping("/inscription-mail")
    public ResponseEntity<String> sendInscriptionEmail(@RequestBody String email) {
        Optional<Utilisateur> existing = utilisateurDao.findByEmail(email);

        if (existing.isPresent()) {
            return new ResponseEntity<>("Email déjà utilisé.", HttpStatus.CONFLICT);
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setResetToken(UUID.randomUUID().toString()); // reuse resetToken
        utilisateurDao.save(utilisateur);

        // Construct link
        String lien = "http://localhost:4200/inscription?token=" + utilisateur.getResetToken();

        // Send email
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ballamoussa57@gmail.com");
            message.setTo(email);
            message.setSubject("Lien pour s'inscrire");
            message.setText(
                    "Confirmation d'inscription\n" +
                    "Cliquez sur ce lien pour finaliser votre inscription : " + lien
            );
            mailSender.send(message);
            return new ResponseEntity<>("Email envoyé.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur d'envoi d'email.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/valider-inscription")
    public ResponseEntity<String> validerInscription(@RequestBody String token) {
        Optional<Utilisateur> optionalUtilisateur = utilisateurDao.findByResetToken(token);

        if (optionalUtilisateur.isEmpty()) {
            return new ResponseEntity<>("Token invalide ou expiré", HttpStatus.BAD_REQUEST);
        }

        Utilisateur utilisateur = optionalUtilisateur.get();

        utilisateur.setPassword(encoder.encode("123")); // Default password
        utilisateur.setResetToken(null); // Clear token
        utilisateur.setRole("EMPLOYE"); // Default role or customizable

        utilisateurDao.save(utilisateur);

        return new ResponseEntity<>("Compte activé", HttpStatus.OK);
    }

    @PostMapping("/connexion")
    public ResponseEntity<String> connexion(@RequestBody Utilisateur utilisateur) {

        try {
            MyUserDetails userDetails = (MyUserDetails) authenticationProvider
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            utilisateur.getEmail(),
                            utilisateur.getPassword()))
                    .getPrincipal();

            return new ResponseEntity<>(jwtUtils.generateJwt(userDetails), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
