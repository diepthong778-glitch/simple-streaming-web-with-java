package com.moviex.movie;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MovieService {

    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public List<Movie> latestByGenre(String genre, int limit) {
        List<Movie> movies = repository.findByGenreIgnoreCaseOrderByCreatedAtDesc(genre);
        return movies.stream().limit(limit).toList();
    }

    public List<Movie> search(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        }
        return repository.findByTitleContainingIgnoreCaseOrGenreContainingIgnoreCaseOrYearContainingIgnoreCase(
                keyword, keyword, keyword);
    }

    public List<Movie> all() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public Movie create(Movie movie) {
        return repository.save(movie);
    }

    public Movie update(String id, Movie updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setTitle(updated.getTitle());
                    existing.setGenre(updated.getGenre());
                    existing.setYear(updated.getYear());
                    existing.setImageName(updated.getImageName());
                    existing.setUrlYoutube(updated.getUrlYoutube());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new IllegalArgumentException("Movie not found: " + id));
    }

    public void delete(String id) {
        repository.deleteById(id);
    }

    public Movie findById(String id) {
        return repository.findById(id).orElse(null);
    }
}
