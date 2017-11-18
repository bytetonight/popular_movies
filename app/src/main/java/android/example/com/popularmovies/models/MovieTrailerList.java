/*
 * PopularMovies by bytetonight
 * Created for the Udacity (c) Android (c) Developer Nanodegree
 * This software uses a remote API kindly made accessible by
 * https://www.themoviedb.org/
 *
 * Copyright (c) 2017.
 */

package android.example.com.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ByteTonight on 18.11.2017.
 */

public class MovieTrailerList implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MovieTrailer> results = null;
    public final static Parcelable.Creator<MovieTrailerList> CREATOR = new Creator<MovieTrailerList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MovieTrailerList createFromParcel(Parcel in) {
            return new MovieTrailerList(in);
        }

        public MovieTrailerList[] newArray(int size) {
            return (new MovieTrailerList[size]);
        }

    }
            ;

    protected MovieTrailerList(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.results, (android.example.com.popularmovies.models.MovieTrailer.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public MovieTrailerList() {
    }

    /**
     *
     * @param id
     * @param results
     */
    public MovieTrailerList(Integer id, List<MovieTrailer> results) {
        super();
        this.id = id;
        this.results = results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieTrailer> getResults() {
        return results;
    }

    public void setResults(List<MovieTrailer> results) {
        this.results = results;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(results);
    }

    public int describeContents() {
        return 0;
    }

}