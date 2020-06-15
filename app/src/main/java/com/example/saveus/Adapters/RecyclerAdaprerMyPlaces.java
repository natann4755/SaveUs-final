package com.example.saveus.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saveus.Activitys.HomeActivity;
import com.example.saveus.Objects.LocationUser;
import com.example.saveus.R;
import java.util.ArrayList;

public class RecyclerAdaprerMyPlaces extends RecyclerView.Adapter<RecyclerAdaprerMyPlaces.Holder> {

    private ArrayList <LocationUser> mLocationUsers;
    private OnChangeLocationListener mLisener;


    public RecyclerAdaprerMyPlaces(ArrayList<LocationUser> mLocationUsers,OnChangeLocationListener onChangeLocationListener) {
        this.mLocationUsers = mLocationUsers;
        this.mLisener = onChangeLocationListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_place_rv, parent,false);
        return new RecyclerAdaprerMyPlaces.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.set(mLocationUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mLocationUsers.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView adrees;
        private TextView startTime;
        private TextView endTime;
        private TextView time;

        public Holder(@NonNull View itemView) {
            super(itemView);
            adrees = itemView.findViewById(R.id.item_RV_adrees_TV);
            startTime = itemView.findViewById(R.id.item_RV_startTime_TV);
            endTime = itemView.findViewById(R.id.item_RV_endTime_TV);
            time = itemView.findViewById(R.id.item_RV_time_TV);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLisener.ChangeLocationPressed(mLocationUsers.get(getAdapterPosition()));
                }
            });
        }

        public void set(LocationUser locationUser) {
            adrees.setText(locationUser.getAdsress());
            startTime.setText(HomeActivity.fixNam(locationUser.getStartHour()) + ":" + HomeActivity.fixNam(locationUser.getStartMinit()));
            endTime.setText(locationUser.getEndTime());
            time.setText(locationUser.getTime());
        }
    }

    public interface OnChangeLocationListener {
        // TODO: Update argument type and name
        void ChangeLocationPressed(LocationUser locationUser);

    }
}
