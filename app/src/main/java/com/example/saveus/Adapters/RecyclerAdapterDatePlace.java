package com.example.saveus.Adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saveus.Activitys.HomeActivity;
import com.example.saveus.Objects.DateListLocations;
import com.example.saveus.Objects.LocationUser;
import com.example.saveus.Objects.myDate;
import com.example.saveus.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RecyclerAdapterDatePlace extends RecyclerView.Adapter<RecyclerAdapterDatePlace.Holder>  {

    private final Context mContext;
    private ArrayList<DateListLocations> mDateListLocations = new ArrayList<>();
    private ArrayList<DateListLocations> originalDateListLocations = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
//    private boolean isFilter = false;

    private RecyclerAdaprerMyPlaces.OnChangeLocationListener mLisener;

    public RecyclerAdapterDatePlace(Context context, ArrayList<DateListLocations> mDateListLocations, RecyclerAdaprerMyPlaces.OnChangeLocationListener onChangeLocationListener) {
        mContext = context;
        this.originalDateListLocations = mDateListLocations;
        this.mDateListLocations.addAll(mDateListLocations);
        this.mLisener = onChangeLocationListener;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_place_rv, parent, false);
        return new RecyclerAdapterDatePlace.Holder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.set(mDateListLocations.get(position));

        if (mDateListLocations.get(position).isOpen()){
            holder.setVisible(true);
        }else {
            holder.setVisible(false);
        }
    }

    @Override
    public int getItemCount() {
        return mDateListLocations.size();
    }

//    public void filterAreeyDate (myDate staret, myDate End) {
//        ArrayList<DateListLocations> filterList = new ArrayList<>();
//        for (int i = 0; i < originalDateListLocations.size(); i++) {
//            if (chackDateIfBetween(staret, End, originalDateListLocations.get(i))) {
//                filterList.add(originalDateListLocations.get(i));
//            }
//        }
//        mDateListLocations.clear();
//        mDateListLocations.addAll(filterList);
//        notifyDataSetChanged();
//    }

//    public boolean chackDateIfBetween (myDate staret, myDate end, DateListLocations dateListLocations){
//        Date dateListLocation = null;
//        Date date1 = null;
//        Date date2 = null;
////        Calendar s = Calendar.getInstance();
////        s.set(Calendar.YEAR,staret.getYear());
////        s.set(Calendar.MONTH,(staret.getMonth()));
////        s.set(Calendar.DAY_OF_MONTH,staret.getYear());
////        Log.d("ggggggggggggggg1", s.get(Calendar.YEAR)+"");
////        Log.d("ggggggggggggggg2", s.get(Calendar.MONTH)+"");
////        Log.d("ggggggggggggggg3", s.get(Calendar.DAY_OF_MONTH)+"");
////        String date = HomeActivity.fixNam(dateListLocations.getDay())+"/"+HomeActivity.fixNam(dateListLocations.getMonth())+"/"+dateListLocations.getYear();
////        try {
////            Date d = new SimpleDateFormat("dd/MM/yyyy").parse(date);
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
////        Calendar dateListLocationsCalendar = Calendar.getInstance();
////        dateListLocationsCalendar.set(dateListLocations.getDay(),dateListLocations.getMonth(),d);
//            // If you already have date objects then skip 1
//
//            //1
//            // Create 2 dates starts
////            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////        try {
////             dateListLocation = sdf.parse(dateListLocations.getYear()+"-"+HomeActivity.fixNam(dateListLocations.getMonth())+"-"+HomeActivity.fixNam(dateListLocations.getDay()));
////             date1 = sdf.parse(staret.getYear()+"-"+HomeActivity.fixNam(staret.getMonth())+"-"+HomeActivity.fixNam(staret.getDay()));
////             date2 = sdf.parse(end.getYear()+"-"+HomeActivity.fixNam(end.getMonth())+"-"+HomeActivity.fixNam(end.getDay()));
////            if (dateListLocation.after(date1) && dateListLocation.before(date2)){
////                return true;
////            }else {
////                return false;
////            }
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
//
//
//
//        if ((dateListLocations.getYear()>= staret.getYear() && dateListLocations.getYear()< end.getYear())|| (dateListLocations.getYear()> staret.getYear() && dateListLocations.getYear()<= end.getYear())
//            || ((dateListLocations.getYear()== staret.getYear() && dateListLocations.getMonth()>= staret.getMonth()) && (dateListLocations.getYear()== end.getYear() && dateListLocations.getMonth()<= end.getMonth()))
//                || ((dateListLocations.getYear()== staret.getYear() && dateListLocations.getMonth() == staret.getMonth() && dateListLocations.getDay() >= staret.getDay())&& (dateListLocations.getYear()== end.getYear() && dateListLocations.getMonth() == end.getMonth() && dateListLocations.getDay() <= end.getDay()))){
//        return true;
//    }else {
//          return false;
//        }
//    }

    public void filterAreeyDateWithCalander(Calendar calendarStart, Calendar calendarEnd) {
        ArrayList<DateListLocations> filterList = new ArrayList<>();
        for (int i = 0; i < originalDateListLocations.size(); i++) {
            if (chackDateIfBetween(calendarStart, calendarEnd, originalDateListLocations.get(i))) {
                filterList.add(originalDateListLocations.get(i));
            }
        }
        mDateListLocations.clear();
        mDateListLocations.addAll(filterList);
        notifyDataSetChanged();

    }



    public boolean chackDateIfBetween (Calendar staret, Calendar end, DateListLocations dateListLocations){
        Calendar calendarDateListLocations = Calendar.getInstance();
        calendarDateListLocations.set(dateListLocations.getYear(),dateListLocations.getMonth()-1,dateListLocations.getDay());
        if (calendarDateListLocations.after(staret)&&calendarDateListLocations.before(end)){
            return true;
        }else {
            return false;
        }
    }


    public class Holder extends RecyclerView.ViewHolder {

        private TextView titelDate;
        private TextView date;
        private ImageView greenErrow;
        private ArrayList<LocationUser> locationUsers;
        private DateListLocations mDateListLocations;
        private RecyclerView myRecyclerView;
        private LinearLayout mLinearLayout;


        public Holder(@NonNull final View itemView) {
            super(itemView);
            titelDate = itemView.findViewById(R.id.item_date_place_titel_data);
            date = itemView.findViewById(R.id.item_date_place_data);
            greenErrow = itemView.findViewById(R.id.item_date_place_green_errow);
            myRecyclerView = itemView.findViewById(R.id.item_date_rv_RV);
            mLinearLayout = itemView.findViewById(R.id.item_date_rv_LL);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mDateListLocations.isOpen()) {
                        setVisible(false);
                        mDateListLocations.setOpen(false);
                        date.setVisibility(View.GONE);
                    } else {
                        myRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                        mAdapter = new RecyclerAdaprerMyPlaces(locationUsers, mLisener);
                        myRecyclerView.setAdapter(mAdapter);
                        setVisible(true);
                        mDateListLocations.setOpen(true);
                        if (checkIfTodey(mDateListLocations)==0 || checkIfTodey(mDateListLocations) == 1){
                            date.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }

        public void set(DateListLocations dateListLocations) {
            mDateListLocations = dateListLocations;
            locationUsers = dateListLocations.getmLocations();
            if (checkIfTodey(mDateListLocations)==0){
                titelDate.setText(mContext.getString(R.string.rv_tudey));
                date.setVisibility(View.VISIBLE);
                date.setText(dateListLocations.getTitelDate());
//                mDateListLocations.setOpen(true);
                initRv();
            }else if (checkIfTodey(mDateListLocations) == 1){
                titelDate.setText(mContext.getString(R.string.rv_yesterday));
                date.setVisibility(View.VISIBLE);
                date.setText(dateListLocations.getTitelDate());
                initRv();
            }
            else {
                titelDate.setText(dateListLocations.getTitelDate());
                date.setVisibility(View.GONE);
            }
        }

        private void initRv() {
            if  (checkIfTodey(mDateListLocations) == 0 || checkIfTodey(mDateListLocations) == 1) {
                myRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                mAdapter = new RecyclerAdaprerMyPlaces(locationUsers, mLisener);
                myRecyclerView.setAdapter(mAdapter);
                setVisible(true);
                mDateListLocations.setOpen(true);
//                date.setVisibility(View.VISIBLE);

            } else {
                setVisible(false);
                mDateListLocations.setOpen(false);
//                date.setVisibility(View.GONE);
            }
        }



        public void setVisible (boolean visible){
            if (visible){
                mLinearLayout.setVisibility(View.VISIBLE);
                greenErrow.setVisibility(View.GONE);

            }else {
                mLinearLayout.setVisibility(View.GONE);
                greenErrow.setVisibility(View.VISIBLE);
            }
        }

        public int checkIfTodey (DateListLocations dateListLocations){
            Calendar oldDeta = Calendar.getInstance();
            oldDeta.set(dateListLocations.getYear(),dateListLocations.getMonth()-1,dateListLocations.getDay());
            Calendar todey = Calendar.getInstance();
            todey.set(Calendar.HOUR_OF_DAY, 00);
            todey.set(Calendar.MINUTE, 00);
            todey.set(Calendar.SECOND, 01);
            Calendar yesterdey = Calendar.getInstance();
            yesterdey.set(Calendar.HOUR_OF_DAY, 00);
            yesterdey.set(Calendar.MINUTE, 00);
            yesterdey.set(Calendar.SECOND, 01);
            yesterdey.set(Calendar.DAY_OF_YEAR,(yesterdey.get(Calendar.DAY_OF_YEAR)-1));

            if
//            (oldDeta.get(Calendar.YEAR) == now.get(Calendar.YEAR) && oldDeta.get(Calendar.MONTH) == now.get(Calendar.MONTH) && oldDeta.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR))
//            (oldDeta.get(Calendar.DATE) == now.get(Calendar.DATE))
                ((!oldDeta.before(todey)))
            {
                return 0;
            }else if
//            (oldDeta.get(Calendar.YEAR) == todey.get(Calendar.YEAR) && oldDeta.get(Calendar.MONTH) == todey.get(Calendar.MONTH) && oldDeta.get(Calendar.DAY_OF_YEAR) == (todey.get(Calendar.DAY_OF_YEAR)-1))
                ((!oldDeta.before(yesterdey)) && (!oldDeta.after(todey)))
            {
                return 1;
            }else {
                return 2;
            }
        }

    }
}
