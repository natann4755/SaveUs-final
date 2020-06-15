package com.example.saveus.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.saveus.Activitys.HomeActivity;
import com.example.saveus.Objects.LocationUser;
import com.example.saveus.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**

 */
public class FragmentAddPlaice extends Fragment  {

    private OnFragmentAddPlaiceListener mListener;

    private LinearLayout topLogo;
    private LocationUser mLocationUser;
    private ImageView close;
    private TextView buttonAddDateTV;
    private ImageView buttonAddDateIV;
    private LinearLayout buttonStarsTimeLL;
    private LinearLayout buttonEndTimeLL;
    private TextView buttonSaveTV;
    private View.OnClickListener addDateLisiner;
    private View.OnClickListener addStartTimeLisiner;
    private View.OnClickListener addEndTimeLisiner;
    private View.OnClickListener saveAll;
    private View.OnClickListener clearAll;
    private TextView startTime;
    private TextView endTime;
    private TextView totalTime;
    private TextView buttonCleerTV;
    private ImageView buttonCleerIV;
    private EditText editTextAddrees;
    private int startHourCelecd;
    private int startMiniteCelecd;



    public FragmentAddPlaice() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static FragmentAddPlaice newInstance() {
        FragmentAddPlaice fragment = new FragmentAddPlaice();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_add_plaice, container, false);

        initLiseners();
        initViews(v);
        return v;

    }




    private void initLiseners() {

        addDateLisiner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mYear;
                final int mMonth;
                final int mDay;
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext() , R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                if (year>mYear || (year==mYear && monthOfYear>mMonth) || (year==mYear && monthOfYear==mMonth && dayOfMonth > mDay)){
                                    Toast.makeText(getContext(), getContext().getString(R.string.tost_date_not_good) , Toast.LENGTH_SHORT).show();
                                }else {
                                    String folldate = HomeActivity.fixNam(dayOfMonth) + "/" + HomeActivity.fixNam(monthOfYear + 1) + "/" + year;
                                    buttonAddDateTV.setText(folldate);
                                    mLocationUser.setYaer(year);
                                    mLocationUser.setManth((monthOfYear + 1));
                                    mLocationUser.setDay(dayOfMonth);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        };

        addStartTimeLisiner = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Calendar c = Calendar.getInstance();
               int mHour = c.get(Calendar.HOUR_OF_DAY);
               int mMinut = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                startHourCelecd = hourOfDay;
                                startMiniteCelecd = minute;
                                String StartT = HomeActivity.fixNam(hourOfDay) + ":" + HomeActivity.fixNam(minute) + ":00";
                                startTime.setText(StartT);
                                mLocationUser.setStartHour(hourOfDay);
                                mLocationUser.setStartMinit(minute);
                                mLocationUser.setStartSecond(0);
                            }
                        }, mHour, mMinut, true);
                timePickerDialog.show();

            }
        };

        addEndTimeLisiner = new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Calendar c = Calendar.getInstance();
               int mHour = c.get(Calendar.HOUR_OF_DAY);
               int mMinut = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String endT = HomeActivity.fixNam(hourOfDay)+ ":" + HomeActivity.fixNam(minute) + ":00";
                                endTime.setText(endT);
                                mLocationUser.setEndTime(HomeActivity.fixNam(hourOfDay)+ ":" + HomeActivity.fixNam(minute));
                                mLocationUser.setEndSecond(0);
                                calculateTime(hourOfDay, minute);
                            }
                        }, mHour, mMinut, true);
                timePickerDialog.show();
            }
        };
        saveAll = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  edittext = editTextAddrees.getText().toString();
                if (edittext == null || mLocationUser.getYaer() == 0 || mLocationUser.getTime()==null  ) {
                    Toast.makeText(getContext(),getContext().getString(R.string.tost_no_full) , Toast.LENGTH_SHORT).show();
                } else{
                    LatLng l = getLocationFromAddress(editTextAddrees.getText().toString());
                mLocationUser.setLatitude(l.latitude);
                mLocationUser.setLongitude(l.longitude);
                mLocationUser.setAdsress(editTextAddrees.getText().toString());
                mListener.sendLocationUser(mLocationUser);
                mListener.moveBakToMapOrRV();
            }
            }
        };

        clearAll = new View.OnClickListener() {          //להשלים!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            @Override
            public void onClick(View v) {
                startTime.setText("00:00:00");
                endTime.setText("00:00:00");
                totalTime.setText("00:00:00");
                editTextAddrees.setText("");
                buttonAddDateTV.setText("");


            }
        };

    }

    private void calculateTime(int hourOfDay, int minute) {
        int totalStartMinite = (startHourCelecd*60) + startMiniteCelecd;
        int totalEndMinite = (hourOfDay*60) + minute;
        int totalMinite = totalEndMinite - totalStartMinite;
        if (totalMinite <=0){
            Toast.makeText(getContext(),getContext().getString(R.string.tost_ouer_not_good) , Toast.LENGTH_SHORT).show();
        }else {
            int totalHour = totalMinite / 60;
            int totalMinit = totalMinite % 60;
            String totalT = HomeActivity.fixNam(totalHour) + ":" + HomeActivity.fixNam(totalMinit) + ":00";
            totalTime.setText(totalT);
            mLocationUser.setTime(totalT);
        }
    }

    private void initViews(View v) {
        mLocationUser = new LocationUser(0.0,0.0);
        topLogo = getActivity().findViewById(R.id.top_logo_LL);
        topLogo.setVisibility(View.GONE);
        buttonAddDateTV = v.findViewById(R.id.add_plaice_date_TV);
        buttonAddDateIV = v.findViewById(R.id.add_plaice_date_IV);
        buttonAddDateIV.setOnClickListener(addDateLisiner);
        buttonAddDateTV.setOnClickListener(addDateLisiner);
        buttonStarsTimeLL = v.findViewById(R.id.add_plaice_butoon_start_time_LL);
        buttonEndTimeLL = v.findViewById(R.id.add_plaice_butoon_end_time_LL);
        buttonStarsTimeLL.setOnClickListener(addStartTimeLisiner);
        buttonEndTimeLL.setOnClickListener(addEndTimeLisiner);
        startTime =  v.findViewById(R.id.add_plaice_start_time_TV);
        endTime = v.findViewById(R.id.add_plaice_end_time_TV);
        totalTime = v.findViewById(R.id.add_plaice_total_time_TV);
        editTextAddrees = v.findViewById(R.id.add_plaice_edit_text_addres_TV);
        buttonSaveTV = v.findViewById(R.id.add_plaice_button_save_TV);
        buttonSaveTV.setOnClickListener(saveAll);
        buttonCleerIV  = v.findViewById(R.id.add_plaice_butoon_cleerAll_IV);
        buttonCleerTV  = v.findViewById(R.id.add_plaice_butoon_cleerAll_TV);
        buttonCleerTV.setOnClickListener(clearAll);
        buttonCleerIV.setOnClickListener(clearAll);
        close = v.findViewById(R.id.add_plaice_button_close_IV);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.moveBakToMapOrRV();
            }
        });
    }

    public LatLng getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(getContext());
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng((double) (location.getLatitude() ),
                    (double) (location.getLongitude() ));

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p1;
    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentAddPlaice();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentAddPlaiceListener) {
            mListener = (OnFragmentAddPlaiceListener) context;
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
    public void onPause() {
        topLogo.setVisibility(View.VISIBLE);
        super.onPause();
    }

    public interface OnFragmentAddPlaiceListener {
        // TODO: Update argument type and name
        void onFragmentAddPlaice();
        void sendLocationUser(LocationUser locationUser);
        void moveBakToMapOrRV();
    }
}
