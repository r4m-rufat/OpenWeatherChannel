package com.kivitool.openweatherchannel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kivitool.openweatherchannel.Models.DaysForWind.JsonMember5DaysResult;
import com.kivitool.openweatherchannel.Models.DaysForWind.ListItem;
import com.kivitool.openweatherchannel.PreferenceManager;
import com.kivitool.openweatherchannel.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ActivityWindAdapter extends RecyclerView.Adapter<ActivityWindAdapter.ViewHolder> {

    Context context;
    List<ListItem> listItems;
    PreferenceManager preferenceManager;

    public ActivityWindAdapter(Context context, List<ListItem> listItems, PreferenceManager preferenceManager) {
        this.context = context;
        this.listItems = listItems;
        this.preferenceManager = preferenceManager;
    }

    @NonNull
    @Override
    public ActivityWindAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_details_for_activity_wind, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityWindAdapter.ViewHolder holder, int position) {

        int hour_value = listItems.get(position).getDt();
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(hour_value * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(calendar.getTimeZone().getID()));
        String hour_format = simpleDateFormat.format(date);

        holder.date.setText(hour_format + "");

        double windSpeed = listItems.get(position).getWind().getSpeed();
        String formatted_value = String.format("%.1f", listItems.get(position).getWind().getSpeed());
        int formatted_km_h = (int) (listItems.get(position).getWind().getSpeed() * 3.6);
        if (preferenceManager.getBoolean("radio_km_h")){

            holder.wind_speed.setText(formatted_km_h + " km/h");

        }else {

            holder.wind_speed.setText(formatted_value + " m/sec");

        }


        if (windSpeed <= 0.2){
            holder.wind_stiuation.setText("Calm");
        }else if (windSpeed > 0.2 && windSpeed <= 1.5){
            holder.wind_stiuation.setText("Light air");
        }else if (windSpeed > 1.5 && windSpeed <= 3.3){
            holder.wind_stiuation.setText("Light breeze");
        }else if (windSpeed > 3.3 && windSpeed <= 5.4){
            holder.wind_stiuation.setText("Gentle wind");
        }else if (windSpeed > 5.4 && windSpeed <= 7.9){
            holder.wind_stiuation.setText("Moderate wind");
        }else if (windSpeed > 7.9 && windSpeed <= 10.7){
            holder.wind_stiuation.setText("Fresh breeze");
        }else if (windSpeed > 10.7 && windSpeed <= 13.8){
            holder.wind_stiuation.setText("Strong wind");
        }else if (windSpeed > 13.8 && windSpeed <= 17.1){
            holder.wind_stiuation.setText("High wind");
        }else if (windSpeed > 17.1 && windSpeed <= 20.7){
            holder.wind_stiuation.setText("Gale");
        }else if (windSpeed > 20.7 && windSpeed <= 24.4){
            holder.wind_stiuation.setText("Severe gale");
        }else if (windSpeed > 24.4 && windSpeed <= 28.4){
            holder.wind_stiuation.setText("Strong storm");
        }else if (windSpeed > 28.4 && windSpeed <= 32.6){
            holder.wind_stiuation.setText("Violent storm");
        }else if (windSpeed > 32.6){
            holder.wind_stiuation.setText("Hurricane force");
        }

        if (listItems.get(position).getWind().getDeg() > 348 ||
                listItems.get(position).getWind().getDeg() <= 11){

            holder.wind_direction.setText("N");
            holder.acpect_direction.setImageResource(R.drawable.s);

        }else if(listItems.get(position).getWind().getDeg() > 11 &&
                listItems.get(position).getWind().getDeg() <= 78){

            holder.wind_direction.setText("NE");
            holder.acpect_direction.setImageResource(R.drawable.sw);

        }else if(listItems.get(position).getWind().getDeg() > 78 &&
                listItems.get(position).getWind().getDeg() <= 101){

            holder.wind_direction.setText("E");
            holder.acpect_direction.setImageResource(R.drawable.w);

        }else if(listItems.get(position).getWind().getDeg() > 101 &&
                listItems.get(position).getWind().getDeg() <= 168){

            holder.wind_direction.setText("SE");
            holder.acpect_direction.setImageResource(R.drawable.nw);

        }else if(listItems.get(position).getWind().getDeg() > 168 &&
                listItems.get(position).getWind().getDeg() <= 191){

            holder.wind_direction.setText("S");
            holder.acpect_direction.setImageResource(R.drawable.n);

        }else if(listItems.get(position).getWind().getDeg() > 191 &&
                listItems.get(position).getWind().getDeg() <= 258){

            holder.wind_direction.setText("SW");
            holder.acpect_direction.setImageResource(R.drawable.ne);

        }else if(listItems.get(position).getWind().getDeg() > 258 &&
                listItems.get(position).getWind().getDeg() <= 281){

            holder.wind_direction.setText("W");
            holder.acpect_direction.setImageResource(R.drawable.e);

        }else if(listItems.get(position).getWind().getDeg() > 281 &&
                listItems.get(position).getWind().getDeg() <= 348){

            holder.wind_direction.setText("NW");
            holder.acpect_direction.setImageResource(R.drawable.se);

        }


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, wind_stiuation, wind_speed, wind_direction;
        ImageView acpect_direction;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.txt_date_for_3hourly);
            wind_stiuation = itemView.findViewById(R.id.txt_wind_stiuation);
            wind_speed = itemView.findViewById(R.id.txt_wind_value);
            wind_direction = itemView.findViewById(R.id.txt_wind_direction);
            acpect_direction = itemView.findViewById(R.id.image_wind_direction);

        }
    }
}
