package org.test.myfirstspringbootweb.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class GameDto {

    @NotBlank
    private String genre;
    @NotBlank
    private String title;
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("10.0")
    private Double rating;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getRating() {
        return rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
