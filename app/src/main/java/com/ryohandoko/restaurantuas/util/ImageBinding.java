package com.ryohandoko.restaurantuas.util;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ryohandoko.restaurantuas.R;

public class ImageBinding {
    @BindingAdapter("ProfileImage")
    public static void loadImage(ImageView view, String imgUrl) {
        Log.i("GLIDE", "loadImage: " + imgUrl);
        Glide.with(view.getContext())
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.bebek_vector))
                .load(imgUrl)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(view);
    }
}
