package edu.fullstack.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import edu.fullstack.demo.view.AffichageReservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @JsonView(AffichageReservation.class)
    protected LocalDateTime dateDebut;

    @JsonView(AffichageReservation.class)
    protected LocalDateTime dateFin;

    @CreatedDate
    @JsonView(AffichageReservation.class)
    protected LocalDateTime dateCreation;
    
    @ManyToOne(optional = false)
    @JsonView(AffichageReservation.class)
    protected Place place;

    @ManyToOne(optional = false)
    protected Utilisateur utilisateur;
}
