package com.JamesBlundo.MovieRecommender.controller;

import com.JamesBlundo.MovieRecommender.service.RecommendationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendations")
    public String recommendations(Model model) {
        model.addAttribute("recs", recommendationService.recommendTopN(10));
        return "recommendations";
    }
}
