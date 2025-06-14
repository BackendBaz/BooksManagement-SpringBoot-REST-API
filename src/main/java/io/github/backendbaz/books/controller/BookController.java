package io.github.backendbaz.books.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    // GET -> http://localhost:8080
    @GetMapping
    public String sayHello() {
        return "Hello Amirhossein!";
    }

}
