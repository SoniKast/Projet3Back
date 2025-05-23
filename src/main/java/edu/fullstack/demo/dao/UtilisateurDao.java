package edu.fullstack.demo.dao;

import edu.fullstack.demo.model.Place;
import edu.fullstack.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur, Integer> {

    Optional<Utilisateur> findByEmail(String email);

    Optional<Utilisateur> findByResetToken(String token);
}
