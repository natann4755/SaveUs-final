package com.example.saveus.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.saveus.Adapters.ViewPagerOnBordingAdapter;
import com.example.saveus.Fragments.Onboarding1Fragment;
import com.example.saveus.Fragments.Onboarding2Fragment;
import com.example.saveus.Fragments.Onboarding3Fragment;
import com.example.saveus.R;
import com.example.saveus.Adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ActivityOnBoarding extends AppCompatActivity implements Onboarding1Fragment.OnFragmentOnboarding1Listener , Onboarding2Fragment.OnFragmentOnboarding2Listener ,
        Onboarding3Fragment.OnFragmentOnboarding3Listener
//        ,View.OnTouchListener
{

    private ViewPager mOnbordingViewPager;
    private ViewPagerOnBordingAdapter myViewPagerAdapter;
    private List<Fragment> mOnbordingFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        initVP();
    }

    private void initVP() {
        listOnbordingFragments();
        mOnbordingViewPager = findViewById(R.id.Onbording_ViewPager_VP);
        myViewPagerAdapter = new ViewPagerOnBordingAdapter(getSupportFragmentManager(), mOnbordingFragments, getApplicationContext());
        mOnbordingViewPager.setAdapter(myViewPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.on_boarding_TL);
        tabLayout.setupWithViewPager(mOnbordingViewPager);
//        mOnbordingViewPager.setOnTouchListener(this);
        mOnbordingViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            private boolean isLastPageSwiped;
//            private int counterPageScroll;

            @Override
            public void onPageScrolled(int position, float positionOffset, int i1) {
                if (position == 2) {
                    if (isLastPageSwiped) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                    isLastPageSwiped = true;
                } else {
                    isLastPageSwiped = false;
                }

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }




    private void listOnbordingFragments() {
        mOnbordingFragments = new ArrayList<>();


        mOnbordingFragments.add(Onboarding3Fragment.newInstance());
        mOnbordingFragments.add(Onboarding2Fragment.newInstance());
        mOnbordingFragments.add(Onboarding1Fragment.newInstance());
    }

    @Override
    public void onFragmentOnboarding1(View button) {
//        moovToOnboarding(button.getId());
    }

    @Override
    public void onFragmentOnboarding2(View button) {
//        moovToOnboarding(button.getId());
    }

    @Override
    public void onFragmentOnboarding3(View button) {
//        moovToOnboarding(button.getId());
    }

//    private void moovToOnboarding(int id) {
//        switch (id) {
//            case R.id.Onboarding1_Botton:
//                mOnbordingViewPager.setCurrentItem(2);
//                break;
//            case R.id.Onboarding2_Botton:
//                mOnbordingViewPager.setCurrentItem(1);
//                break;
//            case R.id.Onboarding3_Botton:
//                mOnbordingViewPager.setCurrentItem(0);
//                break;
//        }
//    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        int downX = 0, upX;
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            downX = (int) event.getX();
//                return true;
//                }
//                else{
//            if(myViewPagerAdapter.getCount() - 1 == mOnbordingViewPager.getCurrentItem()){
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    upX = (int) event.getX();
//
//                    if (downX - upX > - 90) {
//
//
//                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
//                    }else {
//                        return false;
////                        mOnbordingViewPager.setCurrentItem(1);
//                    }
//                }
//                return false;
//
//            }
//            return false;
//        }
//    }

}

