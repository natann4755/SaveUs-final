package com.example.saveus.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static com.example.saveus.Fragments.HomeMapFragment.KEY_mlocations_AREEY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link FragmentEditPlace#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditPlace extends Fragment {


    private OnFragmentEditPlaceListener mListener;
    private ArrayList<LocationUser> mlocationsAreey = new ArrayList<>();
    private LocationUser mLocationUser;
    private LocationUser oldLocationUser;
    private static String KEY_1_locationUser = "KEY_POSISHEN";
    private LinearLayout topLogo;
    private ImageView close;
    private TextView buttonAddDateTV;
    private ImageView buttonAddDateIV;
    private LinearLayout buttonStarsTimeLL;
    private LinearLayout buttonEndTimeLL;
    private TextView buttonSaveTV;
    private TextView startTime;
    private TextView endTime;
    private TextView totalTime;
    private TextView buttonCleerTV;
    private ImageView buttonCleerIV;
    private EditText editTextAddrees;
    private int startHourCelecd;
    private int startMiniteCelecd;
    private View.OnClickListener addDateLisiner;
    private View.OnClickListener addStartTimeLisiner;
    private View.OnClickListener addEndTimeLisiner;
    private View.OnClickListener saveAll;
    private View.OnClickListener deletePlace;

    public FragmentEditPlace() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment FragmentEditPlace.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEditPlace newInstance(ArrayList<LocationUser> mlocations, LocationUser locationUser) {
        FragmentEditPlace fragment = new FragmentEditPlace();
        Bundle args = new Bundle();
        args.putParcelableArrayList(KEY_mlocations_AREEY,mlocations);
        args.putParcelable(KEY_1_locationUser,locationUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mlocationsAreey = getArguments().getParcelableArrayList(KEY_mlocations_AREEY);
            oldLocationUser = getArguments().getParcelable(KEY_1_locationUser);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_fragment_edit_place, container, false);
        initLiseners();
       initVeiws(v);
       setTextFromMyLocation();
        return v;
    }



    private void initVeiws (View v) {
        mLocationUser = new LocationUser (oldLocationUser);
        topLogo = getActivity().findViewById(R.id.top_logo_LL);
        topLogo.setVisibility(View.GONE);
        buttonAddDateTV = v.findViewById(R.id.edit_date_TV);
        buttonAddDateIV = v.findViewById(R.id.edit_date_IV);
        buttonAddDateIV.setOnClickListener(addDateLisiner);
        buttonAddDateTV.setOnClickListener(addDateLisiner);
        buttonStarsTimeLL = v.findViewById(R.id.edit_butoon_start_time_LL);
        buttonEndTimeLL = v.findViewById(R.id.edit_butoon_end_time_LL);
        buttonStarsTimeLL.setOnClickListener(addStartTimeLisiner);
        buttonEndTimeLL.setOnClickListener(addEndTimeLisiner);
        startTime =  v.findViewById(R.id.edit_start_time_TV);
        endTime = v.findViewById(R.id.edit_end_time_TV);
        totalTime = v.findViewById(R.id.edit_total_time_TV);
        editTextAddrees = v.findViewById(R.id.edit_edit_text_addres_TV);
        buttonSaveTV = v.findViewById(R.id.edit_button_save_TV);
        buttonSaveTV.setOnClickListener(saveAll);
        buttonCleerIV  = v.findViewById(R.id.edit_butoon_cleerAll_IV);
        buttonCleerTV  = v.findViewById(R.id.edit_butoon_cleerAll_TV);
        buttonCleerTV.setOnClickListener(deletePlace);
        buttonCleerIV.setOnClickListener(deletePlace);
        close = v.findViewById(R.id.edit_button_close_IV);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mListener.moveBakToRv();
            }
        });
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                if (year>mYear || (year==mYear && monthOfYear>mMonth) || (year==mYear && monthOfYear==mMonth && dayOfMonth > mDay)){
                                    Toast.makeText(getContext(),getContext().getString(R.string.tost_date_not_good) , Toast.LENGTH_SHORT).show();
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
                if (edittext == null ||  mLocationUser.getYaer() == 0 || mLocationUser.getTime()==null  ) {
                    Toast.makeText(getContext(),getContext().getString(R.string.tost_no_full) , Toast.LENGTH_SHORT).show();
                } else{
                    LatLng l = getLocationFromAddress(editTextAddrees.getText().toString());
                    if (l != null){
                        mLocationUser.setLatitude(l.latitude);
                        mLocationUser.setLongitude(l.longitude);
                        mLocationUser.setAdsress(editTextAddrees.getText().toString());
                    }
                    removeOld();
                    mlocationsAreey.add(0,mLocationUser);
                    mListener.updatedAreey();
                    mListener.moveBakToRv();
                }
            }
        };

        deletePlace = new View.OnClickListener() {          //להשלים!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            @Override
            public void onClick(View v) {
                removeOld();
                mListener.updatedAreey();
                mListener.moveBakToRv();
            }
        };

    }

    private void removeOld() {
        int posishen = 0;
        for (int i = 0; i <mlocationsAreey.size() ; i++) {
            if (mlocationsAreey.get(i).equals(oldLocationUser)){
                posishen = i;
            }
        }
        mlocationsAreey.remove(posishen);
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

    private void setTextFromMyLocation() {
        editTextAddrees.setHint(mLocationUser.getAdsress());
        buttonAddDateTV.setText(HomeActivity.fixNam(mLocationUser.getDay()) + "/" + HomeActivity.fixNam(mLocationUser.getManth()) + "/" + mLocationUser.getYaer());
        startTime.setText(HomeActivity.fixNam(mLocationUser.getStartHour()) + ":" + HomeActivity.fixNam(mLocationUser.getStartMinit())+":" +HomeActivity.fixNam(mLocationUser.getStartSecond()));
        endTime.setText(mLocationUser.getEndTime() + ":" + HomeActivity.fixNam(mLocationUser.getEndSecond()));
        totalTime.setText(mLocationUser.getTime());
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
            mListener.onFragmentEditPlace();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentEditPlaceListener) {
            mListener = (OnFragmentEditPlaceListener) context;
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
    public interface OnFragmentEditPlaceListener {
        // TODO: Update argument type and name
        void onFragmentEditPlace();
        void updatedAreey();
        void moveBakToRv();
    }
}
