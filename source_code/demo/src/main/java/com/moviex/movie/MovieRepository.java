package com.moviex.movie;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MovieRepository extends MongoRepository<Movie, String> {

    List<Movie> findByGenreIgnoreCaseOrderByCreatedAtDesc(String genre);

    List<Movie> findByTitleContainingIgnoreCaseOrGenreContainingIgnoreCaseOrYearContainingIgnoreCase(String title,
                                                                                                     String genre,
                                                                                                     String year);
}
