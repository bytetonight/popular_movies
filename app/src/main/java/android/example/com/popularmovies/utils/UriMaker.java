package android.example.com.popularmovies.utils;

import android.example.com.popularmovies.BuildConfig;
import android.example.com.popularmovies.config.Config;
import android.net.Uri;

/**
 * Created by ByteTonight on 28.10.2017.
 */

public class UriMaker {

    public static String getTestUrl() {

        Uri baseUri = Uri.parse(Config.API_ENDPOINT);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendPath("movie"); // action
        uriBuilder.appendPath("550"); // id, in above case movie id
        uriBuilder.appendQueryParameter("api_key", BuildConfig.TMDB_API_KEY);
        //uriBuilder.appendQueryParameter("callback", "test"); //I guess this is only for Web callbacks
        return uriBuilder.toString();
     }
}
