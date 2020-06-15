package com.example.saveus.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.saveus.R;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List <Fragment> myOnbordingFragments;
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, List <Fragment> myOnbordingFragments,Context context) {
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

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = context.getString(R.string.rv_titel_on_map);
        }
        else if (position == 1)
        {
            title = context.getString(R.string.rv_titel_my_place);
        }
        return title;
    }
}
