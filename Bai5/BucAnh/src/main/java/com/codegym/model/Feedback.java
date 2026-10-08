package com.codegym.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String feedback;

    @Column(nullable = false)
    private int likes = 0;

    @Column(name = "feedback_date", nullable = false)
    private LocalDate date;

    public Feedback() {
        this.date = LocalDate.now();
        this.likes = 0;
    }

    public Feedback(int rating, String author, String feedback) {
        this.rating = rating;
        this.author = author;
        this.feedback = feedback;
        this.likes = 0;
        this.date = LocalDate.now();
    }

    public Feedback(Long id, int rating, String author, String feedback, int likes, LocalDate date) {
        this.id = id;
        this.rating = rating;
        this.author = author;
        this.feedback = feedback;
        this.likes = likes;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
