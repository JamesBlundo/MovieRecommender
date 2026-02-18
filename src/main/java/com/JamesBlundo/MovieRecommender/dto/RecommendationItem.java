package com.JamesBlundo.MovieRecommender.dto;

import com.JamesBlundo.MovieRecommender.model.Title;

import java.util.List;

public class RecommendationItem {

    private final Title title;
    private final double score;
    private final List<String> matchedGenres;

    public RecommendationItem(Title title, double score, List<String> matchedGenres) {
        this.title = title;
        this.score = score;
        this.matchedGenres = matchedGenres;
    }

    public Title getTitle() { return title; }
    public double getScore() { return score; }
    public List<String> getMatchedGenres() { return matchedGenres; }
}
