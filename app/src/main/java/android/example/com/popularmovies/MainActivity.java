/*
 * PopularMovies by bytetonight
 * Created for the Udacity (c) Android (c) Developer Nanodegree
 * This software uses a remote API kindly made accessible by
 * https://www.themoviedb.org/
 *
 * Copyright (c) 2017.
 */

package android.example.com.popularmovies;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.example.com.popularmovies.adapters.MediaAdapter;
import android.example.com.popularmovies.adapters.RestAdapter;
import android.example.com.popularmovies.config.Config;
import android.example.com.popularmovies.exceptions.NoConnectionException;
import android.example.com.popularmovies.models.MovieListingPreference;
import android.example.com.popularmovies.models.MovieResults;
import android.example.com.popularmovies.utils.Utils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    // Useful information
    // https://medium.com/@cassioso/a-strategy-to-secure-your-api-keys-using-gradle-b9c107272860
    // https://developers.themoviedb.org/3/discover/movie-discover ... sort_by ... popularity !
    // https://futurestud.io/tutorials/glide-placeholders-fade-animations

    /**
     * Requires a file named keystore.properties to exist within the project root
     * having the following content
     * TmbdApiKey="INSERT YOUR API KEY HERE"
     */
    private static final String TAG = MainActivity.class.getName();
    private ProgressBar mainProgressBar;
    private List<MovieListingPreference> movieListingPreferences = new ArrayList<>();
    private MediaAdapter mediaAdapter;
    private MovieResults movieResults;
    private String moviePreference;
    private int moviePreferencePosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainProgressBar = findViewById(R.id.main_progress_bar);

        prepareDiscoveryPreferences();
        moviePreference = Utils.readStringFromPreferences(this, "moviePreference");

        if (moviePreference == null || moviePreference.isEmpty() || moviePreference.equals("0"))
            moviePreference = ((MovieListingPreference) movieListingPreferences.get(0)).getKey();

        String posString = Utils.readStringFromPreferences(this,
                "moviePreferenceNumeric");

        if (posString != null && !posString.isEmpty())
            moviePreferencePosition = Integer.valueOf(posString);
        else
            moviePreferencePosition = 0;

        int columnCount = 2;
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                columnCount = Config.RECYCLERVIEW_COLUMNS_PORTRAIT;
                break;

            case Configuration.ORIENTATION_LANDSCAPE:
                columnCount = Config.RECYCLERVIEW_COLUMNS_LAND;
                break;
        }

        if (savedInstanceState != null) {
            movieResults = savedInstanceState.getParcelable("movieResults");
        } else {
            movieResults = new MovieResults();
        }

        mediaAdapter = new MediaAdapter(movieResults);
        RecyclerView recyclerView = findViewById(R.id.movies_recyclerview);

        // Using a GridLayoutManager for columns instead of the default LinearLayoutManager
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(MainActivity.this, columnCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mediaAdapter);

        if (savedInstanceState == null)
            listMoviesByPreference();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("moviePreference", moviePreference);
        outState.putParcelable("movieResults", movieResults);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        moviePreference = savedInstanceState.getString("moviePreference");
        Log.d(TAG, "onRestoreInstanceState: ");
    }


    /**
     * Wrapper for Retrrofit Call method to load movies Data
     */
    private void listMoviesByPreference() {
        mainProgressBar.setVisibility(View.VISIBLE);
        try {
            Call<MovieResults> discoverMoviesCall =
                    RestAdapter.getInstance(MainActivity.this)
                            .listMoviesByPreference(moviePreference, BuildConfig.TMDB_API_KEY);

            discoverMoviesCall.enqueue(new Callback<MovieResults>() {
                @Override
                public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                    if (response.isSuccessful()) {
                        movieResults = response.body();
                        mediaAdapter.setResults(movieResults);
                        mediaAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<MovieResults> call, Throwable t) {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.toast_load_movies_fail) + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch (NoConnectionException ex) {
            Toast.makeText(MainActivity.this,
                    R.string.toast_no_internet, Toast.LENGTH_SHORT).show();
        }
        mainProgressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.listing_preferences:
                showMoviePrefsDialog();
                return true;

            case R.id.favorites :
                startActivity(new Intent(this, FavoritesActivity.class), null);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Use hardcoded string-arrays to build a key -> value style List for the Spinner
     */
    private void prepareDiscoveryPreferences() {
        String[] prefKeysArray = getResources().getStringArray(R.array.movie_preferences_values);
        String[] prefValuesArray = getResources().getStringArray(R.array.movie_preference_name);
        for (int current = 0; current < prefKeysArray.length; ++current) {
            movieListingPreferences.add(new MovieListingPreference(prefKeysArray[current],
                    prefValuesArray[current]));
        }
    }

    private void showMoviePrefsDialog() {

        Log.v("###", "showMoviePrefsDialog");
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.preferences_dialog, null);


        Spinner listingPrefsSpinner = view.findViewById(R.id.movieDiscoveryPreference);

        listingPrefsSpinner.setOnItemSelectedListener(sectionSelectListener);

        ArrayAdapter<MovieListingPreference> sectionArrayAdapter =
                new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, movieListingPreferences);
        sectionArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listingPrefsSpinner.setAdapter(sectionArrayAdapter);
        dialogBuilder.setView(view);
        listingPrefsSpinner.setSelection(moviePreferencePosition);
        dialogBuilder.setPositiveButton(R.string.dlg_btn_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int clickedButton) {

            }
        });
        dialogBuilder.setNegativeButton(R.string.dlg_btn_cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int clickedButton) {

                    }
                });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private AdapterView.OnItemSelectedListener sectionSelectListener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    if (moviePreferencePosition == position)
                        return;
                    MovieListingPreference currentItem =
                            (MovieListingPreference) parent.getItemAtPosition(position);
                    Utils.writeStringToPreferences(MainActivity.this,
                            "moviePreference", currentItem.getKey());
                    Utils.writeStringToPreferences(MainActivity.this,
                            "moviePreferenceNumeric", String.valueOf(position));
                    moviePreferencePosition = position;
                    moviePreference = currentItem.getKey();
                    listMoviesByPreference();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };

}
