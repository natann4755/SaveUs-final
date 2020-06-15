package com.example.saveus.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saveus.Objects.LocationUser;
import com.example.saveus.Objects.UserProfile;
import com.example.saveus.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class FragmentProfile extends Fragment {


    private OnFragmentProfileListener mListener;
    private ImageView logoProfile;
    private ImageView closeProfile;
    public static String KEY_SharedPreferences_PROFILE = "KEY_sr_PROFILE";
    public static String KEY__PROFILE = "KEY_PROFILE";
    private UserProfile mUserProfile;
    private TextView userName;
    private TextView fonNumber;
    private TextView dateOfBirth;
    private TextView email;
    private TextView language;
    private TextView getMobileAlerts;
    private ImageView buttonEditProfile;


    public FragmentProfile() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentProfile newInstance() {
        FragmentProfile fragment = new FragmentProfile();
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
        View v = inflater.inflate(R.layout.fragment_fragment_profile, container, false);
        getFromServer();
        initVeiws(v);
        setProfile();
        return v;
    }

    private void initVeiws(View v) {
        logoProfile = getActivity().findViewById(R.id.profile2_button_IV);
        logoProfile.setVisibility(View.GONE);
        closeProfile =  getActivity().findViewById(R.id.profile2_close_profile_IV);
        closeProfile.setVisibility(View.VISIBLE);
        userName = v.findViewById(R.id.profile_user_name_TV);
        fonNumber = v.findViewById(R.id.profile_fon_TV);
        dateOfBirth = v.findViewById(R.id.profile_date_of_berth_TV);
        email = v.findViewById(R.id.profile_email_TV);
        language = v.findViewById(R.id.profile_language_TV);
        getMobileAlerts = v.findViewById(R.id.profile_receive_alerts_TV);
        buttonEditProfile = v.findViewById(R.id.profile_button_edit_profil_IV);
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditProfile(mUserProfile);
            }
        });
        closeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCloseProfilPresd();
            }
        });
    }

    private void setProfile() {
        if (mUserProfile == null){
            return;
        }
        if (mUserProfile.getUserName()!= null) {
            userName.setText(mUserProfile.getUserName());
        }
        if (mUserProfile.getFonNumber()!= null){
            fonNumber.setText(mUserProfile.getFonNumber());
        }
        if (mUserProfile.getDayOfBerth() != null){dateOfBirth.setText(mUserProfile.getDayOfBerth());}
        if (mUserProfile.getEmail()!= null){email.setText(mUserProfile.getEmail()); }
        if (mUserProfile.getLanguage()!= null){language.setText(mUserProfile.getLanguage()); }
        if (mUserProfile.isReceiveAlerts()!= false){
            getMobileAlerts.setText("כן");
        }else {
            getMobileAlerts.setText("לא");
        }
    }

    private void getFromServer() {
        SharedPreferences prefs = getContext().getSharedPreferences(
                KEY_SharedPreferences_PROFILE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(KEY__PROFILE, null);
        if (json != null) {
            mUserProfile = gson.fromJson(json, UserProfile.class);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onEditProfile(mUserProfile);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentProfileListener) {
            mListener = (OnFragmentProfileListener) context;
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
        logoProfile.setVisibility(View.VISIBLE);
        closeProfile.setVisibility(View.GONE);
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
    public interface OnFragmentProfileListener {
        // TODO: Update argument type and name
        void onEditProfile(UserProfile mUserProfile);
        void onCloseProfilPresd();
    }
}
