package com.moviex.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.moviex.movie.Movie;
import com.moviex.movie.MovieRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final MovieRepository movieRepository;

    public DataLoader(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) {
        if (movieRepository.count() > 0) {
            return;
        }

        List<Movie> samples = List.of(
                movie("John Wick: Chapter 2", "Action", "2017", "action.jpg",
                        "https://www.youtube.com/watch?v=ChpLV9AMqm4"),
                movie("Dune", "Adventure", "2021", "dune.jpg",
                        "https://www.youtube.com/watch?v=n9xhJrPXop4"),
                movie("Coco", "Family", "2017", "coco.jpg",
                        "https://www.youtube.com/watch?v=Rvr68u6k5sI"),
                movie("The Conjuring", "Horror", "2013", "the-conjuring.png",
                        "https://www.youtube.com/watch?v=k10ETZ41q5o"),
                movie("Se7en", "Thriller", "1995", "se7en.png",
                        "https://www.youtube.com/watch?v=znmZoVkCjpI")
        );

        movieRepository.saveAll(samples);
    }

    private Movie movie(String title, String genre, String year, String image, String url) {
        Movie m = new Movie();
        m.setTitle(title);
        m.setGenre(genre);
        m.setYear(year);
        m.setImageName(image);
        m.setUrlYoutube(url);
        return m;
    }
}
