package io.github.backendbaz.books.controller;

import io.github.backendbaz.books.entity.Book;
import io.github.backendbaz.books.exception.BookNotFoundException;
import io.github.backendbaz.books.request.BookRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Books REST API endpoints",
        description = "Operations related to books")
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
    @Operation(summary = "Get all books",
            description = "Retrieve a list of all available books")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Book> getBooks(@Parameter(description = "An optional query parameter")
                                   @RequestParam(required = false) String category) {
        if (category == null) return books;
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    // GET -> http://localhost:8080/api/books/3 (Path Parameter)
    @Operation(summary = "Get a book by id",
            description = "Retrieve a specific book by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@Parameter(description = "Id of book to be retrieved")
                                @PathVariable @Min(value = 1) long id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new BookNotFoundException("Book is not found with id "
                                + id));
    }

    // POST -> http://localhost:8080/api/books (Request Body)
    @Operation(summary = "Create a new book",
            description = "Add a new book to the list")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createBook(@Valid @RequestBody BookRequest newBook) {
        long id = books.isEmpty() ? 1 : books.getLast().getId() + 1;
        Book book = convertToBook(id, newBook);
        books.add(book);
    }

    // PUT -> http://localhost:8080/api/books/3 (Request Body - Path Parameter)
    @Operation(summary = "Update a book",
            description = "Update the details of an existing book")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public Book updateBook(@Parameter(description = "Id of the book to update")
                               @PathVariable @Min(value = 1) long id,
                           @Valid @RequestBody BookRequest updatedBook) {
        Book foundBook = books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new BookNotFoundException("Book is not found with id "
                                + id));
        Book editBook = convertToBook(id, updatedBook);
        books.set(books.indexOf(foundBook), editBook);
        return editBook;
    }

    // DELETE -> http://localhost:8080/api/books/3 (Path Parameter)
    @Operation(summary = "Delete a book",
            description = "Remove a book from the list")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@Parameter(description = "Id of the book to delete")
                               @PathVariable @Min(value = 1) long id) {
        if (books.stream().noneMatch(book -> book.getId() == id))
            throw new BookNotFoundException("Book is not found with id " + id);
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
