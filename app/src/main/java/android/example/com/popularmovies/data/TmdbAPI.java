/*
 * PopularMovies by bytetonight
 * Created for the Udacity (c) Android (c) Developer Nanodegree
 * This software uses a remote API kindly made accessible by
 * https://www.themoviedb.org/
 *
 * Copyright (c) 2017.
 */

package android.example.com.popularmovies.data;

import android.example.com.popularmovies.models.MovieResults;
import android.example.com.popularmovies.models.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ByteTonight on 29.10.2017.
 *
 * An Interface describing Retrofit Endpoints to make calls to the remote API in a pretty cool way
 */


public interface TmdbAPI {

    @GET("movie/{id}")
    Call<Movie> getMovieById(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{preference}")
    Call<MovieResults> listMoviesByPreference(
            @Path("preference") String preference,
            @Query("api_key") String apiKey);



}
