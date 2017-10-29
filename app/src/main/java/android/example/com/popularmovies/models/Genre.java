package android.example.com.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ByteTonight on 28.10.2017.
 */

public class Genre  implements Parcelable{

    public static final Creator<Genre> CREATOR =
            new Creator<Genre>() {

                @Override
                public Genre createFromParcel(Parcel source) {
                    return new Genre(source);
                }

                @Override
                public Genre[] newArray(int size) {
                    return new Genre[size];
                }
            };

    private int id;
    private String name;

    public Genre (Parcel parcel) {

    }

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
