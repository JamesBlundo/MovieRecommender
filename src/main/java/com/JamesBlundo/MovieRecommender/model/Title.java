package com.JamesBlundo.MovieRecommender.model;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "titles")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TitleType type;

    private Integer releaseYear;

    public Title() {}

    public Title(String name, TitleType type, Integer releaseYear) {
        this.name = name;
        this.type = type;
        this.releaseYear = releaseYear;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TitleType getType() {
        return type;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(TitleType type) {
        this.type = type;
    }

    public void setYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    @ManyToMany
    @JoinTable(
            name = "title_genres",
            joinColumns = @JoinColumn(name = "title_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    public Set<Genre> getGenres() {
        return genres;
    }
    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

}
