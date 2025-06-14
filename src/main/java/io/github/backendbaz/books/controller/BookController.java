package io.github.backendbaz.books.controller;

import io.github.backendbaz.books.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private final List<Book> books = new ArrayList<>();

    private void initializeBooks() {
        books.addAll(List.of(
                new Book("C#", "Anders Hejlsberg", "programming"),
                new Book("Python", "Guido van Rossum", "programming"),
                new Book("Java", "James Gosling", "programming"),
                new Book("JavaScript", "Brendan Eich", "programming"),
                new Book("PHP", "Rasmus Lerdorf", "programming"),
                new Book("GoLang", "Robert Griesemer", "programming"),
                new Book("C++", "Bjarne Stroustrup", "programming"),
                new Book("Chemistry", "Antoine Lavoisier", "science"),
                new Book("Physics", "Galileo Galilei", "science")
        ));
    }

    public BookController() {
        initializeBooks();
    }

    // GET -> http://localhost:8080/api/books
    @GetMapping("/api/books")
    public List<Book> getBooks() {
        return books;
    }

}
