package io.github.backendbaz.books.controller;

import io.github.backendbaz.books.entity.Book;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
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

    // GET -> http://localhost:8080/api/books?category=science (Query Parameter)
    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String category) {
        if (category == null) return books;
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    // GET -> http://localhost:8080/api/books/C%23 (Path Parameter)
    @GetMapping("/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    // POST -> http://localhost:8080/api/books (Request Body)
    @PostMapping
    public void createBook(@RequestBody Book newBook) {
        boolean notExists = books.stream().noneMatch(book -> book.getTitle()
                        .equalsIgnoreCase(newBook.getTitle()));
        if (notExists) books.add(newBook);
    }

    // PUT -> http://localhost:8080/api/books/java (Request Body - Path Parameter)
    @PutMapping("/{title}")
    public void updateBook(@PathVariable String title,
                           @RequestBody Book updatedBook) {
        books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .ifPresent(foundBook ->
                        books.set(books.indexOf(foundBook), updatedBook));
    }

    // DELETE -> http://localhost:8080/api/books/java (Path Parameter)
    @DeleteMapping("/{title}")
    public void deleteBook(@PathVariable String title) {
        books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
    }

}
