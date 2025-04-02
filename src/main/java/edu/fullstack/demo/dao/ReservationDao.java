package edu.fullstack.demo.dao;

import edu.fullstack.demo.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationDao extends JpaRepository<Reservation, Integer> {
    List<Reservation> findAllByDateDebutLessThanEqualAndDateFinGreaterThanEqual(LocalDateTime debut, LocalDateTime fin);
}
