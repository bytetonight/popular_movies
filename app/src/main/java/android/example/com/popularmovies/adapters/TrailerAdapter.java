/*
 * PopularMovies by bytetonight
 * Created for the Udacity (c) Android (c) Developer Nanodegree
 * This software uses a remote API kindly made accessible by
 * https://www.themoviedb.org/
 *
 * Copyright (c) 2017.
 */

package android.example.com.popularmovies.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.example.com.popularmovies.R;
import android.example.com.popularmovies.databinding.TrailerListItemBinding;
import android.example.com.popularmovies.models.MovieTrailer;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by ByteTonight on 18.11.2017.
 */

public class TrailerAdapter extends ArrayAdapter
{
    private List<MovieTrailer> movieTrailers;
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public TrailerAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        movieTrailers = objects;
    }

    public void setMovieTrailers(List<MovieTrailer> movieTrailers) {
        this.movieTrailers = movieTrailers;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrailerListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                 R.layout.trailer_list_item, parent, false);
        binding.setTrailer(movieTrailers.get(position));

        return binding.getRoot();
    }

    @Override
    public int getCount() {
       if (movieTrailers == null)
           return 0;
        return movieTrailers.size();
        //return super.getCount();
    }
}
