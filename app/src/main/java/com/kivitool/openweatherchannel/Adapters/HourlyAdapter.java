package com.kivitool.openweatherchannel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kivitool.openweatherchannel.Models.Weekly.HourlyItem;
import com.kivitool.openweatherchannel.Models.Weekly.WeeklyResult;
import com.kivitool.openweatherchannel.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {

    Context context;
    List<HourlyItem> hourlyItemList;

    public HourlyAdapter(Context context, List<HourlyItem> hourlyItemList) {
        this.context = context;
        this.hourlyItemList = hourlyItemList;
    }

    @NonNull
    @Override
    public HourlyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_hourly_forecast_for_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyAdapter.ViewHolder holder, int position) {

        int hour_value = hourlyItemList.get(position).getDt();
        Calendar calendar = Calendar.getInstance();
        Date date = new Date(hour_value * 1000L);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(calendar.getTimeZone().getID()));
        String hour_format = simpleDateFormat.format(date);

        holder.hour.setText(hour_format + "");

        String stiuation_icon = hourlyItemList.get(position).getWeather().get(0).getIcon()  + "";

        /**
         * set weather icon depends on stiuation
         */

        if (stiuation_icon.equals("01d")){
            holder.icon_forHourly.setImageResource(R.drawable.icon_01d);
        }else if (stiuation_icon.equals("01n")){
            holder.icon_forHourly.setImageResource(R.drawable.icon_01n);
        }else if (stiuation_icon.equals("02d")){
            holder.icon_forHourly.setImageResource(R.drawable.icon_02d);
        }else if (stiuation_icon.equals("02n")){
            holder.icon_forHourly.setImageResource(R.drawable.icon_02n);
        }else if (stiuation_icon.equals("03d")){
            holder.icon_forHourly.setImageResource(R.drawable.icon03d);
        }else if (stiuation_icon.equals("03n")){
            holder.icon_forHourly.setImageResource(R.drawable.icon03d);
        }else if (stiuation_icon.equals("04d") || stiuation_icon.equals("04n")){
            holder.icon_forHourly.setImageResource(R.drawable.icon_04n);
        }else if (stiuation_icon.equals("09d") || stiuation_icon.equals("09n")){
            holder.icon_forHourly.setImageResource(R.drawable.icon_9d);
        }else if (stiuation_icon.equals("10d")){
            holder.icon_forHourly.setImageResource(R.drawable.icon_10d);
        }else if (stiuation_icon.equals("10n")){
            holder.icon_forHourly.setImageResource(R.drawable.icon_10n);
        }else if (stiuation_icon.equals("11d")){
            holder.icon_forHourly.setImageResource(R.drawable.icon_11d);
        }else if (stiuation_icon.equals("11n")){
            holder.icon_forHourly.setImageResource(R.drawable.icon_11n);
        }else if (stiuation_icon.equals("13d") || stiuation_icon.equals("13n")){
            holder.icon_forHourly.setImageResource(R.drawable.icon_13n);
        }else if (stiuation_icon.equals("50d") || stiuation_icon.equals("50n")){
            holder.icon_forHourly.setImageResource(R.drawable.icon_50d);
        }

        holder.humidity.setText(hourlyItemList.get(position).getHumidity() + "%");
    }

    @Override
    public int getItemCount() {
        return hourlyItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hour,humidity;
        ImageView icon_forHourly;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hour = itemView.findViewById(R.id.txt_hour_forHourly);
            humidity = itemView.findViewById(R.id.txt_humidity_for_hourly);
            icon_forHourly = itemView.findViewById(R.id.stiuation_image_for_hourly);
        }
    }
}
