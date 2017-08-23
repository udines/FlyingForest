package com.flyingforest.flyingforest.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.firebase.ui.database.FirebaseIndexRecyclerAdapter;
import com.flyingforest.flyingforest.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseIndexRecyclerAdapter<EventModel, EventViewHolder> adapter;
    private DatabaseReference eventRef;
    private DatabaseReference query;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.main_recyclerview);
        eventRef = FirebaseDatabase.getInstance().getReference("event");
        query = eventRef;
        adapter = new FirebaseIndexRecyclerAdapter<EventModel, EventViewHolder>(
                EventModel.class,
                R.layout.item_event,
                EventViewHolder.class,
                query,
                eventRef
        ) {
            @Override
            protected void populateViewHolder(EventViewHolder viewHolder, EventModel model, int position) {
                viewHolder.setView(model);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = eventRef.push().getKey();
                String title = "Kebakaran Hutan";
                String location = "Kalimantan";
                long time = new Date().getTime();
                double lat = -0.100687;
                double lng = 113.052102;
                EventModel model = new EventModel(key, title, location, time, lat, lng);
                eventRef.child(key).setValue(model);
            }
        });
    }

}
