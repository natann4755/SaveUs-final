package com.example.saveus.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saveus.Activitys.HomeActivity;
import com.example.saveus.Adapters.RecyclerAdaprerMyPlaces;
import com.example.saveus.Adapters.RecyclerAdapterDatePlace;
import com.example.saveus.Objects.DateListLocations;
import com.example.saveus.Objects.LocationUser;
import com.example.saveus.Objects.myDate;
import com.example.saveus.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.saveus.Fragments.HomeMapFragment.KEY_mlocations_AREEY;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentMyPlacesRV#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMyPlacesRV extends Fragment implements RecyclerAdaprerMyPlaces.OnChangeLocationListener {


    private OnFragmentMyPlacesRVListener mListener;
    private ArrayList<LocationUser> mlocations = new ArrayList<>();
    private ArrayList <DateListLocations> mArreyDataListLocation = new ArrayList();
    private RecyclerView myRecyclerView;
    private RecyclerAdapterDatePlace mAdapter;

    private LinearLayout startDateButton;
    private TextView startDate;
    private LinearLayout endDateButton;
    private TextView endDate;
    private LinearLayout buttonFilter;

    private View.OnClickListener startDateLisener;
    private View.OnClickListener endDateLisener;
    private View.OnClickListener filterLisener;
//    private myDate start = new myDate();
//    private myDate end = new myDate();
    private Calendar calendarStart = Calendar.getInstance();
    private Calendar calendarEnd = Calendar.getInstance();



    public FragmentMyPlacesRV() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentMyPlacesRV newInstance(ArrayList<LocationUser> mlocations) {
        FragmentMyPlacesRV fragment = new FragmentMyPlacesRV();
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
        View v = inflater.inflate(R.layout.fragment_fragment_my_places_rv, container, false);
        initLiseners();
        initVeaws(v);
        return v;
    }

    private void initLiseners() {
        startDateLisener = new View.OnClickListener() {
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
                                if (year > mYear || (year == mYear && monthOfYear > mMonth) || (year == mYear && monthOfYear == mMonth && dayOfMonth > mDay)) {
                                    Toast.makeText(getContext(), getContext().getString(R.string.tost_date_not_good), Toast.LENGTH_SHORT).show();
                                } else {
                                    String folldate = HomeActivity.fixNam(dayOfMonth) + "." + HomeActivity.fixNam(monthOfYear + 1) + "." + year;
                                    startDate.setText(folldate);
                                    calendarStart.set(Calendar.YEAR,year);
                                    calendarStart.set(Calendar.MONTH,(monthOfYear));
                                    calendarStart.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                                    calendarStart.set(Calendar.HOUR_OF_DAY, 00);
                                    calendarStart.set(Calendar.MINUTE, 00);
                                    calendarStart.set(Calendar.SECOND, 01);
//                                    start.set(year,(monthOfYear + 1),dayOfMonth);
//                                    start.setYear(year);
//                                    start.setMonth(monthOfYear+1);
//                                    start.setDay(dayOfMonth);

                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        };

        endDateLisener = new View.OnClickListener() {
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
                                if (year > mYear || (year == mYear && monthOfYear > mMonth) || (year == mYear && monthOfYear == mMonth && dayOfMonth > mDay+1)) {
                                    Toast.makeText(getContext(), getContext().getString(R.string.tost_date_not_good), Toast.LENGTH_SHORT).show();
                                } else {
                                    String folldate = HomeActivity.fixNam(dayOfMonth) + "." + HomeActivity.fixNam(monthOfYear + 1) + "." + year;
                                    endDate.setText(folldate);
                                    calendarEnd.set(year,(monthOfYear),dayOfMonth);
                                    calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
                                    calendarEnd.set(Calendar.MINUTE, 59);
                                    calendarEnd.set(Calendar.SECOND, 01);
//                                    end.setYear(year);
//                                    end.setMonth(monthOfYear+1);
//                                    end.setDay(dayOfMonth);

                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        };
        filterLisener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mAdapter.filterAreeyDate(start,end);
                if (startDate.getText() == "" || endDate.getText() == "") {
                    Toast.makeText(getContext(), getContext().getString(R.string.tost_no_full), Toast.LENGTH_SHORT).show();
                } else {
                    mAdapter.filterAreeyDateWithCalander(calendarStart, calendarEnd);
                }
            }
        };
    }


    private void initVeaws(View v) {
        startDateButton = v.findViewById(R.id.filter_date_button_start_LL);
        startDateButton.setOnClickListener(startDateLisener);
        startDate = v.findViewById(R.id.filter_date_text_start_TV);
        endDateButton =  v.findViewById(R.id.filter_date_button_end_LL);
        endDateButton.setOnClickListener(endDateLisener);
        endDate =  v.findViewById(R.id.filter_date_text_end_TV);
        buttonFilter =  v.findViewById(R.id.filter_date_button_filter_LL);
        buttonFilter.setOnClickListener(filterLisener);
        initArreyDataListLocation();
        myRecyclerView = v.findViewById(R.id.my_places_recycler_view_RV);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerAdapterDatePlace(getContext(), mArreyDataListLocation,this);
        myRecyclerView.setAdapter(mAdapter);
    }

    private void initArreyDataListLocation() {
        if (mlocations.size()>0){
            createDateListLocations(mlocations.get(0));
        }
        for (int i = 1; i <mlocations.size() ; i++) {
            if (mlocations.get(i).getYaer() == mArreyDataListLocation.get(mArreyDataListLocation.size()-1).getYear()
            && mlocations.get(i).getManth() == mArreyDataListLocation.get(mArreyDataListLocation.size()-1).getMonth()
            && mlocations.get(i).getDay() == mArreyDataListLocation.get(mArreyDataListLocation.size()-1).getDay()){
                mArreyDataListLocation.get(mArreyDataListLocation.size()-1).getmLocations().add(mlocations.get(i));
                int a = 22;
            }
            else {
                createDateListLocations (mlocations.get(i));
            }
        }
    }

    public void createDateListLocations (LocationUser locationUser){
        mArreyDataListLocation.add(new DateListLocations(HomeActivity.fixNam(locationUser.getDay())+"/"+HomeActivity.fixNam(locationUser.getManth())+"/"+HomeActivity.fixNam(locationUser.getYaer()),
                locationUser.getYaer(),  locationUser.getManth(),locationUser.getDay(), locationUser));

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentMyPlacesRV();
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentMyPlacesRVListener) {
            mListener = (OnFragmentMyPlacesRVListener) context;
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
    public void ChangeLocationPressed(LocationUser locationUser) {
        mListener.ChangeLocationPressed(locationUser);
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
    public interface OnFragmentMyPlacesRVListener {
        // TODO: Update argument type and name
        void onFragmentMyPlacesRV();
        void ChangeLocationPressed(LocationUser locationUser);
    }
}
