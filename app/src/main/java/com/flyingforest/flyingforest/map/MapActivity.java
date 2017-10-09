package com.flyingforest.flyingforest.map;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.flyingforest.flyingforest.R;
import com.flyingforest.flyingforest.data.DataActivity;
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
    private ArrayList<PointModel> pointDataList;
    private HeatmapTileProvider mProvider;

    private LinearLayout legendaApi;
    private LinearLayout legendaSuhu;
    private RadioGroup radioGroupPemetaan;
    private RadioGroup radioGroupJenisPeta;
    private Button buttonLihatData;
    private Button buttonLihatFoto;
    private RadioButton radioButtonTitikApi;
    private RadioButton radioButtonSuhuUdara;
    private RadioButton radioButtonDefault;
    private RadioButton radioButtonSatelit;
    private RadioButton radioButtonMedan;

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

        setViewById();
        setRadioGroupListener();

        pointRef = FirebaseDatabase.getInstance().getReference("point").child(idEvent);
        startingPoint = new LatLng(lat, lng);
        pointListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pointList = new ArrayList<>();
                ArrayList<LatLng> lowTempPointList = new ArrayList<>();
                ArrayList<LatLng> midTempPointList = new ArrayList<>();
                ArrayList<LatLng> highTempPointList = new ArrayList<>();
                if (radioGroupPemetaan.getCheckedRadioButtonId() == radioButtonTitikApi.getId()) {
                    mMap.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        PointModel model = dataSnapshot1.getValue(PointModel.class);
                        pointList.add(new LatLng(model.getLat(), model.getLng()));
                    }
                    mProvider = new HeatmapTileProvider.Builder().data(pointList).radius(30)
                            .gradient(createGradient()).opacity(1).build();
                    mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
                } else {
                    mMap.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        PointModel model = dataSnapshot1.getValue(PointModel.class);
                        if (model.getTemp() <= 30) {
                            lowTempPointList.add(new LatLng(model.getLat(), model.getLng()));
                        } else if (model.getTemp() <= 50) {
                            midTempPointList.add(new LatLng(model.getLat(), model.getLng()));
                        } else if (model.getTemp() > 50) {
                            highTempPointList.add(new LatLng(model.getLat(), model.getLng()));
                        }
                    }
                    if (lowTempPointList.size() > 0) {
                        HeatmapTileProvider lowTempProvider = new HeatmapTileProvider.Builder().data(lowTempPointList).radius(30)
                                .gradient(createSuhuGradient(1)).opacity(1).build();
                        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(lowTempProvider));
                    }
                    if (midTempPointList.size() > 0) {
                        HeatmapTileProvider midTempProvider = new HeatmapTileProvider.Builder().data(midTempPointList).radius(30)
                                .gradient(createSuhuGradient(2)).opacity(1).build();
                        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(midTempProvider));
                    }
                    if (highTempPointList.size() > 0) {
                        HeatmapTileProvider highTempProvider = new HeatmapTileProvider.Builder().data(highTempPointList).radius(30)
                                .gradient(createSuhuGradient(3)).opacity(1).build();
                        mMap.addTileOverlay(new TileOverlayOptions().tileProvider(highTempProvider));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setCheckedRadioGroup();
        buttonClickListener();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void buttonClickListener() {
        buttonLihatData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dataIntent = new Intent(getApplicationContext(), DataActivity.class);
                dataIntent.putExtra("idEvent", idEvent);
                startActivity(dataIntent);
            }
        });
        buttonLihatFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MapActivity.this, "Fitur belum jadi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Gradient createSuhuGradient(int lowMidHigh) {
        int[] lowTempColor = {
                Color.rgb(21,101,192)
        };
        int[] midTempColor = {
                Color.rgb(255,179,0)
        };
        int[] highTempColor = {
                Color.rgb(244,67,54)
        };

        float[] startPoints = {
                1f
        };
        if (lowMidHigh == 1) {
            return new Gradient(lowTempColor, startPoints);
        } else if (lowMidHigh == 2) {
            return new Gradient(midTempColor, startPoints);
        } else {
            return new Gradient(highTempColor, startPoints);
        }
    }

    private void setCheckedRadioGroup() {
        radioGroupPemetaan.check(radioButtonTitikApi.getId());
        radioGroupJenisPeta.check(radioButtonDefault.getId());
    }

    private void setRadioGroupListener() {
        radioGroupJenisPeta.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.map_radio_button_default :
                        if (mMap != null) {
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        }
                        break;
                    case R.id.map_radio_button_satelit :
                        if (mMap != null) {
                            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        }
                        break;
                    case R.id.map_radio_button_medan :
                        if (mMap != null) {
                            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        }
                        break;
                }
            }
        });
        radioGroupPemetaan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.map_radio_button_titik_api :
                        legendaApi.setVisibility(View.VISIBLE);
                        legendaSuhu.setVisibility(View.GONE);
                        onStart();
                        break;
                    case R.id.map_radio_button_suhu_udara :
                        legendaSuhu.setVisibility(View.VISIBLE);
                        legendaApi.setVisibility(View.GONE);
                        onStart();
                        break;
                }
            }
        });
    }

    private void setViewById() {
        legendaApi = findViewById(R.id.map_legenda_titik_api);
        legendaSuhu = findViewById(R.id.map_legenda_suhu_udara);
        radioGroupPemetaan = findViewById(R.id.map_radio_group_pemetaan);
        radioGroupJenisPeta = findViewById(R.id.map_radio_group_jenis_peta);
        buttonLihatData = findViewById(R.id.map_button_lihat_data);
        buttonLihatFoto = findViewById(R.id.map_button_lihat_foto);
        radioButtonTitikApi = findViewById(R.id.map_radio_button_titik_api);
        radioButtonSuhuUdara = findViewById(R.id.map_radio_button_suhu_udara);
        radioButtonDefault = findViewById(R.id.map_radio_button_default);
        radioButtonSatelit = findViewById(R.id.map_radio_button_satelit);
        radioButtonMedan = findViewById(R.id.map_radio_button_medan);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startingPoint, 15));
        /*mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                selectedLat = latLng.latitude;
                selectedLng = latLng.longitude;
                String key = pointRef.push().getKey();
                PointModel model = new PointModel(key, idEvent, selectedLat, selectedLng);
                pointRef.child(key).setValue(model);
            }
        });*/
    }

    private Gradient createGradient() {
        int[] warnaApi = {
//                Color.rgb(102, 225, 0), // green
//                Color.rgb(255, 0, 0),    // red
//                Color.rgb(183,28,28),
                Color.rgb(244,67,54)
        };

        float[] startPoints = {
                /*0.2f,*/ 1f
        };

        return new Gradient(warnaApi, startPoints);
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
