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
import android.example.com.popularmovies.databinding.ReviewListItemBinding;
import android.example.com.popularmovies.models.Review;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by ByteTonight on 18.11.2017.
 */

public class ReviewAdapter extends ArrayAdapter
{
    private List<Review> reviews;

    public ReviewAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        reviews = objects;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReviewListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(getContext()),
                 R.layout.review_list_item, parent, false);
        binding.setReview(reviews.get(position));

        return binding.getRoot();
    }

    @Override
    public int getCount() {
       if (reviews == null)
           return 0;
        return reviews.size();
        //return super.getCount();
    }
}
