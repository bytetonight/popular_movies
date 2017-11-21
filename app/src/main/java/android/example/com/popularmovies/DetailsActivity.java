/*
 * PopularMovies by bytetonight
 * Created for the Udacity (c) Android (c) Developer Nanodegree
 * This software uses a remote API kindly made accessible by
 * https://www.themoviedb.org/
 *
 * Copyright (c) 2017.
 */

package android.example.com.popularmovies;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.example.com.popularmovies.adapters.RestAdapter;
import android.example.com.popularmovies.adapters.TrailerAdapter;
import android.example.com.popularmovies.data.PieChartWrapper;
import android.example.com.popularmovies.database.FavContract;
import android.example.com.popularmovies.databinding.ActivityDetailsBinding;
import android.example.com.popularmovies.exceptions.NoConnectionException;
import android.example.com.popularmovies.handlers.ActionCallback;
import android.example.com.popularmovies.handlers.MediaItemHandlers;
import android.example.com.popularmovies.models.Movie;
import android.example.com.popularmovies.models.MovieTrailerList;
import android.example.com.popularmovies.models.ReviewResults;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailsActivity extends AppCompatActivity
        implements ActionCallback, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CHECK_FAVORITES_LOADER = 1;
    private static final String SELECTED_MOVIE_BUNDLE_KEY = "selectedMovie";
    private Movie selectedMovie;
    private PieChartWrapper pieChartWrapper;
    private ActivityDetailsBinding binding;
    private TrailerAdapter trailerAdapter;
    private int databaseId = -1;
    private boolean movieFoundInDb = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent senderIntent = getIntent();


        if (senderIntent.hasExtra("absMedia")) {
            selectedMovie = senderIntent.getParcelableExtra("absMedia");
            loadMovieTrailerList(selectedMovie.getId());
            loadMovieReviewsList(selectedMovie.getId());
        }

        if (savedInstanceState != null) {
            selectedMovie = savedInstanceState.getParcelable(SELECTED_MOVIE_BUNDLE_KEY);

        }


        // If there is a databaseId then we came from FavoritesActivity
        // but we could be coming from MainActivity too requesting
        // details of a Movie that actually is a favorite
        // which will not have a databaseId
        // so we need to check if the movieId exists in database too
        if (selectedMovie.getDatabaseId() != -1) {
            // Storing the databaseId here for later use because loading the
            // complete properties from the API is going to
            // overwrite the current Movie instance
            databaseId = selectedMovie.getDatabaseId();
            loadMovieDetailsById(selectedMovie.getId());
        } else {
            getLoaderManager().initLoader(CHECK_FAVORITES_LOADER, null, this);
        }



        if (selectedMovie == null) {
            return;
        }



        binding = DataBindingUtil.setContentView(DetailsActivity.this, R.layout.activity_details);
        binding.setMediaItem(selectedMovie);
        binding.setHandlers(new MediaItemHandlers());
        binding.setActionCallback(this);


        // If this movie is a Favorite, hide the add to favorites button
        if (selectedMovie.getDatabaseId() != -1) {
            toggleAddFavorites(false);
        }

        if (selectedMovie.getVoteAverage() != null) {
            setUpPieChartRating();
        }


        setTitle(getString(R.string.title_details));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SELECTED_MOVIE_BUNDLE_KEY, selectedMovie);
    }

    /**
     * Method from {@link ActionCallback} Interface
     *
     * @param movie
     */
    @Override
    public void onClickAddToFavorites(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(FavContract.FavEntry.TMDB_MOVIE_ID, movie.getId());
        values.put(FavContract.FavEntry.COLUMN_MOVIE_NAME, movie.getOriginalTitle());
        values.put(FavContract.FavEntry.COLUMN_MOVIE_POSTER_URL, movie.getPosterPath());

        Uri newUri = getContentResolver().insert(
                FavContract.FavEntry.CONTENT_URI, values);

        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.insert_movie_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.insert_movie_successful),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method from {@link ActionCallback} Interface
     *
     * @param movie
     */
    @Override
    public void onClickRemoveFromFavorites(Movie movie) {
        int databaseId = movie.getDatabaseId();
        if (databaseId == -1)
            return;
        String deletableId = String.valueOf(databaseId);

        Uri toDeleteUri = ContentUris.withAppendedId(FavContract.FavEntry.CONTENT_URI, databaseId);
        int rowsDeleted = getContentResolver().delete(
                toDeleteUri,
                FavContract.FavEntry._ID,
                new String[]{deletableId}
        );

        if (rowsDeleted == 1) {
            // Success presuming that a database Id is a unique primary key
            onBackPressed();


        } else {
            // Who knows
        }
    }

    private void toggleAddFavorites(boolean visible) {
        if (visible) {
            binding.btnAddFavorite.setVisibility(View.VISIBLE);
            binding.btnRemoveFavorite.setVisibility(View.GONE);
        } else {
            binding.btnAddFavorite.setVisibility(View.GONE);
            binding.btnRemoveFavorite.setVisibility(View.VISIBLE);
        }
    }
    private void setUpPieChartRating() {
        pieChartWrapper = new PieChartWrapper(this, binding.chart);
        pieChartWrapper.setRatingValue(selectedMovie.getVoteAverage() * 10.0);
    }

    /**
     * Retrofit API call only used when displaying details for a Favorite
     * because any Movies selected in the MainActivity already
     * are fully populated in the MainActivity
     * @param id
     */
    private void loadMovieDetailsById(final int id) {
        Call<Movie> getMovieCall;
        try {
            getMovieCall = RestAdapter.getInstance(this).getMovieById(id, BuildConfig.TMDB_API_KEY);

            getMovieCall.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (response.isSuccessful()) {
                        selectedMovie = response.body();
                        selectedMovie.setDatabaseId(databaseId);
                        // Rebind
                        binding.setMediaItem(selectedMovie);
                        binding.invalidateAll();
                       /* binding.backdropImageView.invalidate();
                        binding.posterImageView.invalidate();*/

                        setUpPieChartRating();
                        toggleAddFavorites(false);

                        loadMovieTrailerList(selectedMovie.getId());
                        loadMovieReviewsList(selectedMovie.getId());
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Toast.makeText(DetailsActivity.this,
                            getString(R.string.toast_load_movies_fail) + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });

        } catch (NoConnectionException e) {
            displayConnectivityNotification();
        }
    }

    private void loadMovieTrailerList(int id) {
        Call<MovieTrailerList> getMovieTrailerListCall;
        try {
            getMovieTrailerListCall = RestAdapter.getInstance(this).getTrailersListByMovieId(id, BuildConfig.TMDB_API_KEY);

            getMovieTrailerListCall.enqueue(new Callback<MovieTrailerList>() {
                @Override
                public void onResponse(Call<MovieTrailerList> call, Response<MovieTrailerList> response) {
                    if (response.isSuccessful()) {
                        MovieTrailerList movieTrailerList = response.body();
                        selectedMovie.setTrailerList(movieTrailerList.getResults());
                        binding.invalidateAll();
                        //binding.setMediaItem(selectedMovie);
                        binding.notifyChange();
                    }
                }

                @Override
                public void onFailure(Call<MovieTrailerList> call, Throwable t) {
                    Toast.makeText(DetailsActivity.this,
                            getString(R.string.toast_load_movies_fail) + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });

        } catch (NoConnectionException e) {
            displayConnectivityNotification();
        }
    }

    private void loadMovieReviewsList(int id) {
        Call<ReviewResults> getReviewsCall;
        try {
            getReviewsCall = RestAdapter.getInstance(this).getReviewsByMovieId(id, BuildConfig.TMDB_API_KEY);

            getReviewsCall.enqueue(new Callback<ReviewResults>() {
                @Override
                public void onResponse(Call<ReviewResults> call, Response<ReviewResults> response) {
                    if (response.isSuccessful()) {
                        ReviewResults reviewResults = response.body();
                        selectedMovie.setReviewResults(reviewResults);
                        binding.invalidateAll();
                        //binding.setMediaItem(selectedMovie);
                        binding.notifyChange();
                    }
                }

                @Override
                public void onFailure(Call<ReviewResults> call, Throwable t) {
                    Toast.makeText(DetailsActivity.this,
                            getString(R.string.toast_load_movies_fail) + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });

        } catch (NoConnectionException e) {
            displayConnectivityNotification();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            //NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        Loader<Cursor> returnCursor = null;
        String[] projection;
        switch (loaderId) {


            case CHECK_FAVORITES_LOADER:
                projection = new String[]{
                        "COUNT(" + FavContract.FavEntry._ID + "), "+ FavContract.FavEntry._ID
                };
                String whereCondition = FavContract.FavEntry.TMDB_MOVIE_ID + " = ?";
                String[] whereArgs = {selectedMovie.getId().toString()};


                // loader will execute the ContentProvider's query method on a background thread
                returnCursor = new CursorLoader(this,// Parent activity context
                        FavContract.FavEntry.CONTENT_URI,   // Provider content URI to query
                        projection,                 // Columns to include in the resulting Cursor
                        whereCondition,             // example WHERE column = ?
                        whereArgs,                  // values for placeholders in above condition
                        null);                      // Default sort order
                break;
        }

        return returnCursor;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        switch (loader.getId()) {
            case CHECK_FAVORITES_LOADER:
                if (data.moveToFirst()) {
                    movieFoundInDb = data.getInt(0) > 0;

                    int databaseIdIndex = data.getColumnIndex(FavContract.FavEntry._ID);
                    if (databaseIdIndex != -1) {
                        selectedMovie.setDatabaseId(data.getInt(databaseIdIndex));
                        //binding.setMediaItem(selectedMovie);
                        binding.invalidateAll();
                        if (movieFoundInDb) {
                            toggleAddFavorites(false);
                        } else {
                            toggleAddFavorites(true);
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        //mediaAdapter.swapCursor(null);

    }

    private void displayConnectivityNotification() {
        Toast.makeText(this, getString(R.string.connectivity_issue),
                Toast.LENGTH_SHORT).show();
    }
}
