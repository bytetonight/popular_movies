package android.example.com.popularmovies.adapters;

import android.accounts.NetworkErrorException;
import android.app.Application;
import android.content.Context;
import android.example.com.popularmovies.MainActivity;
import android.example.com.popularmovies.config.Config;
import android.example.com.popularmovies.data.TmdbAPI;
import android.example.com.popularmovies.exceptions.NoConnectionException;
import android.example.com.popularmovies.utils.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ByteTonight on 29.10.2017.
 */

public class RestAdapter  {

    private TmdbAPI tmdbAPI;

    public TmdbAPI getInstance(Context context) throws NoConnectionException {

        if (!Utils.hasConnection(context)) {
            throw new NoConnectionException();
        }
        if (tmdbAPI == null) {
            Gson gson = new GsonBuilder().create();

            OkHttpClient.Builder okHTTPClientBuilder = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            // use HttpLoggingInterceptor.Level.NONE to see constructed URLs for the requests

            okHTTPClientBuilder.addInterceptor(loggingInterceptor);
            okHTTPClientBuilder.connectTimeout(Config.CONNECT_TIMEOUT_MS, TimeUnit.MILLISECONDS);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Config.API_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHTTPClientBuilder.build())
                    .build();

            tmdbAPI = retrofit.create(TmdbAPI.class);
        }
        return tmdbAPI;
    }

}
