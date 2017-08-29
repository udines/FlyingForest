package com.flyingforest.flyingforest;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by laptop on 8/29/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
