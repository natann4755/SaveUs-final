package com.example.saveus.Fragments;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.saveus.Adapters.ViewPagerAdapter;
import com.example.saveus.Objects.LocationUser;
import com.example.saveus.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;
import static com.example.saveus.Fragments.HomeMapFragment.KEY_mlocations_AREEY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link MyPlacesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPlacesFragment extends Fragment  {



    private OnFragmentMyPlacesListener mListener;
    private ArrayList<LocationUser> mlocations;
    private ViewPager myPlacesViewPager;
    private ViewPagerAdapter myViewPagerAdapter;
    private ArrayList<Fragment> listFragment;
    private FragmentMyPlacesRV mFragmentMyPlacesRV;
    private FragmentOnMap mFragmentOnMap;
    private LinearLayout butoonAddPlace;
    private LinearLayout topLogo;
    private ImageView  logoProfile;
    private boolean goToRV = false;



    public MyPlacesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment MyPlacesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPlacesFragment newInstance(ArrayList<LocationUser> mlocations) {
        MyPlacesFragment fragment = new MyPlacesFragment();
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
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_my_places, container, false);
        initVP(v);
        initVeiws(v);
        return v;
    }

    private void initVeiws(View v) {
//        topLogo = getActivity().findViewById(R.id.top_logo_LL);
//        topLogo.setVisibility(View.VISIBLE);
//        logoProfile = getActivity().findViewById(R.id.profile2_close_profile_IV);
//        logoProfile.setVisibility(View.GONE);
        butoonAddPlace = v.findViewById(R.id.butoon_add_place_LL);
        butoonAddPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addPlaceClick();
            }
        });
    }


    private void initVP(View v) {
        listPlacesFragments();
        myPlacesViewPager = v.findViewById(R.id.my_places_ViewPager_VP);
        myViewPagerAdapter = new ViewPagerAdapter(getFragmentManager(),listFragment,getContext());
        myPlacesViewPager.setAdapter(myViewPagerAdapter);
        TabLayout tabLayout = v.findViewById(R.id.my_place_TL);
        tabLayout.setupWithViewPager(myPlacesViewPager);
        if (goToRV){
            myPlacesViewPager.setCurrentItem(1);
        }

    }

    private void listPlacesFragments() {
        mFragmentOnMap = FragmentOnMap.newInstance(mlocations);
        mFragmentMyPlacesRV = FragmentMyPlacesRV.newInstance(mlocations);
        listFragment = new ArrayList<>();
        listFragment.add(mFragmentOnMap);
        listFragment.add(mFragmentMyPlacesRV);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMyPlacesInteraction();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentMyPlacesListener) {
            mListener = (OnFragmentMyPlacesListener) context;
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

//    public void updateNewPlace(LocationUser locationUse) {
//
////        לעדכן נקודות במפה וכן לעדכן את הריסקלרוויו ת נצרך במקרה שהוא יחזור לדף של מי פליסס בלא ללחוץ על הבוטם נוויגישן
//    }

    public void moveToRV (){
        goToRV = true;
    }

//    public int getCorectItemVP() {
//        if (myPlacesViewPager != null) {
//            return myPlacesViewPager.getCurrentItem();
//        }else {
//            return 0;
//        }
//    }

    @Override
    public void onStop() {
        mListener.getCorectItemVP_ForBac(myPlacesViewPager.getCurrentItem());
        super.onStop();
    }



    public interface OnFragmentMyPlacesListener {
        // TODO: Update argument type and name
        void onMyPlacesInteraction();
        void addPlaceClick();
        void getCorectItemVP_ForBac(int CorectItem);
    }
}
