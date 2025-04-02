package edu.fullstack.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fullstack.demo.dao.ReservationDao;
import edu.fullstack.demo.model.Reservation;
import edu.fullstack.demo.model.Utilisateur;
import edu.fullstack.demo.security.IsEmploye;
import edu.fullstack.demo.security.IsPartenaire;
import edu.fullstack.demo.security.MyUserDetails;
import edu.fullstack.demo.view.AffichageReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
public class ReservationController {

    @Autowired
    protected ReservationDao ReservationDao;

    @GetMapping("/reservation/{id}")
    @JsonView(AffichageReservation.class)
    public ResponseEntity<Reservation> get(@PathVariable int id){

        Optional<Reservation> optionalReservation = ReservationDao.findById(id);

        if(optionalReservation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalReservation.get(), HttpStatus.OK);
    }

    @GetMapping("/reservations")
    @JsonView(AffichageReservation.class)
    public List<Reservation> getAll() {

        return ReservationDao.findAll();
    }

    @GetMapping("/reservation-at-date/{date}")
    @JsonView(AffichageReservation.class)
    public List<Reservation> getAllByDate(@PathVariable String date) {

        LocalDateTime dateTime = LocalDateTime.parse(date);

        return ReservationDao.findAllByDateDebutLessThanEqualAndDateFinGreaterThanEqual(dateTime, dateTime);
    }

    @PostMapping("/reservation")
    @JsonView(AffichageReservation.class)
    @IsEmploye
    public ResponseEntity<Reservation> create(@RequestBody Reservation Reservation){

        Reservation.setId(null);
        ReservationDao.save(Reservation);

        return new ResponseEntity<>(Reservation, HttpStatus.CREATED);
    }

    @PostMapping("/reservation-place-journee")
    @JsonView(AffichageReservation.class)
    @IsPartenaire
    public ResponseEntity<Reservation> createReservationJournee(
            @RequestBody Reservation Reservation,
            @AuthenticationPrincipal MyUserDetails userDetails){

        Reservation.setId(null);

        LocalDateTime dateDebut = LocalDateTime.now().withHour(8).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime dateFin = LocalDateTime.now().withHour(19);

        Reservation.setDateDebut(dateDebut);
        Reservation.setDateFin(dateFin);

        Utilisateur fauxUtilisateur = new Utilisateur();
        fauxUtilisateur.setId(1);

        Reservation.setUtilisateur(fauxUtilisateur);

        Reservation.setDateCreation(LocalDateTime.now());

        ReservationDao.save(Reservation);

        return new ResponseEntity<>(Reservation, HttpStatus.CREATED);
    }

    @PutMapping("/reservation/{id}")
    @IsPartenaire
    public ResponseEntity<Reservation> update(
            @AuthenticationPrincipal MyUserDetails userDetails,
            @RequestBody Reservation Reservation,
            @PathVariable int id){

        Reservation.setId(id);

        Optional<Reservation>optionalReservation = ReservationDao.findById(id);

        if(optionalReservation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Reservation reservationBdd = optionalReservation.get();

        if(!reservationBdd.getUtilisateur().getRole().equals("ROLE_ADMIN") &&
                !reservationBdd.getUtilisateur().getEmail().equals(userDetails.getUsername())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        ReservationDao.save(Reservation);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/reservation/{id}")
    @IsPartenaire
    public ResponseEntity<Reservation> delete(
            @PathVariable int id,
            @AuthenticationPrincipal MyUserDetails userDetails) {

        Optional<Reservation> optionalReservation = ReservationDao.findById(id);

        if(optionalReservation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Reservation reservationBdd = optionalReservation.get();

        if(!reservationBdd.getUtilisateur().getRole().equals("ROLE_ADMIN") &&
                !reservationBdd.getUtilisateur().getEmail().equals(userDetails.getUsername())){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        ReservationDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
