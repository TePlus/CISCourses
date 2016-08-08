package com.example.teplus.ciscourses.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.teplus.ciscourses.R;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    Runnable runable;
    long delay_time;
    long time = 3000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();

        runable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, CoursesActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runable , delay_time);
        time = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runable);
        time = delay_time - (System.currentTimeMillis() - time);
    }
}
