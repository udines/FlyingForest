package com.flyingforest.flyingforest.data;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.flyingforest.flyingforest.R;

/**
 * Created by fata on 10/9/2017.
 */

public class DataViewHolder extends RecyclerView.ViewHolder {

    private final TextView dataText;

    public DataViewHolder(View itemView) {
        super(itemView);
        dataText = itemView.findViewById(R.id.item_data_text);
    }

    public void setView(DataModel model) {
        String lat = Double.toString(model.getLat());
        String lng = Double.toString(model.getLng());
        String latitude = lat.substring(0, 11);
        String longitude = lng.substring(0, 11);
        String suhu = Integer.toString(model.getTemp());
        String api = "ada";
        String text = latitude + " | " + longitude + " | " + suhu + " C | " + api;
        dataText.setText(text);
    }
}
