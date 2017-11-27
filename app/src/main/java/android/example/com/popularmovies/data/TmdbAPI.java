/*
 * PopularMovies by bytetonight
 * Created for the Udacity (c) Android (c) Developer Nanodegree
 * This software uses a remote API kindly made accessible by
 * https://www.themoviedb.org/
 *
 * Copyright (c) 2017.
 */

package android.example.com.popularmovies.data;

import android.example.com.popularmovies.models.Movie;
import android.example.com.popularmovies.models.MovieResults;
import android.example.com.popularmovies.models.MovieTrailerList;
import android.example.com.popularmovies.models.ReviewResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ByteTonight on 29.10.2017.
 * <p>
 * An Interface describing Retrofit Endpoints to make calls to the remote API in a pretty cool way
 */


public interface TmdbAPI {

    /**
     * Request the details for a given Movie identified by id
     *
     * @param id     is the tmdb id of a movie acquired from Call<MovieResults>
     * @param apiKey is the key you have requested from the API owner
     * @return a populated Movie model
     */
    @GET("movie/{id}")
    Call<Movie> getMovieById(@Path("id") int id, @Query("api_key") String apiKey);

    /**
     * Request a list of video trailers associated with the given Movie
     *
     * @param id     is the tmdb id of a movie acquired from Call<MovieResults>
     * @param apiKey is the key you have requested from the API owner
     * @return a MovieTrailerList Object which holds the list of MovieTrailer Objects
     */
    @GET("movie/{id}/videos")
    Call<MovieTrailerList> getTrailersListByMovieId(@Path("id") int id, @Query("api_key") String apiKey);

    /**
     * Request a list of video trailers associated with the given Movie
     *
     * @param id     is the tmdb id of a movie acquired from Call<MovieResults>
     * @param apiKey is the key you have requested from the API owner
     * @return
     */
    @GET("movie/{id}/reviews")
    Call<ReviewResults> getReviewsByMovieId(@Path("id") int id, @Query("api_key") String apiKey);

    /**
     * Requests data for movies by preference, ex. popular, top rated, upcoming, now playing
     *
     * @param preference
     * @param apiKey is the key you have requested from the API owner
     * @return
     */
    @GET("movie/{preference}")
    Call<MovieResults> listMoviesByPreference(
            @Path("preference") String preference,
            @Query("api_key") String apiKey);


}
