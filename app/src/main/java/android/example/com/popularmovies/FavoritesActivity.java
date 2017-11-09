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
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{


    // Useful information
    // https://medium.com/@cassioso/a-strategy-to-secure-your-api-keys-using-gradle-b9c107272860
    // https://developers.themoviedb.org/3/discover/movie-discover ... sort_by ... popularity !
    // https://futurestud.io/tutorials/glide-placeholders-fade-animations

    /**
     * Requires a file named keystore.properties to exist within the project root
     * having the following content
     * TmbdApiKey="INSERT YOUR API KEY HERE"
     */
    private static final String TAG = FavoritesActivity.class.getName();

    /**
     * Identifier for the product data loader
     */
    private static final int PRODUCTS_LOADER_ID = 0;
    private static final int COUNT_LOADER_ID = 1;

    private List<MovieListingPreference> movieListingPreferences = new ArrayList<>();
    private RestAdapter restAdapter;
    private MediaAdapter mediaAdapter;
    private MovieResults movieResults;
    private String moviePreference;
    private int moviePreferencePosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        restAdapter = new RestAdapter();
        if (savedInstanceState != null) {
            movieResults = savedInstanceState.getParcelable("movieResults");
        } else {
            movieResults = new MovieResults();
        }

        mediaAdapter = new MediaAdapter(movieResults);
        RecyclerView recyclerView = findViewById(R.id.movies_recyclerview);

        // Using a GridLayoutManager for columns instead of the default LinearLayoutManager
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(FavoritesActivity.this, columnCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mediaAdapter);

        if (savedInstanceState == null)
            listMoviesByPreference();


        // Kick off the loader to get the amount of records in the database
        getLoaderManager().initLoader(COUNT_LOADER_ID, null, this);
        // When {@link onLoadFinished} tells us that above loader has completed
        // We kick off the actual product loader
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
        try {
            Call<MovieResults> discoverMoviesCall =
                    restAdapter.getInstance(FavoritesActivity.this)
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
                    Toast.makeText(FavoritesActivity.this,
                            getString(R.string.toast_load_movies_fail) + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch (NoConnectionException ex) {
            Toast.makeText(FavoritesActivity.this,
                    R.string.toast_no_internet, Toast.LENGTH_SHORT).show();
        }
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
                new ArrayAdapter<>(FavoritesActivity.this,
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
                    Utils.writeStringToPreferences(FavoritesActivity.this,
                            "moviePreference", currentItem.getKey());
                    Utils.writeStringToPreferences(FavoritesActivity.this,
                            "moviePreferenceNumeric", String.valueOf(position));
                    moviePreferencePosition = position;
                    moviePreference = currentItem.getKey();
                    listMoviesByPreference();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Loader<Cursor> returnCursor = null;
        String[] projection;
        switch (i) {
            case COUNT_LOADER_ID:
                projection = new String[]{"COUNT(*)"};
                returnCursor = new CursorLoader(this,   // Parent activity context
                        ProductEntry.CONTENT_URI,       // Provider content URI to query
                        projection,                     // Columns to include in the resulting Cursor
                        null,                           // No selection clause
                        null,                           // No selection arguments
                        null);
                break;

            case PRODUCTS_LOADER_ID:
                // Define a projection that specifies the columns from the table we care about.
                projection = new String[]{
                        ProductEntry._ID,
                        ProductEntry.COLUMN_PRODUCT_NAME,
                        ProductEntry.COLUMN_PRODUCT_PRICE,
                        ProductEntry.COLUMN_PRODUCT_IMAGE,
                        ProductEntry.COLUMN_PRODUCT_QUANTITY};


                // loader will execute the ContentProvider's query method on a background thread
                returnCursor = new CursorLoader(this,// Parent activity context
                        ProductEntry.CONTENT_URI,   // Provider content URI to query
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
        // Update {@link ProductCursorAdapter} with this new cursor containing updated product data
        switch (loader.getId()) {

            case COUNT_LOADER_ID:
                if (data.moveToFirst()) {
                    recordsInDatabase = data.getInt(0);
                    Toast.makeText(this, String.valueOf(recordsInDatabase) +
                            getString(R.string.records_in_database), Toast.LENGTH_SHORT).show();
                }
                // Decided to make the call to the products loader after
                // the count completes due to race condition
                getLoaderManager().initLoader(PRODUCTS_LOADER_ID, null, this);
                break;

            case PRODUCTS_LOADER_ID:
                recyclerProductCursorAdapter.swapCursor(data);

                // Race condition means : even though the count loader is kicked off first,
                // the product loader CAN complete first due to async operations
                // before recordsInDatabase has been initialized
                if (recordsInDatabase > 0) {
                    // We have one or more records
                    emptyView.setVisibility(View.GONE);
                    noResultsView.setVisibility(data.getCount() > 0 ? View.GONE : View.VISIBLE);
                    recyclerView.setVisibility(data.getCount() > 0 ? View.VISIBLE : View.GONE);
                } else {
                    // We have zero records
                    noResultsView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        recyclerProductCursorAdapter.swapCursor(null);
    }
}
