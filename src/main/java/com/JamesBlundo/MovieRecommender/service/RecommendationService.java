package com.JamesBlundo.MovieRecommender.service;

import com.JamesBlundo.MovieRecommender.dto.RecommendationItem;
import com.JamesBlundo.MovieRecommender.model.Genre;
import com.JamesBlundo.MovieRecommender.model.Rating;
import com.JamesBlundo.MovieRecommender.model.Title;
import com.JamesBlundo.MovieRecommender.repo.RatingRepository;
import com.JamesBlundo.MovieRecommender.repo.TitleRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.*;

@Service
public class RecommendationService {

    private final TitleRepository titleRepository;
    private final RatingRepository ratingRepository;

    public RecommendationService(TitleRepository titleRepository, RatingRepository ratingRepository) {
        this.titleRepository = titleRepository;
        this.ratingRepository = ratingRepository;
    }

    /**
     * Returns top N recommendations using genre preference scoring.
     *
     * Algorithm:
     * 1) Build a map: genreId -> preferenceScore (sum of ratings across titles that have that genre)
     * 2) Consider titles with NO ratings as "unrated candidates"
     * 3) Score each candidate by summing preferenceScore for its genres
     * 4) Sort by score desc, return top N
     */
    public List<RecommendationItem> recommendTopN(int n) {
        List<Rating> allRatings = ratingRepository.findAll();

        // If user has no ratings yet, we can’t infer preferences.
        if (allRatings.isEmpty()) {
            return List.of();
        }

        // Titles that have at least one rating should not be recommended (already rated).
        Set<Long> ratedTitleIds = allRatings.stream()
                .map(r -> r.getTitle().getId())
                .collect(Collectors.toSet());

        // Genre preference = sum of scores for every rated title that contains the genre.


        final int NEUTRAL = 5;
        final int MIN_SUPPORT = 2;

        Map<Long, Integer> genreSum = new HashMap<>();
        Map<Long, Integer> genreCount = new HashMap<>();
        Map<Long, Double> genrePreference = new HashMap<>();

        for (Long genreId : genreSum.keySet()) {
            int count = genreCount.getOrDefault(genreId, 0);
            if (count >= MIN_SUPPORT) {
                double avg = (double) genreSum.get(genreId) / count;
                genrePreference.put(genreId, avg);
            }
        }

        for (Rating r : allRatings) {
            int weight = r.getScore() - NEUTRAL; // can be negative
            for (Genre g : r.getTitle().getGenres()) {
                genrePreference.merge(g.getId(), weight, Integer::sum);
            }
        }


        // Candidate titles = titles with no ratings
        List<Title> candidates = titleRepository.findAll().stream()
                .filter(t -> !ratedTitleIds.contains(t.getId()))
                .toList();

        List<RecommendationItem> results = new ArrayList<>();


        for (Title t : candidates) {
            int total = 0;
            List<String> matchedGenreNames = new ArrayList<>();

            for (Genre g : t.getGenres()) {
                int contrib = genrePreference.getOrDefault(g.getId(), 0);
                if (contrib > 0) {
                    total += contrib;
                    matchedGenreNames.add(g.getName());
                }
            }

            // If score is 0, it means none of the title’s genres match what you’ve rated highly
            if (total > 0) {
                results.add(new RecommendationItem(t, total, matchedGenreNames));
            }
        }

        results.sort(Comparator.comparingInt(RecommendationItem::getScore).reversed());

        if (results.size() > n) {
            return results.subList(0, n);
        }
        return results;
    }
}
