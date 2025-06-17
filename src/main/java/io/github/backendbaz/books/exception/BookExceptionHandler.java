package io.github.backendbaz.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleException(BookNotFoundException exception) {
        BookErrorResponse response = new BookErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<BookErrorResponse> handleException(Exception exception) {
        BookErrorResponse response = new BookErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Invalid request",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
