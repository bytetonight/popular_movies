/*
 * PopularMovies by bytetonight
 * Created for the Udacity (c) Android (c) Developer Nanodegree
 * This software uses a remote API kindly made accessible by
 * https://www.themoviedb.org/
 *
 * Copyright (c) 2017.
 */

package android.example.com.popularmovies.models;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.example.com.popularmovies.BR;
import android.example.com.popularmovies.R;
import android.example.com.popularmovies.adapters.ReviewAdapter;
import android.example.com.popularmovies.adapters.TrailerAdapter;
import android.example.com.popularmovies.config.Config;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Complete representation (model) of a Movie with all its properties as found in the JSON results
 * Created with http://www.jsonschema2pojo.org/
 * because why have things the hard way if you can have them easy ?
 * For the purpose of this particular App the model may be overkill
 * Never the less a great demonstration
 */

public class Movie extends BaseObservable implements AbstractMedia, Parcelable
{
    private int databaseId = -1;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("belongs_to_collection")
    @Expose
    private BelongsToCollection belongsToCollection;
    @SerializedName("budget")
    @Expose
    private Integer budget;
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("production_companies")
    @Expose
    private List<ProductionCompany> productionCompanies = null;
    @SerializedName("production_countries")
    @Expose
    private List<ProductionCountry> productionCountries = null;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("revenue")
    @Expose
    private Integer revenue;
    @SerializedName("runtime")
    @Expose
    private Integer runtime;
    @SerializedName("spoken_languages")
    @Expose
    private List<SpokenLanguage> spokenLanguages = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    public final static Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return (new Movie[size]);
        }

    };

    @Expose
    private List<MovieTrailer> trailerList = null;

    @Expose
    private ReviewResults reviewResults = null;

    protected Movie(Parcel in) {
        this.adult = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
        this.belongsToCollection = ((BelongsToCollection) in.readValue((BelongsToCollection.class.getClassLoader())));
        this.budget = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.genres, (android.example.com.popularmovies.models.Genre.class.getClassLoader()));
        this.homepage = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.imdbId = ((String) in.readValue((String.class.getClassLoader())));
        this.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
        this.originalTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.popularity = ((Double) in.readValue((Double.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.productionCompanies, (android.example.com.popularmovies.models.ProductionCompany.class.getClassLoader()));
        in.readList(this.productionCountries, (android.example.com.popularmovies.models.ProductionCountry.class.getClassLoader()));
        this.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
        this.revenue = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.runtime = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.spokenLanguages, (android.example.com.popularmovies.models.SpokenLanguage.class.getClassLoader()));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.tagline = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.video = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.voteAverage = ((Double) in.readValue((Double.class.getClassLoader())));
        this.voteCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.databaseId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.trailerList, (android.example.com.popularmovies.models.MovieTrailer.class.getClassLoader()));
    }

    public Movie() {
    }



    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    @Bindable
    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        notifyPropertyChanged(BR.backdropPath);
    }

    public BelongsToCollection getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(BelongsToCollection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    /**
     * This is turning into spaghetti code. Perhaps I need to better use a RecyclerView again
     * What happens here ?
     * items in the annotation is an attribute of the ListView
     * app:trailer_items="@{mediaItem.trailerList}"
     * So when the layout is bound, we land here
     * @param view
     * @param list
     */
    @BindingAdapter("bind:trailer_items")
    public static void bindTrailerList(ListView view, final List<MovieTrailer> list) {
        final Context context = view.getContext();
        TrailerAdapter trailerAdapter = new TrailerAdapter(view.getContext(), 0,  list);
        view.setAdapter(trailerAdapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieTrailer movieTrailer = list.get(position);
                Toast.makeText(view.getContext(), movieTrailer.getName(), Toast.LENGTH_SHORT).show();
                Intent applicationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + movieTrailer.getKey()));

                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + movieTrailer.getKey()));
                try {
                    context.startActivity(applicationIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(browserIntent);
                }
            }
        });
    }

    @BindingAdapter("bind:review_items")
    public static void bindReviewList(ListView view, final List<Review> list) {
        final Context context = view.getContext();
        ReviewAdapter reviewAdapter = new ReviewAdapter(view.getContext(), 0,  list);
        view.setAdapter(reviewAdapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Review review = list.get(position);
                Toast.makeText(view.getContext(), review.getUrl(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(review.getUrl()));
                context.startActivity(i);
            }
        });
    }

    @BindingAdapter("bind:review_items2")
    public static void bindReviewList2(LinearLayout view, final List<Review> list) {
        if (list == null)
            return;
        final Context context = view.getContext();
        for (final Review review : list) {
            TextView tv = new TextView(context);
            tv.setTextColor(Color.WHITE);
            tv.setText(review.getContent());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), review.getUrl(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(review.getUrl()));
                    context.startActivity(i);
                }
            });
            view.addView(tv);
        }

    }

    @Bindable
    public List<MovieTrailer> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<MovieTrailer> trailerList) {
        this.trailerList = trailerList;
        notifyPropertyChanged(BR.trailerList);
    }

    @Bindable
    public ReviewResults getReviewResults() {
        return reviewResults;
    }

    public void setReviewResults(ReviewResults reviewResults) {
        this.reviewResults = reviewResults;
        notifyPropertyChanged(BR.reviewResults);
    }

    @Bindable
    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
        notifyPropertyChanged(BR.databaseId);
    }


    @BindingAdapter("bind:posterThumbNailUrl")
    public static void getPosterThumbNail(ImageView imageView, String imageUri) {
        if (imageUri == null || imageUri.equals(""))
            return;
        glideImageLoader(imageView, Config.IMAGE_API_ENDPOINT + Config.POSTER_THUMBNAIL_SIZE + imageUri, false);

    }

    /**
     * Prepares loading of poster images with a width of {@link Config} POSTER_DEFAULT_SIZE px
     * When an ImageView in the XML layout contains the attribute app:posterUrl
     * the binder will know due to the annotation @BindingAdapter("bind:posterUrl")
     * to call getPosterImage and pass the value of above attribute as a parameter.
     *
     * @param imageView is the View to load the image into
     * @param imageUri is the remote resource location of the image
     */
    @BindingAdapter("bind:posterUrl")
    public static void getPosterImage(ImageView imageView, String imageUri) {
        if (imageUri == null || imageUri.equals(""))
            return;
        glideImageLoader(imageView, Config.IMAGE_API_ENDPOINT + Config.POSTER_DEFAULT_SIZE + imageUri, false);

    }

    /**
     * Prepares loading of backdrop images with a width of 700 and something px
     *
     * @param imageView is the View to load the image into
     * @param imageUri is the remote resource location of the image
     */
    @BindingAdapter("bind:backdropUrl")
    public static void getBackdropImage(ImageView imageView, String imageUri) {
        if (imageUri == null || imageUri.equals(""))
            return;
        glideImageLoader(imageView, Config.IMAGE_API_ENDPOINT + Config.BACKDROP_DEFAULT_SIZE + imageUri, true);

    }


    private static int posterImageVibrantColor = -1;

    @Bindable
    public static int getPosterImageVibrantColor() {
        return posterImageVibrantColor;
    }

    /**
     * This is the actual loading section for above 2 methods
     * Why I did it this way ?
     * To reduce redundant code of course
     *
     * @param imageView is the View to load the image into
     * @param imageUri is the remote resource location of the image
     * @param isBackdrop determines whether I'm loading a poster or a backdrop image ... sort of
     */
    private static void glideImageLoader(final ImageView imageView, String imageUri, final boolean isBackdrop) {
        final ProgressBar loadingIndicator = imageView.getRootView().findViewById(R.id.loading_indicator);
        final View pseudoFilter = imageView.getRootView().findViewById(R.id.pseudoFilter);
        Log.v("Movie", imageUri);
        final Context context = imageView.getContext();

        // Glide V3 code
        Glide.with(context)
                .load( imageUri)
                .asBitmap()
                .dontAnimate()
                //.centerCrop()
                //.thumbnail(.1f)

                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<Bitmap> target,
                                               boolean isFirstResource) {
                        Log.v("glideImageLoader", e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model,
                                                   Target<Bitmap> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {


                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette p) {
                                // Use generated instance
                                if (isBackdrop) {
                                    //imageView.setColorFilter(posterImageVibrantColor, PorterDuff.Mode.DARKEN);
                                    pseudoFilter.setBackgroundColor(posterImageVibrantColor);
                                } else {
                                    if (context.getClass().getSimpleName().contains("DetailsActivity")) {
                                        posterImageVibrantColor = p.getDominantColor(Color.BLACK);
                                    }
                                }

                            }
                        });

                        loadingIndicator.setVisibility(View.GONE);
                        if (isBackdrop)
                            imageView.setColorFilter(Color.LTGRAY, PorterDuff.Mode.DARKEN);
                        return false;
                    }
                })
                .into(imageView);

        /*Glide.with(context)
                .asBitmap()
                .load( imageUri)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette p) {
                                // Use generated instance
                                if (colorize) {
                                    //imageView.setColorFilter(posterImageVibrantColor, PorterDuff.Mode.DARKEN);
                                    pseudoFilter.setBackgroundColor(posterImageVibrantColor);
                                } else {
                                    if (context.getClass().getSimpleName().contains("DetailsActivity")) {
                                        posterImageVibrantColor = p.getDarkVibrantColor(Color.BLACK);
                                    }
                                }

                            }
                        });

                        loadingIndicator.setVisibility(View.GONE);
                    }
                });*/
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(adult);
        dest.writeValue(backdropPath);
        dest.writeValue(belongsToCollection);
        dest.writeValue(budget);
        dest.writeList(genres);
        dest.writeValue(homepage);
        dest.writeValue(id);
        dest.writeValue(imdbId);
        dest.writeValue(originalLanguage);
        dest.writeValue(originalTitle);
        dest.writeValue(overview);
        dest.writeValue(popularity);
        dest.writeValue(posterPath);
        dest.writeList(productionCompanies);
        dest.writeList(productionCountries);
        dest.writeValue(releaseDate);
        dest.writeValue(revenue);
        dest.writeValue(runtime);
        dest.writeList(spokenLanguages);
        dest.writeValue(status);
        dest.writeValue(tagline);
        dest.writeValue(title);
        dest.writeValue(video);
        dest.writeValue(voteAverage);
        dest.writeValue(voteCount);
        dest.writeValue(databaseId);
    }

    public int describeContents() {
        return 0;
    }

}