package android.example.com.popularmovies.data;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import android.example.com.popularmovies.utils.Utils;
import android.example.com.popularmovies.models.Movie;

/**
 * Created by ByteTonight on 10.06.2017.
 */

public class MoviesLoader extends AsyncTaskLoader<List<Movie>> {

    String sourceUrl;


    public MoviesLoader(Context context, String url) {
        super(context);
        sourceUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (sourceUrl == null) {
            return null;
        }
        // Create URL object
        URL url = Utils.createUrl(sourceUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        try {
            jsonResponse = Utils.makeHttpRequest(url);
        } catch (IOException e) {

        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        List<Movie> movies = Utils.getMoviesFromJSON(getContext(), jsonResponse);

        return movies;
    }


}
