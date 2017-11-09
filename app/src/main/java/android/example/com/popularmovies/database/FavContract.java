/*
 * Copyright (c) 2017. bytetonight@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.example.com.popularmovies.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * API Contract for the inventory app.
 */
public final class FavContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private FavContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.bytetonight.tmdb";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.virtual.warehouse/products/ is a valid path for
     * looking at product data. content://com.virtual.warehouse/something_else/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "something_else".
     */
    public static final String PATH_FAVORITES = "favorites";

    /**
     * Inner class that defines constant values for the products database table.
     * Each entry in the table represents a single product.
     */
    public static final class FavEntry implements BaseColumns {

        /**
         * The content URI to access the product data in the provider
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FAVORITES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of products.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single product.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITES;

        /**
         * Name of database table for products
         */
        public final static String TABLE_NAME = "tmdb_favorites";

        /**
         * Unique ID number for the movie (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Unique ID number for the movie (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String TMDB_MOVIE_ID = "movie_id";

        /**
         * Name of the product.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_MOVIE_NAME = "name";

        /**
         * Price of the product.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_MOVIE_POSTER_URL = "poster_url";

    }

}

