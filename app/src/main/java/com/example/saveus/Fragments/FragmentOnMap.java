package com.example.saveus.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;
import static com.example.saveus.Fragments.HomeMapFragment.KEY_mlocations_AREEY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link FragmentOnMap#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOnMap extends Fragment implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener {


    private OnFragmentOnMapListener mListener;
    private ArrayList<LocationUser> mlocations = new ArrayList<>();
    private SupportMapFragment mapFragment;
    private double mLatitude = 0.0;
    private double mLongitude = 0.0;
    private LocationManager locationManager;
    private GoogleMap googleMap;


    public FragmentOnMap() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment FragmentOnMap.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentOnMap newInstance(ArrayList<LocationUser> mlocations) {
        FragmentOnMap fragment = new FragmentOnMap();
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
        View v = inflater.inflate(R.layout.fragment_fragment_on_map, container, false);
        initVeiws(v);
        return v;

    }

    private void initVeiws(View v) {
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map2, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMinZoomPreference(16f);
        if (mlocations != null && mlocations.size() > 0) {
            mLatitude = mlocations.get(0).getLatitude();
            mLongitude = mlocations.get(0).getLongitude();
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLatitude, mLongitude)));
//       googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        InfoWindowAdapter markerWindowView = new  InfoWindowAdapter(getContext());
        googleMap.setInfoWindowAdapter(markerWindowView);
        googleMap.setOnMarkerClickListener(this);

        if (mlocations.size() > 0) {
            for (LocationUser locationUse : mlocations) {
                addMarker(locationUse.getLatitude(), locationUse.getLongitude(),locationUse);
            }
        }

    }

    private void addMarker(double Latitude,double Longitude, LocationUser locationUser) {

        try {
//            MarkerOptions m = new MarkerOptions()
//                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.red_marker))
//                    .position(new LatLng(Latitude, Longitude))
////                .title("ltapel")
//                    ;
//            googleMap.addMarker(m);

            Marker m = googleMap.addMarker(new MarkerOptions()
                    .icon(bitmapDescriptorFromVector(getContext(), R.drawable.red_marker))
                    .position(new LatLng(Latitude, Longitude))
                    .title("info"));

            m.setTag(locationUser);

           m.showInfoWindow();
        }
        catch (NullPointerException u){
            Log.e("nnnnnnnnnnnnnnnnnnnnn","שגיאה במרקר!!!!!!!!!!!!!!");
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentOnMap();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentOnMapListener) {
            mListener = (OnFragmentOnMapListener) context;
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
    public boolean onMarkerClick(Marker marker) {
        Log.e("uu","ggggggggggggggggggggggggggggggg");
        marker.showInfoWindow();
        return true;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentOnMapListener {
        // TODO: Update argument type and name
        void onFragmentOnMap();
    }
}
