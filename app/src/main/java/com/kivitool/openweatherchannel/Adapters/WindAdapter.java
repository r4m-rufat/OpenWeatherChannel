package com.kivitool.openweatherchannel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kivitool.openweatherchannel.Models.DaysForWind.JsonMember5DaysResult;
import com.kivitool.openweatherchannel.Models.DaysForWind.ListItem;
import com.kivitool.openweatherchannel.PreferenceManager;
import com.kivitool.openweatherchannel.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class WindAdapter extends RecyclerView.Adapter<WindAdapter.ViewHolder> {

    Context context;
    List<ListItem> listItems;
    PreferenceManager preferenceManager;

    public WindAdapter(Context context, List<ListItem> listItems, PreferenceManager preferenceManager) {
        this.context = context;
        this.listItems = listItems;
        this.preferenceManager = preferenceManager;
    }

    @NonNull
    @Override
    public WindAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_hourly_wind_forecast_for_recycler_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WindAdapter.ViewHolder holder, int position) {

        int wind_speed_m = (int) listItems.get(position).getWind().getSpeed();
        String formatted_m = wind_speed_m + "";
        int wind_speed_km = (int) (wind_speed_m * 3.6);
        String formatted_km = wind_speed_km + "";

        if (preferenceManager.getBoolean("radio_km_h")){

            holder.wind_speed.setText(formatted_km + " km/h");

        }else {

            holder.wind_speed.setText(formatted_m + " m/sec");

        }


        int hour_value = listItems.get(position).getDt();
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(hour_value * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(calendar.getTimeZone().getID()));
        String hour_format = simpleDateFormat.format(date);

        holder.hour.setText(hour_format + "");

        if (listItems.get(position).getWind().getDeg() > 348.75 ||
                listItems.get(position).getWind().getDeg() <= 11.25){

            holder.wind_direction.setImageResource(R.drawable.s);

        }else if(listItems.get(position).getWind().getDeg() > 11.25 &&
                listItems.get(position).getWind().getDeg() <= 78.75){

            holder.wind_direction.setImageResource(R.drawable.sw);

        }else if(listItems.get(position).getWind().getDeg() > 78.75 &&
                listItems.get(position).getWind().getDeg() <= 101.25){

            holder.wind_direction.setImageResource(R.drawable.w);

        }else if(listItems.get(position).getWind().getDeg() > 101.25 &&
                listItems.get(position).getWind().getDeg() <= 168.75){

            holder.wind_direction.setImageResource(R.drawable.nw);

        }else if(listItems.get(position).getWind().getDeg() > 168.75 &&
                listItems.get(position).getWind().getDeg() <= 191.25){

            holder.wind_direction.setImageResource(R.drawable.n);

        }else if(listItems.get(position).getWind().getDeg() > 191.25 &&
                listItems.get(position).getWind().getDeg() <= 258.75){

            holder.wind_direction.setImageResource(R.drawable.ne);

        }else if(listItems.get(position).getWind().getDeg() > 258.75 &&
                listItems.get(position).getWind().getDeg() <= 281.25){

            holder.wind_direction.setImageResource(R.drawable.e);

        }else if(listItems.get(position).getWind().getDeg() > 281.25 &&
                listItems.get(position).getWind().getDeg() <= 348.75){

            holder.wind_direction.setImageResource(R.drawable.se);

        }

    }

    @Override
    public int getItemCount() {
        return 16;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView wind_speed, hour;
        ImageView wind_direction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wind_speed = itemView.findViewById(R.id.txt_wind_speed_value);
            wind_direction = itemView.findViewById(R.id.image_wind_direction);
            hour = itemView.findViewById(R.id.txt_hour_forWind);
        }
    }
}
