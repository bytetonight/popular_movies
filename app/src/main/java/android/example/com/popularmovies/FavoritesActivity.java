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
import android.content.CursorLoader;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.example.com.popularmovies.adapters.RecyclerFavoritesCursorAdapter;
import android.example.com.popularmovies.config.Config;
import android.example.com.popularmovies.database.FavContract.FavEntry;
import android.example.com.popularmovies.models.MovieListingPreference;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
    private static final int MOVIES_LOADER_ID = 0;
    private static final int COUNT_LOADER_ID = 1;

    private List<MovieListingPreference> movieListingPreferences = new ArrayList<>();
    private RecyclerFavoritesCursorAdapter mediaAdapter;
    private int recordsInDatabase;
    String whereCondition = FavEntry.COLUMN_MOVIE_NAME + " LIKE ?";
    String[] whereArgs = {"%"};

    /**
     * What to display if no data is available
     **/
    View emptyView;
    View noResultsView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.title_favorites));


        int columnCount = 2;
        switch (getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                columnCount = Config.RECYCLERVIEW_COLUMNS_PORTRAIT;
                break;

            case Configuration.ORIENTATION_LANDSCAPE:
                columnCount = Config.RECYCLERVIEW_COLUMNS_LAND;
                break;
        }



        mediaAdapter = new RecyclerFavoritesCursorAdapter(this, null);
        recyclerView = findViewById(R.id.movies_recyclerview);

        // Using a GridLayoutManager for columns instead of the default LinearLayoutManager
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(FavoritesActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mediaAdapter);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Kick off the loader to get the amount of records in the database
        getLoaderManager().initLoader(COUNT_LOADER_ID, null, this);
        // When {@link onLoadFinished} tells us that above loader has completed
        // We kick off the actual product loader
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
       /* outState.putString("moviePreference", moviePreference);
        outState.putParcelable("movieResults", movieResults);
        Log.d(TAG, "onSaveInstanceState: ");*/
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        /*moviePreference = savedInstanceState.getString("moviePreference");
        Log.d(TAG, "onRestoreInstanceState: ");*/
    }




    /*@Override
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
    }*/


    /**
     * Use hardcoded string-arrays to build a key -> value style List for the Spinner
     */
    /*private void prepareDiscoveryPreferences() {
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
*/
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Loader<Cursor> returnCursor = null;
        String[] projection;
        switch (i) {
            case COUNT_LOADER_ID:
                projection = new String[]{"COUNT(*)"};
                returnCursor = new CursorLoader(this,   // Parent activity context
                        FavEntry.CONTENT_URI,       // Provider content URI to query
                        projection,                     // Columns to include in the resulting Cursor
                        null,                           // No selection clause
                        null,                           // No selection arguments
                        null);
                break;

            case MOVIES_LOADER_ID:
                // Define a projection that specifies the columns from the table we care about.
                projection = new String[]{
                        FavEntry._ID,
                        FavEntry.TMDB_MOVIE_ID,
                        FavEntry.COLUMN_MOVIE_NAME,
                        FavEntry.COLUMN_MOVIE_POSTER_URL,
                        };


                // loader will execute the ContentProvider's query method on a background thread
                returnCursor = new CursorLoader(this,// Parent activity context
                        FavEntry.CONTENT_URI,   // Provider content URI to query
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
                getLoaderManager().initLoader(MOVIES_LOADER_ID, null, this);
                break;

            case MOVIES_LOADER_ID:
                mediaAdapter.swapCursor(data);

                // Race condition means : even though the count loader is kicked off first,
                // the product loader CAN complete first due to async operations
                // before recordsInDatabase has been initialized
                if (recordsInDatabase > 0) {
                    // We have one or more records
                   /* emptyView.setVisibility(View.GONE);
                    noResultsView.setVisibility(data.getCount() > 0 ? View.GONE : View.VISIBLE);
                    recyclerView.setVisibility(data.getCount() > 0 ? View.VISIBLE : View.GONE);*/
                } else {
                    // We have zero records
                   /* noResultsView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);*/
                }
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mediaAdapter.swapCursor(null);
    }
}
