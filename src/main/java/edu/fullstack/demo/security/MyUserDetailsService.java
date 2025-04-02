package edu.fullstack.demo.security;

import edu.fullstack.demo.dao.UtilisateurDao;
import edu.fullstack.demo.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UtilisateurDao utilisateurDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurDao
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur inexistant"));

        return new MyUserDetails(utilisateur);
    }
}
