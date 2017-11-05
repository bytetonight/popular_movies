package android.example.com.popularmovies.models;

import android.databinding.BindingAdapter;
import android.example.com.popularmovies.R;
import android.example.com.popularmovies.config.Config;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
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

public class Movie implements AbstractMedia, Parcelable
{

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
    }

    public Movie() {
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
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
     * Prepares loading of poster images with a width of 342px
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

    /**
     * This is the actual loading section for above 2 methods
     * Why I did it this way ?
     * To reduce redundant code of course
     *
     * @param imageView is the View to load the image into
     * @param imageUri is the remote resource location of the image
     * @param colorize determines whether I'm loading a poster or a backdrop image ... sort of
     */
    private static void glideImageLoader(final ImageView imageView, String imageUri, final boolean colorize) {
        final ProgressBar loadingIndicator = imageView.getRootView().findViewById(R.id.loading_indicator);

        Log.v("Movie", imageUri);
        Glide.with(imageView.getContext())
                .load( imageUri)
                // Glide V3 code
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model,
                                               Target<GlideDrawable> target,
                                               boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache,
                                                   boolean isFirstResource) {
                        loadingIndicator.setVisibility(View.GONE);
                        if (colorize)
                            imageView.setColorFilter(Color.LTGRAY, PorterDuff.Mode.DARKEN);
                        return false;
                    }
                })
                .into(imageView);
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
    }

    public int describeContents() {
        return 0;
    }

}