package com.example.blackfiguresquizjd;

import android.app.Application;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setProjectId("blackhistoricalfigures")
                    .setApplicationId("1:693273107412:android:f22f14a1e49805f8019441")
                    .setApiKey("AIzaSyBiwZfEdHE9ph04oqIPtKRrPZPoTLCO7Oc")
                    .setDatabaseUrl("https://blackhistoricalfigures-default-rtdb.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(this, options);
        }
    }
}