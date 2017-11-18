/*
 * Copyright (c) 2017 bytetonight@gmail.com
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

package android.example.com.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.example.com.popularmovies.BR;
import android.example.com.popularmovies.R;
import android.example.com.popularmovies.database.FavContract;
import android.example.com.popularmovies.database.FavContract.FavEntry;
import android.example.com.popularmovies.databinding.FavoritesListItemBinding;
import android.example.com.popularmovies.handlers.MediaItemHandlers;
import android.example.com.popularmovies.models.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by ByteTonight on 02.07.2017.
 *
 * http://emuneee.com/blog/2016/01/10/cursors-recyclerviews-and-itemanimators/
 * @Google : Why make things easy is you can make them complicated, right ?
 */

public class RecyclerFavoritesCursorAdapter
        extends CursorRecyclerViewAdapter<RecyclerFavoritesCursorAdapter.ViewHolder> {


    public RecyclerFavoritesCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        FavoritesListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.favorites_list_item, parent, false);
        binding.setHandlers(new MediaItemHandlers());
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor cursor) {

            Movie movie = new Movie();

            int idIndex = cursor.getColumnIndex(FavContract.FavEntry._ID);
            int movieIdIndex = cursor.getColumnIndex(FavEntry.TMDB_MOVIE_ID);
            int nameIndex = cursor.getColumnIndex(FavEntry.COLUMN_MOVIE_NAME);
            int imageIndex = cursor.getColumnIndex(FavEntry.COLUMN_MOVIE_POSTER_URL);


            //Check if the columns actually exist
            if (idIndex > -1)
                movie.setDatabaseId(cursor.getInt(idIndex));

            if (movieIdIndex > -1)
                movie.setId(cursor.getInt(movieIdIndex));

            if (nameIndex > -1)
                movie.setOriginalTitle(cursor.getString(nameIndex));

            if (imageIndex > -1)
                movie.setPosterPath(cursor.getString(imageIndex));

            // Movie movie = Movie.fromCursor(null, cursor);
        holder.bind(movie);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        /**
         * @param binding of type ViewDataBinding which is an
         *                abstract Base Class for generated binding classes
         */
        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Object obj) {
            binding.setVariable(BR.mediaItem, obj);
            binding.executePendingBindings();
        }
    }
}
