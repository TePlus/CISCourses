package com.example.teplus.ciscourses;

import android.app.Application;

import com.inthecheesefactory.thecheeselibrary.manager.Contextor;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
            // Intialize thing(s) here
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


}
