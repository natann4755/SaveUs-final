package com.example.saveus.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saveus.Activitys.HomeActivity;
import com.example.saveus.Objects.UserProfile;
import com.example.saveus.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Locale;

import static com.example.saveus.Fragments.FragmentProfile.KEY_SharedPreferences_PROFILE;
import static com.example.saveus.Fragments.FragmentProfile.KEY__PROFILE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link FragmentProfileEdit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfileEdit extends Fragment implements AdapterView.OnItemSelectedListener {

    private OnFragmentonEditProfile mListener;
    private LinearLayout topLogo;
    private UserProfile mUserProfile;
    private String[] itemsSpinner;

    private ImageView close;
    private TextView save;
    private EditText nameET;
    private EditText emailET;
    private EditText fonET;
    private TextView dateOfBerth;
    private TextView dateOfBerthResolt;
    private Spinner dropdown;
    private CheckBox getMobileAlerts;
    private TextView cleerAllTV;
    private ImageView cleerAllIV;
    private static String KEY_PARSLABEL ="KEY_PARSLABEL";

    private String folldate;

    private View.OnClickListener addDateLisiner;
    private View.OnClickListener saveLisiner;
    private View.OnClickListener cleerLisiner;



    public FragmentProfileEdit() {
        // Required empty public constructor
    }

    public static FragmentProfileEdit newInstance(UserProfile userProfile) {
        FragmentProfileEdit fragment = new FragmentProfileEdit();
        Bundle args = new Bundle();
        args.putParcelable(KEY_PARSLABEL, userProfile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserProfile = getArguments().getParcelable(KEY_PARSLABEL);
            if (mUserProfile == null){
                mUserProfile = new UserProfile(false);            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View v =  inflater.inflate(R.layout.fragment_fragment_profile_edit, container, false);
     initLisiners();
     initVeiws(v);
     setProfil();
        return v;
    }

    private void setProfil() {
        if (mUserProfile == null){
            return;
        }
        nameET.setHint(mUserProfile.getUserName());
        emailET.setHint(mUserProfile.getEmail());
        fonET.setHint(mUserProfile.getFonNumber());

        if (mUserProfile.getDayOfBerth() != null &&!mUserProfile.getDayOfBerth().equals("")) {
            dateOfBerthResolt.setText(mUserProfile.getDayOfBerth());
        }
        if (mUserProfile.isReceiveAlerts() == true){
            getMobileAlerts.setChecked(true);
        }else {
            getMobileAlerts.setChecked(false);
        }
    }

    private void initLisiners() {

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
                                    folldate = HomeActivity.fixNam(dayOfMonth) + "/" + HomeActivity.fixNam(monthOfYear + 1) + "/" + year;
                                    dateOfBerthResolt.setText(folldate);

                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        };

        saveLisiner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldName = null;
                String oldEmail = null;
                String oldFon = null;
                if (!nameET.getHint().equals(getString(R.string.edit_profile_name))){
                    oldName = (String) nameET.getHint();
                }
                if (!emailET.getHint().equals(getString(R.string.edit_profile_email))){
                    oldEmail = (String) emailET.getHint();
                }
                if (!fonET.getHint().equals(getString(R.string.edit_profile_fon))){
                    oldFon = (String) fonET.getHint();
                }

                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String fonNumber = fonET.getText().toString();

                if ((name.equals("") && oldName == null )|| (email.equals("") && oldEmail == null )|| (fonNumber.equals("") && oldFon == null) ) {
                    Toast.makeText(getContext(), getContext().getString(R.string.tost_no_full), Toast.LENGTH_SHORT).show();
                } else {
                    if (name.equals("")){
                        name = (String) nameET.getHint();
                    }
                    if (email.equals("")){
                        email = (String) emailET.getHint();
                    }
                    if (fonNumber.equals("")){
                        fonNumber = (String) fonET.getHint();
                    }

                    mUserProfile.setUserName(name);
                    mUserProfile.setEmail(email);
                    mUserProfile.setFonNumber(fonNumber);
                    mUserProfile.setDayOfBerth(folldate);
                    mUserProfile.setReceiveAlerts(getMobileAlerts.isChecked());
                    mListener.setCorrectLanguage(mUserProfile.getLanguage());
                    updateServerProfile(mUserProfile);
                    goBeckToProfile();
                }
            }
        };
        cleerLisiner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProfil();
                nameET.setText("");
                emailET.setText("");
                fonET.setText("");
                if (mUserProfile.getDayOfBerth().equals("")) {
                    dateOfBerthResolt.setText(getString(R.string.edit_profile_m_date));
                }else {
                    dateOfBerthResolt.setText(mUserProfile.getDayOfBerth());
                }
                if (mUserProfile.isReceiveAlerts() == true){
                    getMobileAlerts.setChecked(true);
                }else {
                    getMobileAlerts.setChecked(false);
                }
            }
        };

        }




    private void initVeiws(View v) {
        topLogo = getActivity().findViewById(R.id.top_logo_LL);
        topLogo.setVisibility(View.GONE);
        close = v.findViewById(R.id.edit_profile_button_close_IV_);
        save = v.findViewById(R.id.edit_profile_button_save_TV_);
        nameET = v.findViewById(R.id.edit_profile_name_ET_);
        fonET = v.findViewById(R.id.edit_profile_fon_ET_);
        emailET = v.findViewById(R.id.edit_profile_email_ET_);
        dateOfBerth = v.findViewById(R.id.edit_profile_date_o_b_1TV_);
        dateOfBerthResolt = v.findViewById(R.id.edit_profile_date_o_b2_TV_);
        getMobileAlerts = v.findViewById(R.id.edit_profile_getMobileAlerts_CB_);
        cleerAllIV = v.findViewById(R.id.edit_profile_button_cleerAll_IV_);
        cleerAllTV = v.findViewById(R.id.edit_profile_button_cleerAll_TV_);
        dropdown = v.findViewById(R.id.spinner1);
//        String titelSpiner = initTitelSpiner();
        itemsSpinner = new String[]{"עברית", "אנגלית"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, itemsSpinner);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        dateOfBerth.setOnClickListener(addDateLisiner);
        dateOfBerthResolt.setOnClickListener(addDateLisiner);
        save.setOnClickListener(saveLisiner);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBeckToProfile();

            }
        });
        cleerAllTV.setOnClickListener(cleerLisiner);
        cleerAllIV.setOnClickListener(cleerLisiner);
    }

    private void goBeckToProfile() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onEditProfile();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentonEditProfile) {
            mListener = (OnFragmentonEditProfile) context;
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (mUserProfile != null) {
            mUserProfile.setLanguage(itemsSpinner[position]);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    public interface OnFragmentonEditProfile {
        // TODO: Update argument type and name
        void onEditProfile();
        void setCorrectLanguage(String Language);
    }

    private void updateServerProfile(UserProfile userProfile) {
        SharedPreferences prefs = getContext().getSharedPreferences(
                KEY_SharedPreferences_PROFILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mUserProfile);
        prefsEditor.putString(KEY__PROFILE,json);
        prefsEditor.commit();
    }

//    private String initTitelSpiner() {
//        if (mUserProfile.getLanguage()==""||mUserProfile.getLanguage()==null){
//            return "לשנות!!!!!!!!!!!";
//        }else {
//            return mUserProfile.getLanguage();
//        }
//    }
}
