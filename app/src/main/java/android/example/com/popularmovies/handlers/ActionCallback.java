/*
 * PopularMovies by bytetonight
 * Created for the Udacity (c) Android (c) Developer Nanodegree
 * This software uses a remote API kindly made accessible by
 * https://www.themoviedb.org/
 *
 * Copyright (c) 2017.
 */

package android.example.com.popularmovies.handlers;

import android.example.com.popularmovies.models.Movie;
import android.view.View;

/**
 * Created by ByteTonight on 12.11.2017.
 */

public interface ActionCallback {
    void onClickAddToFavorites(Movie movie);
    void onClickRemoveFromFavorites(Movie movie);
}
