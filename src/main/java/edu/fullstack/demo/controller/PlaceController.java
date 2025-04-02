package edu.fullstack.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fullstack.demo.dao.PlaceDao;
import edu.fullstack.demo.model.Place;
import edu.fullstack.demo.security.IsAdmin;
import edu.fullstack.demo.security.IsPartenaire;
import edu.fullstack.demo.view.AffichagePlace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin()
public class PlaceController {

    @Autowired
    protected PlaceDao placeDao;

    @GetMapping("/place/{id}")
    @JsonView(AffichagePlace.class)
    @IsPartenaire
    public ResponseEntity<Place> get(@PathVariable int id){

        Optional<Place> optionalPlace = placeDao.findById(id);

        if(optionalPlace.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(optionalPlace.get(), HttpStatus.OK);
    }

    @GetMapping("/places")
    @JsonView(AffichagePlace.class)
    @IsPartenaire
    public List<Place> getAll() {

        return placeDao.findAll();
    }

    @GetMapping("/place-indisponible/{date}")
    @JsonView(AffichagePlace.class)
    @IsPartenaire
    public List<Place> getAllIndisponible(@PathVariable String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);

        return placeDao.indisponibleLe(dateTime);
    }

    @GetMapping("/place-disponible/{date}")
    @JsonView(AffichagePlace.class)
    @IsPartenaire
    public List<Place> getAllDisponible(@PathVariable String date) {
        LocalDateTime dateTime = LocalDateTime.parse(date);

        return placeDao.disponibleLe(dateTime);
    }

    @PostMapping("/place")
    @IsAdmin
    public ResponseEntity<Place> create(@RequestBody Place place){

        place.setId(null);
        placeDao.save(place);

        return new ResponseEntity<>(place, HttpStatus.CREATED);
    }

    @PutMapping("/place/{id}")
    @IsAdmin
    public ResponseEntity<Place> update(@RequestBody Place place, @PathVariable int id){
        place.setId(id);

        placeDao.save(place);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/place/{id}")
    @IsAdmin
    public ResponseEntity<Place> delete(@PathVariable int id) {

        Optional<Place> optionalPlace = placeDao.findById(id);

        if(optionalPlace.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        placeDao.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
