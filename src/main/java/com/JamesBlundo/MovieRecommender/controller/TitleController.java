package com.JamesBlundo.MovieRecommender.controller;

import com.JamesBlundo.MovieRecommender.dto.TitleCreateRequest;
import com.JamesBlundo.MovieRecommender.model.Title;
import com.JamesBlundo.MovieRecommender.model.TitleType;
import com.JamesBlundo.MovieRecommender.repo.TitleRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TitleController {

    private final TitleRepository titleRepository;

    public TitleController(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @GetMapping("/titles")
    public String listTitles(Model model) {
        model.addAttribute("titles", titleRepository.findAll());
        return "titles";
    }

    @GetMapping("/titles/new")
    public String newTitleForm(Model model) {
        model.addAttribute("titleCreateRequest", new TitleCreateRequest());
        return "title_form";
    }

    @PostMapping("/titles")
    public String createTitle(
        @Valid @ModelAttribute TitleCreateRequest titleCreateRequest,
        BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()){
            return "title_form";
        }
        Title title = new Title(
                titleCreateRequest.getName(),
                titleCreateRequest.getType(),
                titleCreateRequest.getReleaseYear()
        );
        titleRepository.save(title);
        return "redirect:/titles";
    }
}
