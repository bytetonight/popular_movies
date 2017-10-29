package android.example.com.popularmovies.data;

import android.content.Context;
import android.example.com.popularmovies.models.Movie;
import android.example.com.popularmovies.utils.Utils;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by ByteTonight on 10.06.2017.
 */

public class MovieByIdLoader extends AsyncTaskLoader<Movie> {

    String sourceUrl;


    public MovieByIdLoader(Context context, String url) {
        super(context);
        sourceUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Movie loadInBackground() {
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
        Movie movie = Utils.getMovieByIdFromJSON(getContext(), jsonResponse);

        return movie;
    }


}
