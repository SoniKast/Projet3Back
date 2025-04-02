package edu.fullstack.demo.controller;

import edu.fullstack.demo.model.Place;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping({"/hello", "/"})
    public String bonjour() {

        return "<h1>Hello World</h1>";
    }
}
