package com.example.saveus.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.saveus.R;

public class MainActivity extends AppCompatActivity {

    public static String KEYSharedPreferenceIsConnected;
    public static String KEYisConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        manageStartActivity();
    }


    private void manageStartActivity() {
        SharedPreferences prefs = getSharedPreferences(KEYSharedPreferenceIsConnected, 0);
        boolean isConnected = prefs.getBoolean(KEYisConnected, false);

        if (isConnected){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }else {
            prefs.edit().putBoolean(KEYisConnected , true).apply();
            startActivity(new Intent(MainActivity.this,ActivityOnBoarding.class));
        }
    }
}
