package com.JamesBlundo.MovieRecommender.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many ratings can belong to one Title
    @ManyToOne(optional = false)
    @JoinColumn(name = "title_id", nullable = false)
    private Title title;

    @Column(nullable = false)
    private int score; // 1–10, required

    @Column(nullable = false)
    private LocalDateTime ratedAt;

    public Rating() {}

    public Rating(Title title, int score, LocalDateTime ratedAt) {
        this.title = title;
        this.score = score;
        this.ratedAt = ratedAt;
    }

    public Long getId() { return id; }
    public Title getTitle() { return title; }
    public int getScore() { return score; }
    public LocalDateTime getRatedAt() { return ratedAt; }

    public void setTitle(Title title) { this.title = title; }
    public void setScore(int score) { this.score = score; }
    public void setRatedAt(LocalDateTime ratedAt) { this.ratedAt = ratedAt; }
}
