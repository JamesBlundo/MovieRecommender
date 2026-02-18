package com.JamesBlundo.MovieRecommender.model;


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

    private Integer year;

    public Title() {}

    public Title(String name, TitleType type, Integer year) {
        this.name = name;
        this.type = type;
        this.year = year;
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

    public Integer getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(TitleType type) {
        this.type = type;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
