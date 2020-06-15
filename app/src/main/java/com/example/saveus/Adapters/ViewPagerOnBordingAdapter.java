package com.example.saveus.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


import java.util.List;


public class ViewPagerOnBordingAdapter extends FragmentStatePagerAdapter {


    private List<Fragment> myOnbordingFragments;
    private Context context;

    public ViewPagerOnBordingAdapter(FragmentManager fm, List <Fragment> myOnbordingFragments,Context context) {
        super(fm);
        this.myOnbordingFragments = myOnbordingFragments;
        this.context = context;

    }


    @Override
    public Fragment getItem(int position) {
        return myOnbordingFragments.get(position);
    }

    @Override
    public int getCount() {
        return myOnbordingFragments.size();
    }

}
