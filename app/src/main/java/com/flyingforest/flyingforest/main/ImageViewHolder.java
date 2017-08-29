package com.flyingforest.flyingforest.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyingforest.flyingforest.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by fata on 8/28/2017.
 */

public class ImageViewHolder extends RecyclerView.ViewHolder {

    private final ImageView image;

    public ImageViewHolder(View itemView) {
        super(itemView);
        image = (ImageView)itemView.findViewById(R.id.item_image);
    }

    public void setView(String imageUrl, Context context) {
        Glide.with(context)
                .load(imageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
    }
}
