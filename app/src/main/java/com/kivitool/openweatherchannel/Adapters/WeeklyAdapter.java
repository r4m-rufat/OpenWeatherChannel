package com.kivitool.openweatherchannel.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kivitool.openweatherchannel.Home.MainActivity;
import com.kivitool.openweatherchannel.Models.Weekly.DailyItem;
import com.kivitool.openweatherchannel.Models.Weekly.WeeklyResult;
import com.kivitool.openweatherchannel.R;

import java.net.ContentHandler;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static java.security.AccessController.getContext;

public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.ViewHolder> {

    Context context;
    List<DailyItem> dailyItemList;

    public WeeklyAdapter(Context context, List<DailyItem> dailyItemList) {
        this.context = context;
        this.dailyItemList = dailyItemList;
    }

    @NonNull
    @Override
    public WeeklyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_daily_forecast_for_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyAdapter.ViewHolder holder, int position) {

        int weekNameValue =dailyItemList.get(position).getDt();

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
        holder.week_name.setText(formattedDate1 + "");
        holder.daily_date.setText("   " + formattedDate2 + "   ");

        holder.humidity.setText(dailyItemList.get(position).getHumidity() + "%");



        String stiuation_icon = dailyItemList.get(position).getWeather().get(0).getIcon()  + "";

        /**
         * set weather icon depends on stiuation
         */

        if (stiuation_icon.equals("01d")){
            holder.weekly_icon.setImageResource(R.drawable.icon_01d);
        }else if (stiuation_icon.equals("01n")){
            holder.weekly_icon.setImageResource(R.drawable.icon_01n);
        }else if (stiuation_icon.equals("02d")){
            holder.weekly_icon.setImageResource(R.drawable.icon_02d);
        }else if (stiuation_icon.equals("02n")){
            holder.weekly_icon.setImageResource(R.drawable.icon_02n);
        }else if (stiuation_icon.equals("03d")){
            holder.weekly_icon.setImageResource(R.drawable.icon03d);
        }else if (stiuation_icon.equals("03n")){
            holder.weekly_icon.setImageResource(R.drawable.icon03d);
        }else if (stiuation_icon.equals("04d") || stiuation_icon.equals("04n")){
            holder.weekly_icon.setImageResource(R.drawable.icon_04n);
        }else if (stiuation_icon.equals("09d") || stiuation_icon.equals("09n")){
            holder.weekly_icon.setImageResource(R.drawable.icon_9d);
        }else if (stiuation_icon.equals("10d")){
            holder.weekly_icon.setImageResource(R.drawable.icon_10d);
        }else if (stiuation_icon.equals("10n")){
            holder.weekly_icon.setImageResource(R.drawable.icon_10n);
        }else if (stiuation_icon.equals("11d")){
            holder.weekly_icon.setImageResource(R.drawable.icon_11d);
        }else if (stiuation_icon.equals("11n")){
            holder.weekly_icon.setImageResource(R.drawable.icon_11n);
        }else if (stiuation_icon.equals("13d") || stiuation_icon.equals("13n")){
            holder.weekly_icon.setImageResource(R.drawable.icon_13n);
        }else if (stiuation_icon.equals("50d") || stiuation_icon.equals("50n")){
            holder.weekly_icon.setImageResource(R.drawable.icon_50d);
        }

    }

    @Override
    public int getItemCount() {
        return dailyItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView week_name, daily_date, humidity;
        ImageView weekly_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            week_name = itemView.findViewById(R.id.txt_week_forDaily);
            daily_date = itemView.findViewById(R.id.txt_monthly_date_time);
            humidity = itemView.findViewById(R.id.txt_humidity_for_daily);
            weekly_icon = itemView.findViewById(R.id.stiuation_image_for_daily);
        }
    }
}
