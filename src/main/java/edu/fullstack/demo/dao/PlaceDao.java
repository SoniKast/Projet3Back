package edu.fullstack.demo.dao;

import edu.fullstack.demo.model.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlaceDao extends JpaRepository<Place, Integer> {

    Optional<Place> findByNumero(String numero);

    @Query("FROM Place p " +
            "JOIN p.reservations r " +
            "WHERE r.dateDebut <= :date " +
            "AND r.dateFin >= :date")
    List<Place> indisponibleLe(@Param("date") LocalDateTime date);

    @Query("FROM Place p1 WHERE p1 NOT IN " +
            "(FROM Place p2 " +
            "JOIN p2.reservations r " +
            "WHERE r.dateDebut <= :date " +
            "AND r.dateFin >= :date)")
    List<Place> disponibleLe(@Param("date") LocalDateTime date);

}
