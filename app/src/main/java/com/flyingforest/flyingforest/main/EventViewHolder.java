package com.flyingforest.flyingforest.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.flyingforest.flyingforest.R;
import com.flyingforest.flyingforest.map.MapActivity;
import com.google.android.gms.maps.SupportMapFragment;

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
    private final RecyclerView recyclerView;
    private final View view;

    public EventViewHolder(View itemView) {
        super(itemView);
        time = (TextView)itemView.findViewById(R.id.item_event_time);
        title = (TextView)itemView.findViewById(R.id.item_event_title);
        location = (TextView)itemView.findViewById(R.id.item_event_location);
        recyclerView = (RecyclerView)itemView.findViewById(R.id.item_event_recyclerview);
        view = itemView;
    }

    public void setView(final EventModel model, final Context context) {
        title.setText(model.getTitle());
        location.setText(model.getLocation());
        time.setText(getStringDate(model.getTime()));
        displayImage();
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
    }

    private void displayImage() {
    }

    private String getStringDate(long time) {
        Date date = new Date();
        date.setTime(time);
        SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy - hh:mm a", Locale.getDefault());
        return format.format(date);
    }
}
