package io.github.backendbaz.books.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class BookRequest {

    @Size(min = 2, max = 50, message = "Title must be between 2 and 50 characters!")
    private String title;

    @Size(min = 2, max = 35, message = "Author must be between 2 and 35 characters!")
    private String author;

    @Size(min = 2, max = 30, message = "Category must be between 2 and 30 characters!")
    private String category;

    @Min(value = 1, message = "Rating must be greater than 1!")
    @Max(value = 10, message = "Rating must be smaller than 10!")
    private int rating;

    public BookRequest(String title, String author, String category, int rating) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
