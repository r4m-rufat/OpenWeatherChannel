package com.kivitool.openweatherchannel.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.kivitool.openweatherchannel.Home.MainActivity;
import com.kivitool.openweatherchannel.PreferenceManager;
import com.kivitool.openweatherchannel.R;

public class NotificationService extends Service {

    // display notification varaibles
    public static final String ID = "Notification";
    public static final int NOTIFICATION_ID = 101;
    PreferenceManager preferenceManager;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        preferenceManager = new PreferenceManager(this);

        createNotificationChannel();

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        remoteViews.setTextViewText(R.id.current_temprature, preferenceManager.getInteger("current_temprature_notification") + "°C");
        remoteViews.setTextViewText(R.id.daily_max_min_temprature, preferenceManager.getString("first_day_max_temp_for_notification") + "°/" + preferenceManager.getString("first_day_min_temp_for_notification") + "°");
        remoteViews.setTextViewText(R.id.city_name, preferenceManager.getString("city_name_for_notification"));

        String string = preferenceManager.getString("current_description_notification");
        String capital = string.substring(0, 1).toUpperCase() + string.substring(1);

        remoteViews.setTextViewText(R.id.current_description, capital);

        if (preferenceManager.getString("current_icon_notification").equals("01d")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon_01d);
        } else if (preferenceManager.getString("current_icon_notification").equals("01n")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon_01n);
        } else if (preferenceManager.getString("current_icon_notification").equals("02d")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon_02d);
        } else if (preferenceManager.getString("current_icon_notification").equals("02n")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon_02n);
        } else if (preferenceManager.getString("current_icon_notification").equals("03d")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon03d);
        } else if (preferenceManager.getString("current_icon_notification").equals("03n")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon03d);
        } else if (preferenceManager.getString("current_icon_notification").equals("04d") || preferenceManager.getString("current_icon_notification").equals("04n")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon_04n);
        } else if (preferenceManager.getString("current_icon_notification").equals("09d") || preferenceManager.getString("current_icon_notification").equals("09n")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon_9d);
        } else if (preferenceManager.getString("current_icon_notification").equals("10d")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon_10d);
        } else if (preferenceManager.getString("current_icon_notification").equals("10n")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon_10n);
        } else if (preferenceManager.getString("current_icon_notification").equals("11d")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon_11d);
        } else if (preferenceManager.getString("current_icon_notification").equals("11n")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon_11n);
        } else if (preferenceManager.getString("current_icon_notification").equals("13d") || preferenceManager.getString("current_icon_notification").equals("13n")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon_13n);
        } else if (preferenceManager.getString("current_icon_notification").equals("50d") || preferenceManager.getString("current_icon_notification").equals("50n")) {
            remoteViews.setImageViewResource(R.id.icon_weather,R.drawable.icon_50d);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ID);

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setSmallIcon(R.drawable.weatherlogo);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);
        builder.setCustomContentView(remoteViews);
        builder.setContentIntent(pendingIntent);


        Notification notification = builder.build();

        startForeground(1, notification);


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void createNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notification Channel";
            String description = "Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ID, name, importance);
            channel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

    }

}
