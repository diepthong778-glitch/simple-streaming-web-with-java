package com.moviex.web;

import com.moviex.movie.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final MovieService movieService;

    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String landing() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("actionList", movieService.latestByGenre("Action", 18));
        model.addAttribute("adventureList", movieService.latestByGenre("Adventure", 18));
        model.addAttribute("familyList", movieService.latestByGenre("Family", 18));
        model.addAttribute("horrorList", movieService.latestByGenre("Horror", 18));
        model.addAttribute("thrillerList", movieService.latestByGenre("Thriller", 18));
        return "homepage";
    }

    @PostMapping("/search")
    public String search(@RequestParam("ch") String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        model.addAttribute("results", movieService.search(keyword));
        return "search";
    }
}
