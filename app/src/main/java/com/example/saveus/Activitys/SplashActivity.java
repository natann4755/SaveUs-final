package com.example.saveus.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.saveus.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {

                Intent Manage = new Intent(getBaseContext(), MainActivity.class);
                finish();
                startActivity(Manage);
            }
        }, 3000);
    }


}

