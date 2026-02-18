package com.JamesBlundo.MovieRecommender.config;


import com.JamesBlundo.MovieRecommender.model.Genre;
import com.JamesBlundo.MovieRecommender.model.Title;
import com.JamesBlundo.MovieRecommender.model.TitleType;
import com.JamesBlundo.MovieRecommender.repo.GenreRepository;
import com.JamesBlundo.MovieRecommender.repo.TitleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TitleRepository titleRepository;
    private final GenreRepository genreRepository;

    public DataSeeder(TitleRepository titleRepository, GenreRepository genreRepository){
        this.titleRepository = titleRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public void run(String... args){
        if (genreRepository.count() == 0) {
            List<String> starterGenres = List.of(
                    "Action", "Comedy", "Drama", "Thriller", "Sci-Fi",
                    "Horror", "Romance", "Animation", "Crime", "Documentary"
            );
            starterGenres.forEach(g -> genreRepository.save(new Genre(g)));
        }

        if (titleRepository.count() == 0) {
            Title tdk = new Title("The Dark Knight", TitleType.MOVIE, 2008);
            Title bb = new Title("Breaking Bad", TitleType.SHOW, 2008);

            Genre action = genreRepository.findByName("Action").orElseThrow();
            Genre crime = genreRepository.findByName("Crime").orElseThrow();
            Genre drama = genreRepository.findByName("Drama").orElseThrow();
            Genre thriller = genreRepository.findByName("Thriller").orElseThrow();

            tdk.getGenres().add(action);
            tdk.getGenres().add(crime);

            bb.getGenres().add(drama);
            bb.getGenres().add(crime);
            bb.getGenres().add(thriller);

            titleRepository.save(tdk);
            titleRepository.save(bb);
        }
    }
}
