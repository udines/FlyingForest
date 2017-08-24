package com.flyingforest.flyingforest.map;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.flyingforest.flyingforest.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private String idEvent;
    private double lat;
    private double lng;
    private LatLng startingPoint;
    private GoogleMap mMap;
    private double selectedLat;
    private double selectedLng;
    private DatabaseReference pointRef;
    private ValueEventListener pointListener;
    private ArrayList<LatLng> pointList;
    private HeatmapTileProvider mProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        idEvent = getIntent().getStringExtra("idEvent");
        lat = getIntent().getDoubleExtra("lat", 0);
        lng = getIntent().getDoubleExtra("lng", 0);
        pointRef = FirebaseDatabase.getInstance().getReference("point").child(idEvent);
        startingPoint = new LatLng(lat, lng);
        pointListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pointList = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    PointModel model = dataSnapshot1.getValue(PointModel.class);
                    pointList.add(new LatLng(model.getLat(), model.getLng()));
                }
                mProvider = new HeatmapTileProvider.Builder().data(pointList).radius(35)
                        .gradient(createGradient()).build();
                mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*selectedLat = lat;
                selectedLng = lng;
                String key = pointRef.push().getKey();
                PointModel model = new PointModel(key, idEvent, selectedLat, selectedLng);
                pointRef.child(key).setValue(model);*/
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingPoint, 15));
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                selectedLat = latLng.latitude;
                selectedLng = latLng.longitude;
                String key = pointRef.push().getKey();
                PointModel model = new PointModel(key, idEvent, selectedLat, selectedLng);
                pointRef.child(key).setValue(model);
            }
        });
    }

    private Gradient createGradient() {
        int[] colors = {
                Color.rgb(102, 225, 0), // green
//                Color.rgb(255, 0, 0),    // red
                Color.rgb(255, 0, 0)    // red
        };

        float[] startPoints = {
                0.2f, 1f
        };

        return new Gradient(colors, startPoints);
    }

    @Override
    protected void onStart() {
        super.onStart();
        idEvent = getIntent().getStringExtra("idEvent");
        pointRef.addValueEventListener(pointListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        pointRef.removeEventListener(pointListener);
    }
}
