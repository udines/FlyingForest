package com.flyingforest.flyingforest.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.flyingforest.flyingforest.R;

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

    public EventViewHolder(View itemView) {
        super(itemView);
        time = (TextView)itemView.findViewById(R.id.item_event_time);
        title = (TextView)itemView.findViewById(R.id.item_event_title);
        location = (TextView)itemView.findViewById(R.id.item_event_location);
    }

    public void setView(EventModel model) {
        title.setText(model.getTitle());
        location.setText(model.getLocation());
        time.setText(getStringDate(model.getTime()));
    }

    private String getStringDate(long time) {
        Date date = new Date();
        date.setTime(time);
        SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy - hh:mm a", Locale.getDefault());
        return format.format(date);
    }
}
