package com.kivitool.openweatherchannel.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kivitool.openweatherchannel.Models.Weekly.DailyItem;
import com.kivitool.openweatherchannel.Models.Weekly.WeeklyResult;
import com.kivitool.openweatherchannel.PreferenceManager;
import com.kivitool.openweatherchannel.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ActivityDailyAdapter extends RecyclerView.Adapter<ActivityDailyAdapter.ViewHolder> {


    Context context;
    List<DailyItem> dailyItemList;
    PreferenceManager preferenceManager;

    public ActivityDailyAdapter(Context context, List<DailyItem> dailyItemList, PreferenceManager preferenceManager) {
        this.context = context;
        this.dailyItemList = dailyItemList;
        this.preferenceManager = preferenceManager;
    }

    @NonNull
    @Override
    public ActivityDailyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_details_for_activity_daily, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityDailyAdapter.ViewHolder holder, int position) {

        int weekNameValue = dailyItemList.get(position).getDt();

        Calendar calendar = Calendar.getInstance();
        Date convertDate = new Date(weekNameValue * 1000L);
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("EEE");
        dateFormat1.setTimeZone(TimeZone.getTimeZone(calendar.getTimeZone().getID()));
        dateFormat2.setTimeZone(TimeZone.getTimeZone(calendar.getTimeZone().getID()));
        String formattedDate1 = dateFormat1.format(convertDate);
        String formattedDate2 = dateFormat2.format(convertDate);


        if (formattedDate2.equals("Sat") || formattedDate2.equals("Sun") || formattedDate2.equals("B.") || formattedDate2.equals("Ş.")) {
            holder.daily_date.setBackgroundColor(ContextCompat.getColor(context, R.color.red));
            holder.daily_date.setTypeface(Typeface.DEFAULT_BOLD);
        }
        holder.daily_date.setText(formattedDate2 + " " + formattedDate1);

        holder.humidity.setText("Humidity: " + dailyItemList.get(position).getHumidity() + "%");

        String stiuation_icon = dailyItemList.get(position).getWeather().get(0).getIcon()  + "";

        /**
         * set weather icon depends on stiuation
         */

        if (stiuation_icon.equals("01d")){
            holder.weather_image.setImageResource(R.drawable.icon_01d);
        }else if (stiuation_icon.equals("01n")){
            holder.weather_image.setImageResource(R.drawable.icon_01n);
        }else if (stiuation_icon.equals("02d")){
            holder.weather_image.setImageResource(R.drawable.icon_02d);
        }else if (stiuation_icon.equals("02n")){
            holder.weather_image.setImageResource(R.drawable.icon_02n);
        }else if (stiuation_icon.equals("03d")){
            holder.weather_image.setImageResource(R.drawable.icon03d);
        }else if (stiuation_icon.equals("03n")){
            holder.weather_image.setImageResource(R.drawable.icon03d);
        }else if (stiuation_icon.equals("04d") || stiuation_icon.equals("04n")){
            holder.weather_image.setImageResource(R.drawable.icon_04n);
        }else if (stiuation_icon.equals("09d") || stiuation_icon.equals("09n")){
            holder.weather_image.setImageResource(R.drawable.icon_9d);
        }else if (stiuation_icon.equals("10d")){
            holder.weather_image.setImageResource(R.drawable.icon_10d);
        }else if (stiuation_icon.equals("10n")){
            holder.weather_image.setImageResource(R.drawable.icon_10n);
        }else if (stiuation_icon.equals("11d")){
            holder.weather_image.setImageResource(R.drawable.icon_11d);
        }else if (stiuation_icon.equals("11n")){
            holder.weather_image.setImageResource(R.drawable.icon_11n);
        }else if (stiuation_icon.equals("13d") || stiuation_icon.equals("13n")){
            holder.weather_image.setImageResource(R.drawable.icon_13n);
        }else if (stiuation_icon.equals("50d") || stiuation_icon.equals("50n")){
            holder.weather_image.setImageResource(R.drawable.icon_50d);
        }

        holder.weather_stiuation.setText(dailyItemList.get(position).getWeather().get(0).getMain() + "");
        holder.pressure.setText("Pressure: " + dailyItemList.get(position).getPressure() + " pHa");

        int int_sunrise = dailyItemList.get(position).getSunrise();
        Date date1 = new Date(int_sunrise * 1000L);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
        simpleDateFormat1.setTimeZone(TimeZone.getTimeZone(calendar.getTimeZone().getID()));
        String formated_date1 = simpleDateFormat1.format(date1);
        holder.sunrise.setText("Sunrise: " + formated_date1 + "");

        int int_sunset = dailyItemList.get(position).getSunset();
        Date date2 = new Date(int_sunset * 1000L);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
        simpleDateFormat2.setTimeZone(TimeZone.getTimeZone(calendar.getTimeZone().getID()));
        String formated_date2 = simpleDateFormat2.format(date2);
        holder.sunset.setText("Sunset: " + formated_date2 + "");

        int int_morning = (int) dailyItemList.get(position).getTemp().getMorn();
        int int_evening = (int) dailyItemList.get(position).getTemp().getEve();
        int int_night = (int) dailyItemList.get(position).getTemp().getNight();
        int int_max = (int) dailyItemList.get(position).getTemp().getMax();
        int int_min = (int) dailyItemList.get(position).getTemp().getMin();

        /**
         * change celcius to fahrenheit  ----- °F = (°C × 9/5) + 32
         */

        int farenheit_morning = (int_morning * 9 / 5) + 32;
        int farenheit_evening = (int_evening * 9 / 5) + 32;
        int farenheit_night = (int_night * 9 / 5) + 32;
        int farenheit_max = (int_max * 9 / 5) + 32;
        int farenheit_min = (int_min * 9 / 5) + 32;


        if (preferenceManager.getBoolean("radio_fahrenheit")){

            holder.morning_temprature.setText("Morning: " + farenheit_morning + "°F");
            holder.evening_temprature.setText("Evening: " + farenheit_evening + "°F");
            holder.night_temprature.setText("Night: " + farenheit_night + "°F");
            holder.max_temprature.setText("↑" + farenheit_max + "°F");
            holder.min_temprature.setText("↓" + farenheit_min + "°F");

        }else {

            holder.morning_temprature.setText("Morning: " + int_morning + "°C");
            holder.evening_temprature.setText("Evening: " + int_evening + "°C");
            holder.night_temprature.setText("Night: " + int_night + "°C");
            holder.max_temprature.setText("↑" + int_max + "°C");
            holder.min_temprature.setText("↓" + int_min + "°C");

        }


        int int_uv_index = (int) dailyItemList.get(position).getUvi();
        holder.uv_index.setText("UV index: " + int_uv_index + "");


    }

    @Override
    public int getItemCount() {
        return dailyItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView daily_date, weather_stiuation,
        humidity, pressure, sunrise,sunset, morning_temprature,
        evening_temprature, night_temprature, uv_index, max_temprature, min_temprature;

        ImageView weather_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            daily_date = itemView.findViewById(R.id.daily_date);
            weather_stiuation = itemView.findViewById(R.id.txt_weather_stiuation);
            humidity = itemView.findViewById(R.id.txt_humidity_for_daily);
            pressure = itemView.findViewById(R.id.txt_pressure);
            sunrise = itemView.findViewById(R.id.txt_sunrise);
            sunset = itemView.findViewById(R.id.txt_sunset);
            weather_image = itemView.findViewById(R.id.stiuation_image_for_daily);
            morning_temprature = itemView.findViewById(R.id.morning_temprature);
            evening_temprature = itemView.findViewById(R.id.evenining_temprature);
            night_temprature = itemView.findViewById(R.id.night_tempratue);
            uv_index = itemView.findViewById(R.id.txt_uv_index);
            max_temprature = itemView.findViewById(R.id.max_temprature);
            min_temprature = itemView.findViewById(R.id.min_temprature);

        }
    }
}
