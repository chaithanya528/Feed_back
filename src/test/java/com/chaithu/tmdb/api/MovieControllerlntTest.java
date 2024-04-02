package com.chaithu.tmdb.api;

import com.chaithu.tmdb.model.Movie;
import com.chaithu.tmdb.repo.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerlntTest {

        @Autowired
         MockMvc mockMvc;

        @Autowired
        ObjectMapper objectMapper;

        @Autowired
    MovieRepository movieRepository;

        @BeforeEach
        void cleanUp(){
            movieRepository.deleteAllInBatch();
        }


    @Test
         void giveMovie_whenCreateMovie_thenReturnSavedMovie() throws Exception{

            // given
            Movie movie = new Movie();
            movie.setName("fidaa");
            movie.setDirector("sakhar kamala");
            movie.setActors(List.of("varun tej", "sai palavi", "usaaaaa"));

            // when create a movie

              var response = mockMvc.perform(post("/movies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(movie)));


              // then verify saved movie
              response.andDo(print())
                      .andExpect(status().isOk());
                     // .andExpect(jsonPath("$.id", is(notNullValue())))
                    //  .andExpect(jsonPath("$.name", is(movie.getName())))
                    //  .andExpect(jsonPath("$.director", is(movie.getDirector())))
                     // .andExpect(jsonPath(".actors", is(movie.getActors())))
        }

        @Test
    void givenMovieId_whenFetchMovie_thenReturnMovie() throws Exception {
            Movie movie = new Movie();
            movie.setName("fidaa");
            movie.setDirector("sakhar kamala");
            movie.setActors(List.of("varun tej", "sai palavi", "usaaaaa"));

            Movie savedMovie = movieRepository.save(movie);

            var response = mockMvc.perform(get("/movies/" + savedMovie.getId()));

            response.andDo(print())
                    .andExpect(status().isOk());
            // .andExpect(jsonPath("$.id", is(savedMovie.getId().intValue())))
            //.andExpect(jsonPath("$.name", is(movie.getName())))
               //     .andExpect(jsonPath("$.director", is(movie.getDirector())))
           // .andExpect(jsonPath(".actors", is(movie.getActors())));
        }


        @Test
    void givenSavedMovie_whenUpdateMovie_thenMovieUpdatedInDb() throws Exception{

            Movie movie = new Movie();
            movie.setName("fidaa");
            movie.setDirector("sakhar kamala");
            movie.setActors(List.of("varun tej", "sai palavi", "usaaaaa"));

            Movie savedMovie = movieRepository.save(movie);

            Long id = savedMovie.getId();

            // update movie

            movie.setActors(List.of("varun tej", "sai palavi", "jsnkjcbsjb"));

            var response = mockMvc.perform(put("/movies/" + id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(movie)));

                // the verify the update
         //   response.andDo(print())
               //     .andExpect(status().isOk());

            var fetchresponse = mockMvc.perform(get("/movies/" + id));

            // verify the saved movie

            fetchresponse .andDo(print())
                    .andExpect(status().isOk());
            //.andExpect(jsonPath("$.name", is(movie.getName())))
              //      .andExpect(jsonPath("$.director", is(movie.getDirector())))
            //.andExpect(jsonPath(".actors", is(movie.getActors())));

        }
}