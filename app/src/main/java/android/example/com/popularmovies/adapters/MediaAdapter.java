package android.example.com.popularmovies.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.example.com.popularmovies.BR;
import android.example.com.popularmovies.R;
import android.example.com.popularmovies.databinding.MediaListItemBinding;
import android.example.com.popularmovies.handlers.MediaItemHandlers;
import android.example.com.popularmovies.models.AbstractMedia;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    private List<AbstractMedia> items;


    public MediaAdapter(List<AbstractMedia> items) {
        this.items = items;
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
        holder.bind(items.get(position));
    }


    @Override
    public int getItemCount() {
        return items.size();
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
