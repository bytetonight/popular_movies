/*
 * PopularMovies by bytetonight
 * Created for the Udacity (c) Android (c) Developer Nanodegree
 * This software uses a remote API kindly made accessible by
 * https://www.themoviedb.org/
 *
 * Copyright (c) 2017.
 */

package android.example.com.popularmovies.config;

/**
 * Created by ByteTonight on 28.10.2017.
 */

public class Config {
    public static final String API_ENDPOINT = "https://api.themoviedb.org/3/";
    public static final String IMAGE_API_ENDPOINT = "http://image.tmdb.org/t/p/";
    public static final String POSTER_THUMBNAIL_SIZE = "w92/";
    public static final String POSTER_DEFAULT_SIZE = "w154/";
    public static final String BACKDROP_DEFAULT_SIZE = "w780/";
    public static final int RECYCLERVIEW_COLUMNS_PORTRAIT = 3;
    public static final int RECYCLERVIEW_COLUMNS_LAND = 5;
    public static final int READ_TIMEOUT_MS = 10000;
    public static final int CONNECT_TIMEOUT_MS = 15000;
}
