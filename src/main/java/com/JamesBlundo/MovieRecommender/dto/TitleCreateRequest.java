package com.JamesBlundo.MovieRecommender.dto;

import com.JamesBlundo.MovieRecommender.model.TitleType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;


public class TitleCreateRequest {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotNull(message = "Type is required.")
    private TitleType type;

    @Min(value = 1880, message = "Year must be 1880 or later.")
    @Max(value = 2100, message = "Year must be 2100 or earlier.")
    private Integer releaseYear;

    public String getName() { return name; }
    public TitleType getType() { return type; }
    public Integer getReleaseYear() { return releaseYear; }

    public void setName(String name) { this.name = name; }
    public void setType(TitleType type) { this.type = type; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    private Set<Long> genreIds = new HashSet<>();

    public Set<Long> getGenreIds() { return genreIds; }
    public void setGenreIds(Set<Long> genreIds) { this.genreIds = genreIds; }

}
