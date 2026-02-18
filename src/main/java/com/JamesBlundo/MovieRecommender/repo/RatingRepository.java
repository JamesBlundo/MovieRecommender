package com.JamesBlundo.MovieRecommender.repo;

import com.JamesBlundo.MovieRecommender.model.Rating;
import com.JamesBlundo.MovieRecommender.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    // All ratings for a given title
    List<Rating> findByTitle(Title title);

}
