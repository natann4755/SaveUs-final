package com.example.saveus.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.saveus.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link Onboarding3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Onboarding3Fragment extends Fragment implements View.OnClickListener {


    private OnFragmentOnboarding3Listener mListener;

    private Button moovToOnbording1;
    private Button moovToOnbording2;
    private Button moovToOnbording3;

    public Onboarding3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment Onboarding3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Onboarding3Fragment newInstance() {
        Onboarding3Fragment fragment = new Onboarding3Fragment();
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
        // Inflate the layout for this fragment

        View myFragment = inflater.inflate(R.layout.fragment_onboarding3, container, false);
        initButtons(myFragment);
        return myFragment;
    }

    private void initButtons(View myFragment) {
//        moovToOnbording1 = myFragment.findViewById(R.id.Onboarding1_Botton);
//        moovToOnbording2 = myFragment.findViewById(R.id.Onboarding2_Botton);
//        moovToOnbording3 = myFragment.findViewById(R.id.Onboarding3_Botton);
//
//        moovToOnbording1.setOnClickListener(this);
//        moovToOnbording2.setOnClickListener(this);
//        moovToOnbording3.setOnClickListener(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
//            mListener.onFragmentOnboarding3();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentOnboarding3Listener) {
            mListener = (OnFragmentOnboarding3Listener) context;
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
    public void onClick(View myButton) {
        mListener.onFragmentOnboarding3(myButton);

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
    public interface OnFragmentOnboarding3Listener {
        // TODO: Update argument type and name
        void onFragmentOnboarding3(View button);
    }
}
