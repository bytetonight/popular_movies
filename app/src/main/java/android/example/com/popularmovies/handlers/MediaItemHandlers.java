package android.example.com.popularmovies.handlers;

import android.content.Context;
import android.content.Intent;
import android.example.com.popularmovies.DetailsActivity;
import android.example.com.popularmovies.R;
import android.example.com.popularmovies.models.AbstractMedia;
import android.view.View;


/**
 * Created by ByteTonight on 05.06.2017.
 */

public class MediaItemHandlers {

    public void onClickLearnMore(View v, AbstractMedia absMedia) {
        Context context = v.getContext(); //quite cool hey ?
        Intent mediaDetails = new Intent(context, DetailsActivity.class);
        mediaDetails.putExtra("absMedia", absMedia);
        context.startActivity(mediaDetails);
    }

    public void sharePoi(View v, AbstractMedia absMedia) {
        Context context = v.getContext();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, absMedia.getName());
        sendIntent.setType("text/plain");
        context.startActivity(
                Intent.createChooser(
                        sendIntent, context.getString(R.string.share_with)));
    }
}
