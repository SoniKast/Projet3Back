package edu.fullstack.demo.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fullstack.demo.view.AffichagePlace;
import edu.fullstack.demo.view.AffichageReservation;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(AffichagePlace.class)
    protected Integer id;

    @JsonView({AffichageReservation.class, AffichagePlace.class})
    protected String numero;

    @OneToMany(mappedBy = "place")
    protected List<Reservation> reservations;
}
