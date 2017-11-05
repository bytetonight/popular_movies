/*
 * PopularMovies by bytetonight
 * Created for the Udacity (c) Android (c) Developer Nanodegree
 * This software uses a remote API kindly made accessible by
 * https://www.themoviedb.org/
 *
 * Copyright (c) 2017.
 */

package android.example.com.popularmovies.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.example.com.popularmovies.BR;
import android.example.com.popularmovies.R;


import android.example.com.popularmovies.databinding.MediaListItemBinding;
import android.example.com.popularmovies.handlers.MediaItemHandlers;
import android.example.com.popularmovies.models.MovieResults;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private MovieResults results;


    public MediaAdapter(MovieResults results) {
        this.results = results;
    }

    /**
     * Used to set the data after the Adapter has been initialized
     * @param results
     */
    public void setResults(MovieResults results) {
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // was using ViewDataBinding binding but you can't assign handlers to the base
         MediaListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.media_list_item, parent, false);

        binding.setHandlers(new MediaItemHandlers()); //use my handlers class
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(results.getResults().get(position));
    }


    @Override
    public int getItemCount() {
        if (results != null && results.getResults() != null)
            return results.getResults().size();
        return 0;
    }


    public final class ViewHolder extends RecyclerView.ViewHolder {
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
