/*
 * PopularMovies by bytetonight
 * Created for the Udacity (c) Android (c) Developer Nanodegree
 * This software uses a remote API kindly made accessible by
 * https://www.themoviedb.org/
 *
 * Copyright (c) 2017.
 */

package android.example.com.popularmovies.handlers;


import android.content.Context;
import android.content.Intent;
import android.example.com.popularmovies.DetailsActivity;
import android.example.com.popularmovies.models.AbstractMedia;
import android.example.com.popularmovies.models.Movie;
import android.view.View;



/**
 * Created by ByteTonight on 05.06.2017.
 */

public class MediaItemHandlers {

    // Todo Thorsten, investigate if setting a callback this way could be an advantage
    // https://stackoverflow.com/questions/41938671/databinding-button-onclick-not-working
    public void onClickViewDetails(View v, AbstractMedia absMedia) {
        Context context = v.getContext();
        Intent mediaDetails = new Intent(context, DetailsActivity.class);
        mediaDetails.putExtra("absMedia", absMedia);
        context.startActivity(mediaDetails);
    }
}
