package com.example.saveus.Activitys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saveus.Objects.UserProfile;
import com.example.saveus.R;
import com.google.gson.Gson;

import java.util.Locale;
import java.util.UUID;

import static com.example.saveus.Fragments.FragmentProfile.KEY_SharedPreferences_PROFILE;
import static com.example.saveus.Fragments.FragmentProfile.KEY__PROFILE;

public class LoginActivity extends AppCompatActivity {

    private Button skipButton;
    private Button singUpButton;
    private TextView terms;
    private TextView termsOfUse;
    private ImageView openProfile;
    private EditText name;
    private EditText email;
    private EditText fonNumber;
    private UserProfile mUserProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setCorrectLanguage();
        initVeiws();    //זמני לתקן!!!!!!
    }

    private void setCorrectLanguage() {
        UserProfile userProfile = null;
        SharedPreferences prefs = getSharedPreferences(
                KEY_SharedPreferences_PROFILE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(KEY__PROFILE, null);
        if (json != null) {
            userProfile = gson.fromJson(json, UserProfile.class);
        }
        if (userProfile != null){
            String languageToLoad;
            if (userProfile.getLanguage().equals("עברית")){
                languageToLoad = "he";
            }else {
                languageToLoad = "en";
            }
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }
    }



    private void initVeiws() {
        name = findViewById(R.id.login_name_ET);
        email = findViewById(R.id.login_email_ET);
        fonNumber = findViewById(R.id.login_fon_ET);
        skipButton = findViewById(R.id.login_skip_BUT);
        singUpButton = findViewById(R.id.login_sing_up_BUT);
        terms = findViewById(R.id.login_text2_tv);
        termsOfUse = findViewById(R.id.login_text4_tv);
        openProfile = findViewById(R.id.profile1_button_IV);


        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mName = name.getText().toString();
                String mEmail =  email.getText().toString();
                String mFonNumber = fonNumber.getText().toString();
                if (mName.equals("")  || mEmail.equals("") || mFonNumber.equals("")){
                    Toast.makeText(getApplicationContext(),getString(R.string.tost_no_full) , Toast.LENGTH_SHORT).show();
                }else {
                    mUserProfile = new UserProfile(true);
                    mUserProfile.setUserName(mName);
                    mUserProfile.setEmail(mEmail);
                    mUserProfile.setFonNumber(mFonNumber);
                    updateServerProfile(mUserProfile);
                    saveID();
                    finish();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                }
            }
        });

        openProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
            }
        });

        View.OnClickListener mDeialog = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deialog(v);
            }
        };

        terms.setOnClickListener(mDeialog);
        termsOfUse.setOnClickListener(mDeialog);


        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveID();
                finish();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

            }
        });
    }

    private void saveID() {
        String uniqueID = UUID.randomUUID().toString();
        //לשמור בשרת
    }

    private void deialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("This is my message.");

        switch (v.getId()) {
            case R.id.login_text2_tv:
                builder.setMessage(R.string.deialog_terms);
                break; // optional

            case R.id.login_text4_tv:
                builder.setMessage(R.string.deialog_termsofuse);
                break; // optional
        }

        // add a button
        builder.setPositiveButton("OK", null);//לשנות!!!!

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void updateServerProfile(UserProfile userProfile) {
        SharedPreferences prefs = this.getSharedPreferences(
                KEY_SharedPreferences_PROFILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUserProfile);
        prefsEditor.putString(KEY__PROFILE,json);
        prefsEditor.commit();
    }

}



