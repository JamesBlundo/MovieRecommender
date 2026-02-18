package com.JamesBlundo.MovieRecommender.repo;

import com.JamesBlundo.MovieRecommender.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Long> {

}
