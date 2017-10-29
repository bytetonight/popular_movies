package android.example.com.popularmovies.models;

/**
 * Created by ByteTonight on 28.10.2017.
 */

public class Collection {
    private int id;
    private String posterPath;
    private String backdropPath;

    public Collection(int id, String posterPath, String backdropPath) {
        this.id = id;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }
}
