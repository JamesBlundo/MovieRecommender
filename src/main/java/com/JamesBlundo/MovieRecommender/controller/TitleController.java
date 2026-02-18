package com.JamesBlundo.MovieRecommender.controller;

import com.JamesBlundo.MovieRecommender.dto.TitleCreateRequest;
import com.JamesBlundo.MovieRecommender.model.Rating;
import com.JamesBlundo.MovieRecommender.model.Title;
import com.JamesBlundo.MovieRecommender.model.TitleType;
import com.JamesBlundo.MovieRecommender.repo.GenreRepository;
import com.JamesBlundo.MovieRecommender.repo.RatingRepository;
import com.JamesBlundo.MovieRecommender.repo.TitleRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;


@Controller
public class TitleController {

    private final TitleRepository titleRepository;
    private final GenreRepository genreRepository;
    private final RatingRepository ratingRepository;

    public TitleController(TitleRepository titleRepository,
                           GenreRepository genreRepository,
                           RatingRepository ratingRepository) {
        this.titleRepository = titleRepository;
        this.genreRepository = genreRepository;
        this.ratingRepository = ratingRepository;
    }
    @GetMapping("/titles/{id}")
    public String titleDetails(@PathVariable Long id, Model model) {
        Title title = titleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("title", title);
        model.addAttribute("ratings", ratingRepository.findByTitle(title));
        return "title_detail";
    }
    @PostMapping("/titles/{id}/rate")
    public String rateTitle(@PathVariable Long id, @RequestParam int score) {
        Title title = titleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (score < 1 || score > 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Score must be 1–10.");
        }

        Rating rating = new Rating(title, score, LocalDateTime.now());
        ratingRepository.save(rating);

        return "redirect:/titles/" + id;
    }


    @GetMapping("/titles")
    public String listTitles(Model model) {
        model.addAttribute("titles", titleRepository.findAll());
        return "titles";
    }

    @GetMapping("/titles/new")
    public String newTitleForm(Model model) {
        model.addAttribute("titleCreateRequest", new TitleCreateRequest());
        model.addAttribute("allGenres", genreRepository.findAll());
        return "title_form";
    }

    @PostMapping("/titles")
    public String createTitle(
            @Valid @ModelAttribute TitleCreateRequest titleCreateRequest,
            BindingResult bindingResult,
            Model model
    ){
        if (bindingResult.hasErrors()){
            model.addAttribute("allGenres", genreRepository.findAll());
            return "title_form";
        }

        Title title = new Title(
                titleCreateRequest.getName(),
                titleCreateRequest.getType(),
                titleCreateRequest.getReleaseYear()
        );

        var selectedGenres = new HashSet<>(genreRepository.findAllById(
                titleCreateRequest.getGenreIds()
        ));
        title.setGenres(selectedGenres);


        titleRepository.save(title);
        return "redirect:/titles";
    }


}
