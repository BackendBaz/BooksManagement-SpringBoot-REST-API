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
                new Book(1, "C#", "Anders Hejlsberg", "programming", 9),
                new Book(2, "Python", "Guido van Rossum", "programming", 3),
                new Book(3, "Java", "James Gosling", "programming", 10),
                new Book(4, "JavaScript", "Brendan Eich", "programming", 5),
                new Book(5, "PHP", "Rasmus Lerdorf", "programming", 7),
                new Book(6, "GoLang", "Robert Griesemer", "programming", 4),
                new Book(7, "C++", "Bjarne Stroustrup", "programming", 9),
                new Book(8, "Chemistry", "Antoine Lavoisier", "science", 1),
                new Book(9, "Physics", "Galileo Galilei", "science", 1)
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
