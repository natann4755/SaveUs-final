package com.example.saveus.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saveus.Activitys.HomeActivity;
import com.example.saveus.Adapters.InfoWindowAdapter;
import com.example.saveus.Objects.LocationUser;
import com.example.saveus.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;


public class HomeMapFragment extends Fragment implements OnMapReadyCallback ,ActivityCompat.OnRequestPermissionsResultCallback {



    private OnFragmentHomeMapListener mListener;
    private ArrayList<LocationUser> mlocations = new ArrayList<>();
    private SupportMapFragment mapFragment;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private double mLatitude = 0.0;
    private double mLongitude = 0.0;
    private LocationManager locationManager;
    private GoogleMap googleMap;
    private Location gps_loc;
    private Location network_loc;
    private Location final_loc;
    private boolean isPressed = false;
    private RelativeLayout backgroundButton;
    private RelativeLayout buttonStartTimer;
    private TextView text1;
    private TextView text2;
    private String mTime;
    private LinearLayout topLogo;
    private ImageView logoProfile;
    private LocationUser mLocationUser;
    private CountDownTimer myTimer;
    static final String KEY_mlocations_AREEY = "KEY_mlocations_AREEY";



    public HomeMapFragment() {
        // Required empty public constructor
    }

    public static HomeMapFragment newInstance(ArrayList<LocationUser> mlocations) {
        HomeMapFragment fragment = new HomeMapFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_mlocations_AREEY,mlocations);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mlocations = getArguments().getParcelableArrayList(KEY_mlocations_AREEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_home_map, container, false);
//        initListener();
        initVeiws(rootView);
        initMap(rootView);
        return rootView;
    }

    private void initMap(View rootView) {
        getPremissionLocation();
    }


    private void initVeiws(View rootView) {
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        buttonStartTimer = rootView.findViewById(R.id.butoon_startTimet_RL);
        text1 = rootView.findViewById(R.id.butoon_startTimet_text1_RL);
        text2 = rootView.findViewById(R.id.butoon_startTimet_text2_RL);
        backgroundButton = rootView.findViewById(R.id.lite_background_startTimet_RL);

        buttonStartTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressed == false) {
                   if (mLatitude == 0.0 && mLongitude == 0.0 ){
                       Toast.makeText(getContext() , R.string.tost_no_locashen , Toast.LENGTH_SHORT)
                               .show();
                   }else
                    changeButtonAndTimer();
                }
                else {
                    mLocationUser.setTime(mTime);
                    myTimer.onFinish();
                    isPressed = false;
                    mLocationUser.setEndTime(HomeActivity.fixNam(getTime()[0]) + ":" + HomeActivity.fixNam(getTime()[1]));
                    mLocationUser.setEndSecond(getTime()[2]);
//                    mlocations.add(0,mLocationUser);
                    mListener.addNewLocationUserFromButton(mLocationUser);
                    addMarker(mLocationUser.getLatitude(),mLocationUser.getLongitude());
                    Toast.makeText(getContext(),"On the date " + mLocationUser.getDay()+"/"+mLocationUser.getManth()+"/"+mLocationUser.getYaer() +"\n"
                                   +"For the duration " + mLocationUser.getAdsress()+", " +"\n"
                                    +"Between the hours " + mLocationUser.getStartHour()+":"+mLocationUser.getStartMinit()+", " + mLocationUser.getEndTime()+"\n"
                                    +"For the duration " + mLocationUser.getTime()
                            , Toast.LENGTH_SHORT)
                            .show();

                    backgroundButton.setBackgroundResource(R.drawable.circel_litegreen);
                    buttonStartTimer.setBackgroundResource(R.drawable.circel_green);
                    text1.setText(R.string.main_botton_text1);
                    text2.setText(R.string.main_botton_text2);
                }
            }
        });

    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentHomeMap();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentHomeMapListener) {
            mListener = (OnFragmentHomeMapListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMinZoomPreference(16f);
//        LatLng lastLatLng;
//        if (mlocations.size()>0){
//            lastLatLng = new LatLng(mlocations.get(0).getLatitude(),mlocations.get(0).getLongitude());
//        }else{
//            lastLatLng = new LatLng(31.771959, 35.217018);
//            }

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLatitude,mLongitude)));
//        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (mlocations.size()>0){
            for (LocationUser locationUse: mlocations) {
                addMarker(locationUse.getLatitude(),locationUse.getLongitude());
            }
        }
        InfoWindowAdapter markerWindowView = new  InfoWindowAdapter(getContext());
        googleMap.setInfoWindowAdapter(markerWindowView);



//        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                MarkerOptions m  =    new MarkerOptions()
//                .icon (bitmapDescriptorFromVector(getContext(),R.drawable.red_marker))
//                .position(new LatLng(latLng.latitude, latLng.longitude));
//                googleMap.addMarker(m);
//            }
//        });
    }



    private void getPremissionLocation() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
        &&ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_NETWORK_STATE},MY_PERMISSIONS_REQUEST_LOCATION);
            } else{
            getLocaishen();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case  MY_PERMISSIONS_REQUEST_LOCATION : {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocaishen();

                }else {
                    Log.v("locaishen x","locaishen x");
                    Toast.makeText(getContext(),R.string.main_tost_text , Toast.LENGTH_SHORT)
                            .show();
                }
                return;
            }
        }
    }

    private void getLocaishen() {
//        googleMap.setMyLocationEnabled(true);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED||ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (gps_loc != null) {
                final_loc = gps_loc;
                mLatitude = final_loc.getLatitude();
                mLongitude = final_loc.getLongitude();
            } else if (network_loc != null) {
                final_loc = network_loc;
                mLatitude = final_loc.getLatitude();
                mLongitude = final_loc.getLongitude();
            }
            if (googleMap != null) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLatitude, mLongitude)));
            }
        }
    }

    private void changeButtonAndTimer() {
        isPressed = true;
        backgroundButton.setBackgroundResource(R.drawable.circel_litered);
        buttonStartTimer.setBackgroundResource(R.drawable.circel_stopred);
        text1.setText(R.string.main_botton_text3);
        mLocationUser = new LocationUser(mLatitude,mLongitude);

        getAddres(mLatitude,mLongitude);
        timer(mLocationUser);
        setDate();
    }



    private void timer(LocationUser mLocationUser) {
        mLocationUser.setStartHour(getTime()[0]);
        mLocationUser.setStartMinit(getTime()[1]);
        mLocationUser.setStartSecond(getTime()[2]);
        final long EndTime   = 360000000*100;          //End at min:sec converted to seconds
        myTimer = new CountDownTimer(EndTime*1000, 1000) {
        public void onTick(long millisUntilFinished) {
            long secondUntilFinished = (long) (millisUntilFinished/1000);
            long secondsPassed = (EndTime - secondUntilFinished);
            long minutesPassed = (long) (secondsPassed/60);
            long hoursPassed = (long) (minutesPassed/60);
            secondsPassed = secondsPassed%60;
            minutesPassed = minutesPassed%60;
            mTime = String.format("%02d", hoursPassed) + ":" + String.format("%02d", minutesPassed) + ":" + String.format("%02d", secondsPassed);
            text2.setText(mTime);
            }
            public void onFinish() {
                cancel();
                }
         }.start();
    }

    private void getAddres(double latitude, double longitude)  {
            try {
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String country;
                String addres = "";
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    country = (address.getCountryName());
                    addres = addresses.get(0).getAddressLine(0);
                }else {
                    country = "Unknown";
                }
                mLocationUser.setAdsress(addres);
                Log.v("locaishen x","locaishen x");

                } catch (IOException e) {

            }
        }

    private int [] getTime(){
        Calendar cc = Calendar.getInstance();
        int [] arreyTime =  {cc.get(Calendar.HOUR_OF_DAY),cc.get(Calendar.MINUTE),cc.get(Calendar.SECOND)};
        return arreyTime;
    }

    private void setDate() {
        Calendar cc = Calendar.getInstance();
        mLocationUser.setYaer(cc.get(Calendar.YEAR));
        mLocationUser.setManth(cc.get(Calendar.MONTH)+1);
        mLocationUser.setDay(cc.get(Calendar.DAY_OF_MONTH));

    }

    private void addMarker(double Latitude,double Longitude) {

        try {
           Marker m = googleMap.addMarker(new MarkerOptions()
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.red_marker))
                    .position(new LatLng(Latitude, Longitude)));
//           m.showInfoWindow();
//            MarkerOptions m = new MarkerOptions()
//                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.red_marker))
//                    .position(new LatLng(Latitude, Longitude));
//            googleMap.addMarker(m);

        }catch (NullPointerException u){
          Log.e("nnnnnnnnnnnnnnnnnnnnn","שגיאה במרקר!!!!!!!!!!!!!!");
            Log.e("nnnnnnnnnnnnnnnnnnnnn",u+"");
        }

    }

        private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context,vectorDrawableResourceId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }



//    public void updateNewPlace(LocationUser locationUse) {
//        if (locationUse != null) {
//            addMarker(locationUse.getLatitude(), locationUse.getLongitude());
//        }
//    }

    public interface OnFragmentHomeMapListener {
        void onFragmentHomeMap();
        void addNewLocationUserFromButton(LocationUser locationUser);
    }


}
