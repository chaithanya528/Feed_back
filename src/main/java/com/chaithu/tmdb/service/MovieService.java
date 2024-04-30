package com.chaithu.tmdb.service;

import com.chaithu.tmdb.exception.InvaildDataException;
import com.chaithu.tmdb.exception.NotFoundException;
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
            throw new InvaildDataException("Invalid movie null");
        }
        return movieRepository.save(movie);
    }

    public Movie read(Long id){
        return movieRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("movie not found with id : " +id));
    }

    public void update(Long id, Movie update){
        if (update == null || id == null){     // ||movie.getId() == null
            throw new InvaildDataException("Invalid movie: null");
        }

        // check if exit

        if(movieRepository.existsById(id)){
            Movie movie = movieRepository.getReferenceById(id);
            movie.setName(update.getName());
            movie.setDirector(update.getDirector());
            movie.setActors(update.getActors());
            movieRepository.save(movie);
        }else {
            throw new NotFoundException("movie not found with id : " +id);
        }
    }

    public void delete(Long id){
        if(movieRepository.existsById(id)){
            movieRepository.deleteById(id);
        } else {
            throw new NotFoundException("movie not found with id : " +id);
        }
    }
}
