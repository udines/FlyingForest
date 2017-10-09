package com.flyingforest.flyingforest.data;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.flyingforest.flyingforest.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference dataRef;
    private String idEvent;
    private FirebaseRecyclerAdapter<DataModel, DataViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        idEvent = getIntent().getStringExtra("idEvent");
        recyclerView = findViewById(R.id.data_recyclerview);
        dataRef = FirebaseDatabase.getInstance().getReference().child("point").child(idEvent);
        adapter = new FirebaseRecyclerAdapter<DataModel, DataViewHolder>(
                DataModel.class,
                R.layout.item_data,
                DataViewHolder.class,
                dataRef
        ) {
            @Override
            protected void populateViewHolder(DataViewHolder viewHolder, DataModel model, int position) {
                viewHolder.setView(model);
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
