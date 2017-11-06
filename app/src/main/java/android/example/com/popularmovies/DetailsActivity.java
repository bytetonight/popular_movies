/*
 * PopularMovies by bytetonight
 * Created for the Udacity (c) Android (c) Developer Nanodegree
 * This software uses a remote API kindly made accessible by
 * https://www.themoviedb.org/
 *
 * Copyright (c) 2017.
 */

package android.example.com.popularmovies;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.example.com.popularmovies.data.PieChartWrapper;
import android.example.com.popularmovies.databinding.ActivityDetailsBinding;
import android.example.com.popularmovies.models.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * This Activity does not make a new API request because that seems quite redundant
 */
public class DetailsActivity extends AppCompatActivity {

    private Movie selectedMovie;
    private PieChartWrapper pieChartWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent senderIntent = getIntent();
        // There can never be no intent ... something lead us here BUT
        // Only if the indices are contained, they will be returned, else 0
        if (senderIntent.hasExtra("absMedia"))
            selectedMovie = senderIntent.getParcelableExtra("absMedia");

        if (savedInstanceState != null) {
            selectedMovie = savedInstanceState.getParcelable("selectedMovie");
        }

        if (selectedMovie == null) {
            return;
        }


        ActivityDetailsBinding binding = DataBindingUtil.setContentView(DetailsActivity.this, R.layout.activity_details);
        binding.setMediaItem(selectedMovie);

        pieChartWrapper = new PieChartWrapper(this, binding.chart);
        pieChartWrapper.setRatingValue(selectedMovie.getVoteAverage() * 10.0);
        setTitle(selectedMovie.getOriginalTitle());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("selectedMovie", selectedMovie);
    }
}
