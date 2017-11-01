package android.example.com.popularmovies.models;

/**
 * Created by ByteTonight on 01.11.2017.
 */

/**
 * Just a simple POJO
 * The Movie Listing Preference Spinner uses a List of MovieListingPreference to display
 * translatable String values in the Spinner while giving me the ability to use corresponding keys
 */
public class MovieListingPreference {
    private String key;
    private String value;

    public MovieListingPreference(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return value;
    }
}
