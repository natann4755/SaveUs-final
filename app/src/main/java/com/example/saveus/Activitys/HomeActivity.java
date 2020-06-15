package com.example.saveus.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.saveus.Fragments.FragmentAddPlaice;
import com.example.saveus.Fragments.FragmentEditPlace;
import com.example.saveus.Fragments.FragmentMyPlacesRV;
import com.example.saveus.Fragments.FragmentOnMap;
import com.example.saveus.Fragments.FragmentProfile;
import com.example.saveus.Fragments.FragmentProfileEdit;
import com.example.saveus.Fragments.HomeMapFragment;
import com.example.saveus.Fragments.MyPlacesFragment;
import com.example.saveus.Fragments.NotificationsFragment;
import com.example.saveus.Objects.LocationUser;
import com.example.saveus.Objects.UserProfile;
import com.example.saveus.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements HomeMapFragment.OnFragmentHomeMapListener, MyPlacesFragment.OnFragmentMyPlacesListener , NotificationsFragment.OnFragmentNotificationsListener,
 FragmentOnMap.OnFragmentOnMapListener , FragmentMyPlacesRV.OnFragmentMyPlacesRVListener ,
        FragmentAddPlaice.OnFragmentAddPlaiceListener, FragmentEditPlace.OnFragmentEditPlaceListener, FragmentProfile.OnFragmentProfileListener
        , FragmentProfileEdit.OnFragmentonEditProfile
        {


    private ArrayList <LocationUser> mLocations = new ArrayList<>();
    private HomeMapFragment mHomeMapFragment;
    private MyPlacesFragment myPlacesFragment;
    private NotificationsFragment mNotificationsFragment = NotificationsFragment.newInstance();
    private FragmentProfile mFragmentProfile = FragmentProfile.newInstance();
    private FragmentAddPlaice mFragmentAddPlaice;
    private BottomNavigationView bottomNavigation;
    public static String KEY_SharedPreferences_LOCATIONS = "ALL LOCATIONS";
    public static String KEY_ALL_LOCATIONS = "ALL LOCATIONS";
    private ImageView openProfile;
    private int getCorectItemVP_ForBac = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initAllLocationUser();
        initVeiws();
        initFramLayot(HomeMapFragment.newInstance(mLocations),"HomeMapFragment");
    }

    private void initAllLocationUser() {
        SharedPreferences prefs = this.getSharedPreferences(
                KEY_SharedPreferences_LOCATIONS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(KEY_ALL_LOCATIONS, null);
        Type type = new TypeToken<List<LocationUser>>() {}.getType();
        if (json != null) {
            mLocations = gson.fromJson(json, type);
        }
    }


    private void initVeiws() {
        mHomeMapFragment = HomeMapFragment.newInstance(mLocations);
        myPlacesFragment = MyPlacesFragment.newInstance(mLocations);

        BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_main:
                                mHomeMapFragment = HomeMapFragment.newInstance(mLocations);
                                initFramLayot(mHomeMapFragment,"HomeMapFragment");
                                return true;
                            case R.id.navigation_myPlace:
                                myPlacesFragment = MyPlacesFragment.newInstance(mLocations);
                                initFramLayot(myPlacesFragment,"MyPlacesFragment");
                                return true;
                            case R.id.navigation_Notifications:
                                initFramLayot(mNotificationsFragment,"NotificationsFragment");
                                return true;
                        }
                        return false;
                    }
                };

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        bottomNavigation.setItemIconTintList(null);

        openProfile = findViewById(R.id.profile2_button_IV);

        openProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFramLayot(mFragmentProfile,"FragmentProfile");
            }
        });
    }

    private void initFramLayot(Fragment mFragment, String fragmentName) {
        getSupportFragmentManager().beginTransaction().addToBackStack(fragmentName).replace(R.id.home_franlayout_FL,mFragment).commit();
    }

    public static String fixNam(int namber) {
        if (namber>9){
            return  String.valueOf(namber);
        }else {
            return "0"+String.valueOf(namber);
        }
    }

    private void updateServer() {
        SharedPreferences prefs = this.getSharedPreferences(
                KEY_SharedPreferences_LOCATIONS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mLocations);
        editor.putString(KEY_ALL_LOCATIONS, json);
        editor.commit();
    }

    @Override
    public void onFragmentHomeMap() {

    }

    @Override
    public void addNewLocationUserFromButton(LocationUser locationUser) {
        mLocations.add(0,locationUser);
        updateNewLocationUser(locationUser);
    }

    private void updateNewLocationUser(LocationUser locationUser) {
//        Collections.sort(mLocations);
//        mHomeMapFragment.updateNewPlace(locationUser);
//        myPlacesFragment.updateNewPlace(locationUser);
        updateServer();
    }

    @Override
    public void onMyPlacesInteraction() {
    }

    @Override
    public void addPlaceClick() {
        mFragmentAddPlaice = FragmentAddPlaice.newInstance();
        initFramLayot(mFragmentAddPlaice,"FragmentAddPlaice");
    }

            @Override
            public void getCorectItemVP_ForBac(int CorectItem) {
                getCorectItemVP_ForBac = CorectItem;
            }

            @Override
    public void onNotificationsInteraction() {

    }

    @Override
    public void onFragmentMyPlacesRV() {

    }

    @Override
    public void ChangeLocationPressed(LocationUser locationUser) {
        FragmentEditPlace mFragmentEditPlace = FragmentEditPlace.newInstance(mLocations, locationUser);
        initFramLayot(mFragmentEditPlace,"FragmentEditPlace");
    }

    @Override
    public void onFragmentOnMap() {

    }

    @Override
    public void onFragmentAddPlaice() {

    }

    @Override
    public void sendLocationUser(LocationUser locationUse) {
        mLocations.add(findPliceInAreey(locationUse),locationUse);
        updateNewLocationUser(locationUse);
    }

    @Override
    public void moveBakToMapOrRV() {
        int corectItem = getCorectItemVP_ForBac;
        myPlacesFragment = MyPlacesFragment.newInstance(mLocations);
        initFramLayot(myPlacesFragment,"MyPlacesFragment");
        if (corectItem == 1){
            myPlacesFragment.moveToRV();
        }
    }


    @Override
    public void onFragmentEditPlace() {

    }

    @Override
    public void updatedAreey() {
//        mLocations = mLocationsNewAreey;
        Collections.sort(mLocations);
        updateServer();
    }

    @Override
    public void moveBakToRv() {
        myPlacesFragment = MyPlacesFragment.newInstance(mLocations);
        initFramLayot(myPlacesFragment,"MyPlacesFragment");
        myPlacesFragment.moveToRV();

    }


    public int findPliceInAreey (LocationUser element){
        int posishen;
        for (posishen = 0; posishen <mLocations.size() ; posishen++) {
            if (mLocations.size()==posishen){
                return posishen;
            }
            if (        element.getYaer() > mLocations.get(posishen).getYaer()
                    || (element.getYaer() == mLocations.get(posishen).getYaer() && element.getManth() > mLocations.get(posishen).getManth())
                    || (element.getYaer() == mLocations.get(posishen).getYaer() && element.getManth() == mLocations.get(posishen).getManth() && element.getDay() > mLocations.get(posishen).getDay())
                    || (element.getYaer() == mLocations.get(posishen).getYaer() && element.getManth() == mLocations.get(posishen).getManth() && element.getDay() == mLocations.get(posishen).getDay()
                    &&  element.getStartHour() > mLocations.get(posishen).getStartHour())
                    || (element.getYaer() == mLocations.get(posishen).getYaer() && element.getManth() == mLocations.get(posishen).getManth() && element.getDay() == mLocations.get(posishen).getDay()
                    && element.getStartHour() == mLocations.get(posishen).getStartHour() && element.getStartMinit() > mLocations.get(posishen).getStartMinit()
                    || (element.getYaer() == mLocations.get(posishen).getYaer() && element.getManth() == mLocations.get(posishen).getManth() && element.getDay() == mLocations.get(posishen).getDay()
                    && element.getStartHour() == mLocations.get(posishen).getStartHour() && element.getStartMinit() == mLocations.get(posishen).getStartMinit() && element.getStartSecond() > mLocations.get(posishen).getStartSecond()))){
                return posishen;
            }
        }
        return posishen;
    }

            @Override
            public void onEditProfile(UserProfile mUserProfile) {
                FragmentProfileEdit mFragmentProfileEdit = FragmentProfileEdit.newInstance(mUserProfile);
                initFramLayot(mFragmentProfileEdit,"FragmentProfileEdit");
            }

            @Override
            public void onCloseProfilPresd() {
                onBackPressed();
            }

            @Override
            public void onEditProfile() {

            }

            @Override
            public void setCorrectLanguage(String Language) {
                String languageToLoad;
                if (Language.equals("עברית")){
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



            @Override
            public void onBackPressed() {
                FragmentManager fm = getSupportFragmentManager();
               String currectFragmentTag =  fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName();

//               if (currectFragmentTag.equals("NotificationsFragment")){
//                   Log.e("uu","ggggggggggggggggggggggggggggggg");
//               }
                switch(currectFragmentTag) {
                    case "HomeMapFragment":
                        finish();
                        break;
                    case "MyPlacesFragment":
                        fm.popBackStack ("HomeMapFragment", 0);
                        Log.e("uu","ggggggggggggggggggggggggggggggg");
                        bottomNavigation.setSelectedItemId(R.id.navigation_main);
                        break;
                    case "NotificationsFragment":
                        fm.popBackStack ("HomeMapFragment", 0);
                        Log.e("uu","ggggggggggggggggggggggggggggggg");
                        bottomNavigation.setSelectedItemId(R.id.navigation_main);
                        break;
                    case "FragmentMyPlacesRV":
                        fm.popBackStack ("HomeMapFragment", 0);
                        bottomNavigation.setSelectedItemId(R.id.navigation_main);
                        break;
                    case "FragmentOnMap":
                        fm.popBackStack ("HomeMapFragment", 0);
                        bottomNavigation.setSelectedItemId(R.id.navigation_main);
                        break;
                    case "FragmentProfile":
                        if   (fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 2).getName().equals("MyPlacesFragment")){
                            bottomNavigation.setSelectedItemId(R.id.navigation_myPlace);
                            moveBakToMapOrRV();
                        }
                        else {
                            fm.popBackStack();
                            if (fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 2).getName().equals("HomeMapFragment")) {
                                bottomNavigation.setSelectedItemId(R.id.navigation_main);
                            }
                            if (fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 2).getName().equals("NotificationsFragment")) {
                                bottomNavigation.setSelectedItemId(R.id.navigation_Notifications);
                            }

                        }
                        break;
                    case "FragmentProfileEdit":
                        fm.popBackStack ();
                        Log.e("uu","ggggggggggggggggggggggggggggggg");
                        break;

                    case "FragmentAddPlaice":
                        moveBakToMapOrRV();
                        Log.e("uu","ggggggggggggggggggggggggggggggg");
                        break;

                    case "FragmentEditPlace":
                        moveBakToRv();
                        Log.e("uu","ggggggggggggggggggggggggggggggg");
                        break;


                }
             }


        }


