package com.kivitool.openweatherchannel.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kivitool.openweatherchannel.Models.Weekly.HourlyItem;
import com.kivitool.openweatherchannel.Models.Weekly.WeeklyResult;
import com.kivitool.openweatherchannel.PreferenceManager;
import com.kivitool.openweatherchannel.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ActivityHourlyAdapter extends RecyclerView.Adapter<ActivityHourlyAdapter.ViewHolder> {

    Context context;
    List<HourlyItem> hourlyItemList;
    PreferenceManager preferenceManager;

    public ActivityHourlyAdapter(Context context, List<HourlyItem> hourlyItemList, PreferenceManager preferenceManager) {
        this.context = context;
        this.hourlyItemList = hourlyItemList;
        this.preferenceManager = preferenceManager;
    }

    @NonNull
    @Override
    public ActivityHourlyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_details_for_activity_hourly, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityHourlyAdapter.ViewHolder holder, int position) {

        int hour_value = hourlyItemList.get(position).getDt();

        Calendar calendar = Calendar.getInstance();
        Date convertDate = new Date(hour_value * 1000L);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("EEE dd/MM HH:mm");
        dateFormat1.setTimeZone(TimeZone.getTimeZone(calendar.getTimeZone().getID()));
        String formattedDate1 = dateFormat1.format(convertDate);

        holder.date.setText(formattedDate1 + "");

        String description = toUpperCase(hourlyItemList.get(position).getWeather().get(0).getDescription());
        holder.weather_stiuation.setText(description + "");

        holder.humidity.setText("Humidity: " + hourlyItemList.get(position).getHumidity() + "%");

        holder.pressure.setText("Pressure: " + hourlyItemList.get(position).getPressure() + " hPa");

        int casted_int = (int) hourlyItemList.get(position).getTemp();

        /**
         * change celcius to fahrenheit  ----- °F = (°C × 9/5) + 32
         */

        int farenheit_temprature = (casted_int * 9 / 5) + 32;

        if (preferenceManager.getBoolean("radio_fahrenheit")){

            holder.weather_value.setText(farenheit_temprature + "°F");

        }else if (preferenceManager.getBoolean("radio_celcius")){

            holder.weather_value.setText(casted_int + "°C");


        }

        String stiuation_icon = hourlyItemList.get(position).getWeather().get(0).getIcon()  + "";

        /**
         * set weather icon depends on stiuation
         */

        if (stiuation_icon.equals("01d")){
            holder.weather_icon.setImageResource(R.drawable.icon_01d);
        }else if (stiuation_icon.equals("01n")){
            holder.weather_icon.setImageResource(R.drawable.icon_01n);
        }else if (stiuation_icon.equals("02d")){
            holder.weather_icon.setImageResource(R.drawable.icon_02d);
        }else if (stiuation_icon.equals("02n")){
            holder.weather_icon.setImageResource(R.drawable.icon_02n);
        }else if (stiuation_icon.equals("03d")){
            holder.weather_icon.setImageResource(R.drawable.icon03d);
        }else if (stiuation_icon.equals("03n")){
            holder.weather_icon.setImageResource(R.drawable.icon03d);
        }else if (stiuation_icon.equals("04d") || stiuation_icon.equals("04n")){
            holder.weather_icon.setImageResource(R.drawable.icon_04n);
        }else if (stiuation_icon.equals("09d") || stiuation_icon.equals("09n")){
            holder.weather_icon.setImageResource(R.drawable.icon_9d);
        }else if (stiuation_icon.equals("10d")){
            holder.weather_icon.setImageResource(R.drawable.icon_10d);
        }else if (stiuation_icon.equals("10n")){
            holder.weather_icon.setImageResource(R.drawable.icon_10n);
        }else if (stiuation_icon.equals("11d")){
            holder.weather_icon.setImageResource(R.drawable.icon_11d);
        }else if (stiuation_icon.equals("11n")){
            holder.weather_icon.setImageResource(R.drawable.icon_11n);
        }else if (stiuation_icon.equals("13d") || stiuation_icon.equals("13n")){
            holder.weather_icon.setImageResource(R.drawable.icon_13n);
        }else if (stiuation_icon.equals("50d") || stiuation_icon.equals("50n")){
            holder.weather_icon.setImageResource(R.drawable.icon_50d);
        }

    }

    @Override
    public int getItemCount() {
        return hourlyItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date, weather_stiuation,
        humidity, pressure, weather_value;
        ImageView weather_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.txt_date_for_hourly);
            weather_stiuation = itemView.findViewById(R.id.txt_weather_stiuation);
            humidity = itemView.findViewById(R.id.txt_weather_humidity);
            pressure = itemView.findViewById(R.id.txt_pressure);
            weather_value = itemView.findViewById(R.id.txt_weather_value);
            weather_icon = itemView.findViewById(R.id.image_for_hourly);
        }
    }

    private String toUpperCase(String city_name){

        String capital = city_name.substring(0, 1).toUpperCase() + city_name.substring(1);

        return capital;

    }

}
