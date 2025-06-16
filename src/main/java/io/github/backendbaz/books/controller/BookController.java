package io.github.backendbaz.books.controller;

import io.github.backendbaz.books.entity.Book;
import io.github.backendbaz.books.request.BookRequest;
import jakarta.validation.constraints.Min;
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

    // GET -> http://localhost:8080/api/books/3 (Path Parameter)
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable @Min(value = 1) long id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // POST -> http://localhost:8080/api/books (Request Body)
    @PostMapping
    public void createBook(@RequestBody BookRequest newBook) {
        long id = books.isEmpty() ? 1 : books.getLast().getId() + 1;
        Book book = convertToBook(id, newBook);
        books.add(book);
    }

    // PUT -> http://localhost:8080/api/books/3 (Request Body - Path Parameter)
    @PutMapping("/{id}")
    public void updateBook(@PathVariable @Min(value = 1) long id,
                           @RequestBody BookRequest updatedBook) {
        books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .ifPresent(foundBook ->
                        books.set(books.indexOf(foundBook),
                                convertToBook(id, updatedBook)));
    }

    // DELETE -> http://localhost:8080/api/books/3 (Path Parameter)
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable @Min(value = 1) long id) {
        books.removeIf(book -> book.getId() == id);
    }

    private Book convertToBook(long id, BookRequest bookRequest) {
        return new Book(
                id,
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getCategory(),
                bookRequest.getRating()
        );
    }

}
