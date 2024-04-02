package com.chaithu.tmdb.service;

import com.chaithu.tmdb.model.Movie;
import com.chaithu.tmdb.repo.MovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    // crud operations

    public Movie create(Movie movie){

        if (movie == null){
            throw new RuntimeException("Invalid movie");
        }
        return movieRepository.save(movie);
    }

    public Movie read(Long id){
        return movieRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("movie not found"));
    }

    public void update(Long id, Movie update){
        if (update == null || id == null){     // ||movie.getId() == null
            throw new RuntimeException("Invalid movie");
        }

        // check if exit

        if(movieRepository.existsById(id)){
            Movie movie = movieRepository.getReferenceById(id);
            movie.setName(update.getName());
            movie.setDirector(update.getDirector());
            movie.setActors(update.getActors());
            movieRepository.save(movie);
        }else {
            throw new RuntimeException("Movie not found");
        }
    }

    public void delete(Long id){
        if(movieRepository.existsById(id)){
            movieRepository.deleteById(id);
        } else {
            throw new RuntimeException("movie not found");
        }
    }
}
