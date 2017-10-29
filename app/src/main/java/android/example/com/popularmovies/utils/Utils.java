package android.example.com.popularmovies.utils;

import android.content.Context;
import android.example.com.popularmovies.R;
import android.example.com.popularmovies.models.Collection;
import android.example.com.popularmovies.models.Movie;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ByteTonight on 05.06.2017.
 */

public class Utils {
    private static final String LOG_TAG = Utils.class.getName();
    private static final int READ_TIMEOUT_MS = 10000;
    private static final int CONNECT_TIMEOUT_MS = 15000;

    private static final String ARG_BOOK = "arg_book";
    private static final String KEY_RESULTS = "results";
    private static final String KEY_VOLUME_INFO = "volumeInfo";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_AUTHORS = "authors";
    private static final String KEY_IMAGE_LINKS = "imageLinks";
    private static final String KEY_THUMBNAIL = "thumbnail";

    /**
     * Returns new URL object from the given string URL.
     */
    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null)
            return jsonResponse;
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(READ_TIMEOUT_MS /* milliseconds */);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT_MS /* milliseconds */);
            urlConnection.connect();

            int resposeCode = urlConnection.getResponseCode();
            switch (resposeCode) {
                case 200:
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                    break;
                default:
                    Log.e(LOG_TAG, "Server responded with :" + resposeCode);
                    //do something
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error trying to fetch JSON data", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static Movie jsonToMovie(Context context, JSONObject jsonObject) throws JSONException {

        boolean isAdultRated = jsonObject.getBoolean("adult");
        String backdrop_path = jsonObject.getString("backdrop_path");
        JSONObject jsonCollection =
                !jsonObject.isNull("belongs_to_collection") ?
                        jsonObject.getJSONObject("belongs_to_collection") : null;
        if (jsonCollection != null) {

        }
        int id = jsonObject.getInt("id");

        Movie movie = new Movie(context);
        movie.setAdultRated(isAdultRated);
        movie.setBackdropPath(backdrop_path);

        return movie;
    }
    public static Movie getMovieByIdFromJSON(Context context, String response) {
        // If the JSON string is empty or null, then return early.
        if (response.isEmpty()) {
            return null;
        }


        try {
            JSONObject root = new JSONObject(response);

            //Uncomment the 2 lines below to raise a JSONException, and see how it's handled
            //if (true)
            //throw new JSONException("Some Element was not found... yada");

            int id = root.getInt("id");

            if (root.has(KEY_RESULTS)) {
                JSONArray resultsArray = root.getJSONArray(KEY_RESULTS); //JSON Node named items
                for (int i = 0; i < resultsArray.length(); ++i) {

                    JSONObject currentResult = resultsArray.getJSONObject(i);


                    JSONObject volumeInfo = currentResult.getJSONObject(KEY_VOLUME_INFO);

                    String title = volumeInfo.getString(KEY_TITLE);


                    List<String> authors = new ArrayList<String>();

                    if (volumeInfo.has(KEY_AUTHORS)) {
                        JSONArray authorsArray = volumeInfo.getJSONArray(KEY_AUTHORS);
                        for (int j = 0; j < authorsArray.length(); j++) {
                            authors.add(authorsArray.getString(j));
                        }
                    }

                    String description = "";

                    if (volumeInfo.has(KEY_DESCRIPTION)) {
                        description = volumeInfo.getString(KEY_DESCRIPTION);
                    }

                    String thumbnailLink = "";
                    if (volumeInfo.has(KEY_IMAGE_LINKS)) {
                        JSONObject imageLinks = volumeInfo.getJSONObject(KEY_IMAGE_LINKS);
                        thumbnailLink = imageLinks.getString(KEY_THUMBNAIL);
                    }
                    Movie movie = new Movie(context, title, authors, description, thumbnailLink);

                    movies.add(movie);
                }
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, context.getString(R.string.json_error), e);
            EventBus.getDefault().post(new
                    MessageEvent(context.getString(R.string.json_error)+
                    "\n"+e.getMessage()));
        }
        return movies;
    }

    public static ArrayList<Movie> getMoviesFromJSON(Context context, String response) {
        // If the JSON string is empty or null, then return early.
        if (response.isEmpty()) {
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(response);

             //Uncomment the 2 lines below to raise a JSONException, and see how it's handled
            //if (true)
            //throw new JSONException("Some Element was not found... yada");



            if (root.has(KEY_RESULTS)) {
                JSONArray resultsArray = root.getJSONArray(KEY_RESULTS); //JSON Node named items
                for (int i = 0; i < resultsArray.length(); ++i) {

                    JSONObject currentResult = resultsArray.getJSONObject(i);
                    int id = currentResult.getInt("id");

                    JSONObject volumeInfo = currentResult.getJSONObject(KEY_VOLUME_INFO);

                    String title = volumeInfo.getString(KEY_TITLE);


                    List<String> authors = new ArrayList<String>();

                    if (volumeInfo.has(KEY_AUTHORS)) {
                        JSONArray authorsArray = volumeInfo.getJSONArray(KEY_AUTHORS);
                        for (int j = 0; j < authorsArray.length(); j++) {
                            authors.add(authorsArray.getString(j));
                        }
                    }

                    String description = "";

                    if (volumeInfo.has(KEY_DESCRIPTION)) {
                        description = volumeInfo.getString(KEY_DESCRIPTION);
                    }

                    String thumbnailLink = "";
                    if (volumeInfo.has(KEY_IMAGE_LINKS)) {
                        JSONObject imageLinks = volumeInfo.getJSONObject(KEY_IMAGE_LINKS);
                        thumbnailLink = imageLinks.getString(KEY_THUMBNAIL);
                    }
                    Movie movie = new Movie(context, title, authors, description, thumbnailLink);

                    movies.add(movie);
                }
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, context.getString(R.string.json_error), e);
            EventBus.getDefault().post(new
                    MessageEvent(context.getString(R.string.json_error)+
                    "\n"+e.getMessage()));
        }
        return movies;
    }

    public static boolean isWifi(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
        return isWiFi;
    }

    public static boolean hasConnection(Context context)
    {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

}
