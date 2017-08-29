package com.flyingforest.flyingforest.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.flyingforest.flyingforest.R;
import com.flyingforest.flyingforest.map.MapActivity;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fata on 8/17/2017.
 */

public class EventViewHolder extends RecyclerView.ViewHolder {

    private final TextView time;
    private final TextView title;
    private final TextView location;
    private final View view;
    private final ImageView image;
    private Context context;
    private EventModel model;

    public EventViewHolder(View itemView) {
        super(itemView);
        time = (TextView)itemView.findViewById(R.id.item_event_time);
        title = (TextView)itemView.findViewById(R.id.item_event_title);
        location = (TextView)itemView.findViewById(R.id.item_event_location);
        image = (ImageView)itemView.findViewById(R.id.item_event_image);
        view = itemView;
    }

    public void setView(final EventModel m, final Context c) {
        this.context = c;
        this.model = m;
        title.setText(model.getTitle());
        location.setText(model.getLocation());
        time.setText(getStringDate(model.getTime()));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(context, MapActivity.class);
                mapIntent.putExtra("idEvent", model.getId());
                mapIntent.putExtra("lat", model.getLat());
                mapIntent.putExtra("lng", model.getLng());
                context.startActivity(mapIntent);
            }
        });
        setImage();
    }

    private void setImage() {
        StorageReference imageRef = FirebaseStorage.getInstance().getReference("image").child(model.getId()+".jpg");
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(imageRef)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(image);
    }

    private String getStringDate(long time) {
        Date date = new Date();
        date.setTime(time);
        SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy - hh:mm a", Locale.getDefault());
        return format.format(date);
    }
}
