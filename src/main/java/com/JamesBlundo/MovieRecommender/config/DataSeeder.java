package com.JamesBlundo.MovieRecommender.config;


import com.JamesBlundo.MovieRecommender.model.Title;
import com.JamesBlundo.MovieRecommender.model.TitleType;
import com.JamesBlundo.MovieRecommender.repo.TitleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TitleRepository titleRepository;

    public DataSeeder(TitleRepository titleRepository){
        this.titleRepository = titleRepository;
    }

    @Override
    public void run(String... args){
        if (titleRepository.count() == 0) {
            titleRepository.save(new Title("The Dark Knight", TitleType.MOVIE, 2008));
            titleRepository.save(new Title("Breaking Bad", TitleType.SHOW, 2008));
        }
    }
}
