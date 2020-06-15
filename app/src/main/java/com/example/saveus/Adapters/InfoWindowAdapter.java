package com.example.saveus.Adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.saveus.Objects.LocationUser;
import com.example.saveus.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Collection;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private View mInfoWindow;
    private Context context;

    public InfoWindowAdapter(Context context) {
        this.context = context;
        mInfoWindow = LayoutInflater.from(context).inflate(R.layout.info_window,null);
    }

    private void initInfoWindow (Marker marker, View info){}

    @Override
    public View getInfoWindow(Marker marker) {
        initInfoWindow(marker,mInfoWindow);
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        initInfoWindow(marker,mInfoWindow);
//        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 87, context.getResources().getDisplayMetrics());
//        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 166, context.getResources().getDisplayMetrics());
//
//        mInfoWindow.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
        return null;
    }

//    private ArrayList <LocationUser> allLocations;
//    private View markerInfoWindow;
//    private TextView adress;
//    private TextView date;
//    private TextView endTime;
//    private TextView startTime;
//    private TextView time;
//
//    public InfoWindowAdapter(Context context, ArrayList <LocationUser> allLocations ) {
//        markerInfoWindow =  LayoutInflater.from(context).inflate(R.layout.info_window, null);
//        this.allLocations = allLocations;
//        initViews();
//    }
//
//    private void initViews() {
//        adress = markerInfoWindow.findViewById(R.id.info_window_addresTV);
//        date = markerInfoWindow.findViewById(R.id.info_window_dateTV);
//        endTime = markerInfoWindow.findViewById(R.id.info_window_endTimeTV);
//        startTime = markerInfoWindow.findViewById(R.id.info_window_startTimeTV);
//        time = markerInfoWindow.findViewById(R.id.info_window_timeTV);
//    }
//
//    @Override
//    public View getInfoWindow(Marker marker) {
//
//        ArrayList<LocationUser> copyAreey = new ArrayList<>();
//        copyAreey.addAll(allLocations);
//        for (int i = 0; i <copyAreey.size() ; i++) {
//            if (copyAreey.get(i).getLatitude()!= marker.getPosition().latitude || copyAreey.get(i).getLongitude()!= marker.getPosition().longitude){
//                copyAreey.remove(i);
//            }
//        }
//        if (copyAreey.size()==1){       // לטפל במה קורה אם הוא היה פעמיים!!!!!
//            adress.setText(copyAreey.get(0).getAdsress());
//            date.setText(copyAreey.get(0).getDay()+"/"+copyAreey.get(0).getManth()+"/"+copyAreey.get(0).getYaer());
//            endTime.setText(copyAreey.get(0).getEndTime());
////            startTime.setText(copyAreey.get(0).getStartTime());
//            time.setText(copyAreey.get(0).getTime());
//        }
//
//        return markerInfoWindow;
//    }
//
//    @Override
//    public View getInfoContents(Marker marker) {
//        return null;
//    }
//}
}
