package com.moviex.web;

import com.moviex.movie.Movie;
import com.moviex.movie.MovieService;
import com.moviex.web.dto.MovieForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    private final MovieService movieService;

    public AdminController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("movies", movieService.all());
        return "admin";
    }

    @GetMapping("/admin/movies/new")
    public String newMovie(Model model) {
        model.addAttribute("movieForm", new MovieForm());
        return "admin_add";
    }

    @PostMapping("/admin/movies")
    public String create(@Valid @ModelAttribute("movieForm") MovieForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "admin_add";
        }
        Movie movie = new Movie();
        movie.setTitle(form.getTitle());
        movie.setGenre(form.getGenre());
        movie.setYear(form.getYear());
        movie.setImageName(form.getImageName());
        movie.setUrlYoutube(form.getUrlYoutube());
        movieService.create(movie);
        return "redirect:/admin";
    }

    @GetMapping("/admin/movies/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Movie movie = movieService.findById(id);
        if (movie == null) {
            return "redirect:/admin";
        }
        MovieForm form = new MovieForm();
        form.setId(movie.getId());
        form.setTitle(movie.getTitle());
        form.setGenre(movie.getGenre());
        form.setYear(movie.getYear());
        form.setImageName(movie.getImageName());
        form.setUrlYoutube(movie.getUrlYoutube());
        model.addAttribute("movieForm", form);
        return "edit_movie";
    }

    @PostMapping("/admin/movies/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("movieForm") MovieForm form,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit_movie";
        }
        Movie movie = new Movie();
        movie.setTitle(form.getTitle());
        movie.setGenre(form.getGenre());
        movie.setYear(form.getYear());
        movie.setImageName(form.getImageName());
        movie.setUrlYoutube(form.getUrlYoutube());
        movieService.update(id, movie);
        return "redirect:/admin";
    }

    @PostMapping("/admin/movies/{id}/delete")
    public String delete(@PathVariable String id) {
        movieService.delete(id);
        return "redirect:/admin";
    }
}
