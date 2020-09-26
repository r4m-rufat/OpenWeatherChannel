package com.kivitool.openweatherchannel.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.github.tianma8023.formatter.SunriseSunsetLabelFormatter;
import com.github.tianma8023.model.Time;
import com.github.tianma8023.ssv.SunriseSunsetView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kivitool.openweatherchannel.API.ManagerAll;
import com.kivitool.openweatherchannel.Adapters.HourlyAdapter;
import com.kivitool.openweatherchannel.Adapters.WeeklyAdapter;
import com.kivitool.openweatherchannel.Adapters.WindAdapter;
import com.kivitool.openweatherchannel.Daily.Daily;
import com.kivitool.openweatherchannel.Database.SpinnerDatabase;
import com.kivitool.openweatherchannel.DrawerLayoutItems.AboutTheApplication;
import com.kivitool.openweatherchannel.DrawerLayoutItems.AddNewLocation;
import com.kivitool.openweatherchannel.DrawerLayoutItems.ContactUs;
import com.kivitool.openweatherchannel.DrawerLayoutItems.MyLocation;
import com.kivitool.openweatherchannel.DrawerLayoutItems.Settings;
import com.kivitool.openweatherchannel.Hourly.Hourly;
import com.kivitool.openweatherchannel.MenuItem.Help;
import com.kivitool.openweatherchannel.Models.Current.CurrentResult;
import com.kivitool.openweatherchannel.Models.DaysForWind.JsonMember5DaysResult;
import com.kivitool.openweatherchannel.Models.DaysForWind.ListItem;
import com.kivitool.openweatherchannel.Models.Weekly.DailyItem;
import com.kivitool.openweatherchannel.Models.Weekly.HourlyItem;
import com.kivitool.openweatherchannel.Models.Weekly.WeeklyResult;
import com.kivitool.openweatherchannel.PreferenceManager;
import com.kivitool.openweatherchannel.R;
import com.kivitool.openweatherchannel.Services.NotificationService;
import com.kivitool.openweatherchannel.Wind.Wind;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.kivitool.openweatherchannel.DrawerLayoutItems.MyLocation.REQUEST_CODE;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "MainActivity";
    private InterstitialAd interstitialAd;
    private Context context;
    private TextView clock, temprature, weather_stiuation, wind_speed1,
            feels_like, pressure, sunrise, sunset, wind_speed2, txt_km_h, txt_m_sec,
            sunriseView, sunsetView, durationOFDay;
    private ImageView weather_stiuation_image, ic_share;
    private SunriseSunsetView sunriseSunsetView;
    private CurrentResult currentResult, current_notificationResult;
    private WeeklyResult weeklyResult, weekly_notificationResult;
    private JsonMember5DaysResult jsonMember5DaysResult;
    private RecyclerView dailyRecyclerView, hourlyRecyclerView, windRecyclerView;
    String lat, lon, formated_date1, formated_date2;
    private PreferenceManager preferenceManager;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private List<HourlyItem> hourlyItemList;
    private List<DailyItem> dailyItemList;
    private List<ListItem> listItems;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;


    // Database
    SpinnerDatabase spinnerDatabase;

    // variable
    private static final String api_key = "92dd1b88143e5f718881adb35cc2c5ce";
    private static String units = "metric";
    String direction;

    private static String spinnerItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        //TODO THIS IS ## interstitialAd ADVERTISING $$ IS SET aCTIVITY
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId("ca-app-pub-7456215763987145/2989668452");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        preferenceManager = new PreferenceManager(context);

        spinnerDatabase = new SpinnerDatabase(context);

        setupWidgets();
        setupAdapters();
        setupShare();

        if (preferenceManager.getString("current_pressure") != null) {

            reloadPageWithFast();

        }

        // current location

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        spinnerColor();
        setupBottomNavigationViewEx();
        showNotification();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();


    }


    private void spinnerColor() {

        final ArrayList<String> city_names = new ArrayList<>();
        Cursor cursor = spinnerDatabase.getData();

        while (cursor.moveToNext()) {

            String name = cursor.getString(1);

            city_names.add(name);

        }

        try {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.layout_for_spinner_color, city_names);

            adapter.setDropDownViewResource(R.layout.layout_dropdown_spinner);
            final Spinner spinner = findViewById(R.id.toolbar_spinner);
            spinner.setAdapter(adapter);

            try {

                spinner.setSelection(preferenceManager.getInteger("spinner_position"));

            } catch (IndexOutOfBoundsException e) {

            }

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selected_city = spinner.getSelectedItem().toString();

                    spinnerItem = selected_city;
                    int spinnerPosition = spinner.getSelectedItemPosition();
                    preferenceManager.putInteger("spinner_position", spinnerPosition);

                    if (String.valueOf(preferenceManager.getInteger("spinner_position")) == null) {

                        Response(city_names.get(0));

                    } else if (String.valueOf(preferenceManager.getInteger("spinner_position")) != null) {

                        int getSpinnerPosition = preferenceManager.getInteger("spinner_position");
                        spinnerItem = city_names.get(getSpinnerPosition);

                        Response(spinnerItem);

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });


        } catch (NullPointerException e) {

        }
    }


    private void spinnerSetAdapter() {

        final ArrayList<String> city_names = new ArrayList<>();
        Cursor cursor = spinnerDatabase.getData();

        while (cursor.moveToNext()) {

            String name = cursor.getString(1);
            city_names.add(name);

        }

        try {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.layout_for_spinner_color, city_names);

            adapter.setDropDownViewResource(R.layout.layout_dropdown_spinner);
            final Spinner spinner = findViewById(R.id.toolbar_spinner);
            spinner.setAdapter(adapter);

            try {

                spinner.setSelection(preferenceManager.getInteger("spinner_position") - 1);

            } catch (IndexOutOfBoundsException e) {

            }

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selected_city = spinner.getSelectedItem().toString();

                    spinnerItem = selected_city;
                    int spinnerPosition = spinner.getSelectedItemPosition();
                    preferenceManager.putInteger("spinner_position", spinnerPosition);

                    if (String.valueOf(preferenceManager.getInteger("spinner_position")) == null) {

                        Response(city_names.get(0));

                    } else if (String.valueOf(preferenceManager.getInteger("spinner_position")) != null) {

                        int getSpinnerPosition = preferenceManager.getInteger("spinner_position");
                        spinnerItem = city_names.get(getSpinnerPosition);

                        Response(spinnerItem);

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }
            });


        } catch (NullPointerException e) {

        }
    }

    private void deleteLocationFromDatabase(String spinner_position) {

        spinnerDatabase.removeLocationDataListItem(spinner_position);

    }

    private void setupWidgets() {

        clock = findViewById(R.id.txt_clock);
        temprature = findViewById(R.id.current_degree);
        weather_stiuation = findViewById(R.id.weather_stiuation);
        weather_stiuation_image = findViewById(R.id.weather_stiuation_image);
        wind_speed1 = findViewById(R.id.txt_wind_speed);
        wind_speed2 = findViewById(R.id.txt_wind_value);
        feels_like = findViewById(R.id.txt_feelslike);
        sunrise = findViewById(R.id.txt_sunrise);
        pressure = findViewById(R.id.txt_pressure);
        sunset = findViewById(R.id.txt_sunset);
        dailyRecyclerView = findViewById(R.id.daily_recyclerView);
        hourlyRecyclerView = findViewById(R.id.hourly_recyclerView);
        windRecyclerView = findViewById(R.id.wind_recyclerView);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar_for_drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        txt_km_h = findViewById(R.id.txt_km_h);
        txt_m_sec = findViewById(R.id.txt_m_sec);
        ic_share = findViewById(R.id.share_image);
        sunriseSunsetView = findViewById(R.id.ssv);
        sunriseView = findViewById(R.id.sunriseView_time);
        sunsetView = findViewById(R.id.sunsetView_time);
        durationOFDay = findViewById(R.id.durationOfDay_time);

    }

    private void setupShare(){

        ic_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Open Weather Channel");
                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.kivitool.openweatherchannel");
                startActivity(Intent.createChooser(intent, "Share Open Weather Channel"));

            }
        });

    }

    private void setupAdapters() {
        dailyRecyclerView.setHasFixedSize(false);
        dailyRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        hourlyRecyclerView.setHasFixedSize(false);
        hourlyRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        windRecyclerView.setHasFixedSize(false);
        windRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    public void startService() {

        Intent serviceIntent = new Intent(context, NotificationService.class);
        serviceIntent.putExtra("input", "notification");
        startService(serviceIntent);
    }

    public void stopService() {

        Intent serviceIntent = new Intent(context, NotificationService.class);
        stopService(serviceIntent);

    }

    private void showNotification() {
        if (preferenceManager.getBoolean("switch_boolean")) {

            startService();

        } else {

            stopService();

        }
    }

    private String toUpperCase(String string) {

        String capital = string.substring(0, 1).toUpperCase() + string.substring(1);

        return capital;

    }

    /**
     * Very important method: When page is reloaded, it will not be slowly
     */

    private void reloadPageWithFast() {

        if (preferenceManager.getString("current_pressure") != null) {


            if (preferenceManager.getBoolean("radio_fahrenheit")) {

                temprature.setText(preferenceManager.getInteger("current_temprature_f") + "°F");
                feels_like.setText("Feels like: " + preferenceManager.getInteger("current_temprature_f") + "°F");

            } else {

                temprature.setText(preferenceManager.getInteger("current_temprature_c") + "°C");
                feels_like.setText("Feels like: " + preferenceManager.getInteger("current_temprature_c") + "°C");

            }

            weather_stiuation.setText(preferenceManager.getString("current_description") + "");

            /**
             * this cases for wind speed metr/second or km/hour
             * visiblity is for km/hour and metr/second
             */

            if (preferenceManager.getBoolean("radio_km_h")) {

                wind_speed1.setText(preferenceManager.getString("current_wind_km") + " km/h from " + preferenceManager.getString("current_wind_direction"));
                wind_speed2.setText(preferenceManager.getString("current_wind_km") + "");

                txt_m_sec.setVisibility(View.INVISIBLE);
                txt_km_h.setVisibility(View.VISIBLE);

            } else {

                wind_speed1.setText(preferenceManager.getString("current_wind_m") + " m/sec from " + preferenceManager.getString("current_wind_direction"));
                wind_speed2.setText(preferenceManager.getString("current_wind_m") + "");

                txt_km_h.setVisibility(View.INVISIBLE);
                txt_m_sec.setVisibility(View.VISIBLE);

            }

            clock.setText(preferenceManager.getString("current_date") + "/(" + preferenceManager.getString("current_time_zone") + ")");
            pressure.setText("Pressure: " + preferenceManager.getString("current_pressure") + " hPa");
            sunrise.setText("Sunrise: " + preferenceManager.getString("current_sunrise") + "");
            sunset.setText("Sunset: " + preferenceManager.getString("current_sunset") + "");

//            preferenceManager.getString("current_icon");

            if (preferenceManager.getString("current_icon").equals("01d")) {

                weather_stiuation_image.setImageResource(R.drawable.icon_01d);

            } else if (preferenceManager.getString("current_icon").equals("01n")) {

                weather_stiuation_image.setImageResource(R.drawable.icon_01n);

            } else if (preferenceManager.getString("current_icon").equals("02d")) {

                weather_stiuation_image.setImageResource(R.drawable.icon_02d);

            } else if (preferenceManager.getString("current_icon").equals("02n")) {

                weather_stiuation_image.setImageResource(R.drawable.icon_02n);

            } else if (preferenceManager.getString("current_icon").equals("03d")) {

                weather_stiuation_image.setImageResource(R.drawable.icon03d);

            } else if (preferenceManager.getString("current_icon").equals("03n")) {

                weather_stiuation_image.setImageResource(R.drawable.icon03d);

            } else if (preferenceManager.getString("current_icon").equals("04d") || preferenceManager.getString("current_icon").equals("04n")) {

                weather_stiuation_image.setImageResource(R.drawable.icon_04n);

            } else if (preferenceManager.getString("current_icon").equals("09d") || preferenceManager.getString("current_icon").equals("09n")) {

                weather_stiuation_image.setImageResource(R.drawable.icon_9d);

            } else if (preferenceManager.getString("current_icon").equals("10d")) {

                weather_stiuation_image.setImageResource(R.drawable.icon_10d);

            } else if (preferenceManager.getString("current_icon").equals("10n")) {

                weather_stiuation_image.setImageResource(R.drawable.icon_10n);

            } else if (preferenceManager.getString("current_icon").equals("11d")) {

                weather_stiuation_image.setImageResource(R.drawable.icon_11d);

            } else if (preferenceManager.getString("current_icon").equals("11n")) {

                weather_stiuation_image.setImageResource(R.drawable.icon_11n);

            } else if (preferenceManager.getString("current_icon").equals("13d") || preferenceManager.getString("current_icon").equals("13n")) {

                weather_stiuation_image.setImageResource(R.drawable.icon_13n);

            } else if (preferenceManager.getString("current_icon").equals("50d") || preferenceManager.getString("current_icon").equals("50n")) {

                weather_stiuation_image.setImageResource(R.drawable.icon_50d);

            }


            if (preferenceManager.getString("current_icon").equals("01d") || preferenceManager.getString("current_icon").equals("01n")) {

                drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.clean_sky));

            } else if (preferenceManager.getString("current_icon").equals("02d") || preferenceManager.getString("current_icon").equals("02n")) {

                drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.sunny_and_cloudy));

            } else if (preferenceManager.getString("current_icon").equals("03d") || preferenceManager.getString("current_icon").equals("03n") ||
                    preferenceManager.getString("current_icon").equals("04d") || preferenceManager.getString("current_icon").equals("04n")) {

                drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.cloudly));

            } else if (preferenceManager.getString("current_icon").equals("09d") || preferenceManager.getString("current_icon").equals("09n") ||
                    preferenceManager.getString("current_icon").equals("10d") || preferenceManager.getString("current_icon").equals("10n")) {

                drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.rainy));

            } else if (preferenceManager.getString("current_icon").equals("11d") || preferenceManager.getString("current_icon").equals("11n")) {

                drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.thunderstorm));

            } else if (preferenceManager.getString("current_icon").equals("13d") || preferenceManager.getString("current_icon").equals("13n")) {

                drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.snowy));

            } else if (preferenceManager.getString("current_icon").equals("50d") || preferenceManager.getString("current_icon").equals("50n")) {

                drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.mist));

            }

        }


        loadHourlyData();
        loadWeeklyData();
        loadWindData();

    }


    private void Response(final String city_name) {


        final Call<CurrentResult> resultCall = ManagerAll.getInstance().getWeatherCurrentInfo(city_name, units, api_key);
        resultCall.enqueue(new Callback<CurrentResult>() {
            @Override
            public void onResponse(Call<CurrentResult> call, Response<CurrentResult> response) {

                if (response.isSuccessful()) {
                    currentResult = response.body();


                    int cast_temprature = (int) currentResult.getMain().getTemp();

                    /**
                     * change celcius to fahrenheit  ----- °F = (°C × 9/5) + 32
                     */

                    int farenheit_temprature = (cast_temprature * 9 / 5) + 32;
                    if (preferenceManager.getBoolean("radio_fahrenheit")) {

                        temprature.setText(farenheit_temprature + "°F");
                        feels_like.setText("Feels like: " + farenheit_temprature + "°F");

                    } else {

                        temprature.setText(cast_temprature + "°C");
                        feels_like.setText("Feels like: " + cast_temprature + "°C");

                    }

                    String capitalDescription = toUpperCase(currentResult.getWeather().get(0).getDescription());

                    weather_stiuation.setText(capitalDescription + "");

                    // for direction with degree
                    if (currentResult.getWind().getDeg() > 348.75 ||
                            currentResult.getWind().getDeg() <= 11.25) {

                        direction = "N";

                    } else if (currentResult.getWind().getDeg() > 11.25 &&
                            currentResult.getWind().getDeg() <= 78.75) {

                        direction = "NE";

                    } else if (currentResult.getWind().getDeg() > 78.75 &&
                            currentResult.getWind().getDeg() <= 101.25) {

                        direction = "E";

                    } else if (currentResult.getWind().getDeg() > 101.25 &&
                            currentResult.getWind().getDeg() <= 168.75) {

                        direction = "SE";

                    } else if (currentResult.getWind().getDeg() > 168.75 &&
                            currentResult.getWind().getDeg() <= 191.25) {

                        direction = "S";

                    } else if (currentResult.getWind().getDeg() > 191.25 &&
                            currentResult.getWind().getDeg() <= 258.75) {

                        direction = "SW";

                    } else if (currentResult.getWind().getDeg() > 258.75 &&
                            currentResult.getWind().getDeg() <= 281.25) {

                        direction = "W";

                    } else if (currentResult.getWind().getDeg() > 281.25 &&
                            currentResult.getWind().getDeg() <= 348.75) {

                        direction = "NW";

                    }

                    pressure.setText("Pressure: " + currentResult.getMain().getPressure() + " hPa");

                    /**
                     * change wind speed from m/sec to km/h
                     */

                    double wind_speed_value = currentResult.getWind().getSpeed();
                    String formatted_wind_m = String.format("%.1f", wind_speed_value);
                    int km_h = (int) (wind_speed_value * 3.6);
                    String formatted_wind_km = km_h + "";

                    if (preferenceManager.getBoolean("radio_km_h")) {

                        wind_speed1.setText(formatted_wind_km + " km/h from " + direction);
                        wind_speed2.setText(formatted_wind_km + "");
                        preferenceManager.putString("current_wind_km", formatted_wind_km);
                        txt_m_sec.setVisibility(View.INVISIBLE);
                        txt_km_h.setVisibility(View.VISIBLE);

                    } else {

                        wind_speed1.setText(formatted_wind_m + " m/sec from " + direction);
                        wind_speed2.setText(formatted_wind_m + "");
                        preferenceManager.putString("current_wind_m", formatted_wind_m);
                        txt_km_h.setVisibility(View.INVISIBLE);
                        txt_m_sec.setVisibility(View.VISIBLE);

                    }


                    // current date
                    Calendar calendar = Calendar.getInstance();
                    String current_date = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                    String time_zone = calendar.getTimeZone().getID();
                    clock.setText(current_date + "/(" + time_zone + ")");

                    /*
                    convert unix time to local time
                     */
                    int int_sunrise = currentResult.getSys().getSunrise();
                    Date date1 = new Date(int_sunrise * 1000L);
                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
                    simpleDateFormat1.setTimeZone(TimeZone.getTimeZone(calendar.getTimeZone().getID()));
                    formated_date1 = simpleDateFormat1.format(date1);
                    sunrise.setText("Sunrise: " + formated_date1 + "");

                    int int_sunset = currentResult.getSys().getSunset();
                    Date date2 = new Date(int_sunset * 1000L);
                    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");
                    simpleDateFormat2.setTimeZone(TimeZone.getTimeZone(calendar.getTimeZone().getID()));
                    formated_date2 = simpleDateFormat2.format(date2);
                    sunset.setText("Sunset: " + formated_date2 + "");

                    String stiuation_icon = currentResult.getWeather().get(0).getIcon() + "";


                    // firstly loaded sunrise and sunset time

                    final int view_sunrise_hour_onResponse = Integer.parseInt(formated_date1.substring(0,2));
                    final int view_sunrise_minute_onResponse = Integer.parseInt(formated_date1.substring(3));
                    final int view_sunset_hour_onResponse = Integer.parseInt(formated_date2.substring(0,2));
                    final int view_sunset_minute_onResponse = Integer.parseInt(formated_date2.substring(3));

                    sunriseSunsetView.setSunriseTime(new Time(view_sunrise_hour_onResponse, view_sunrise_minute_onResponse));
                    sunriseSunsetView.setSunsetTime(new Time(view_sunset_hour_onResponse, view_sunset_minute_onResponse));

                    sunriseView.setText("Sunrise: " + formated_date1);
                    sunsetView.setText("Sunset: " + formated_date2);

                    int hour1 = ((view_sunset_hour_onResponse * 60 + view_sunset_minute_onResponse) - (view_sunrise_hour_onResponse * 60 + view_sunrise_minute_onResponse)) / 60;

                    int minute1 = ((view_sunset_hour_onResponse * 60 + view_sunset_minute_onResponse) - (view_sunrise_hour_onResponse * 60 + view_sunrise_minute_onResponse)) - (hour1 * 60);

                    if (hour1 < 0){
                        hour1 *= -1;
                    }
                    if (minute1 < 0){
                        minute1 *= -1;
                    }

                    if (minute1 != 0){
                        durationOFDay.setText("Duration of the day: " + hour1 + " h, " + minute1 + " m");
                    }else{
                        durationOFDay.setText("Duration of the day: " + hour1 + " h");
                    }

                    sunriseSunsetView.setLabelFormatter(new SunriseSunsetLabelFormatter() {
                        @Override
                        public String formatSunriseLabel(@NonNull Time sunrise) {
                            return String.format("%02d:%02d", view_sunrise_hour_onResponse, view_sunrise_minute_onResponse);
                        }

                        @Override
                        public String formatSunsetLabel(@NonNull Time sunset) {
                            return String.format("%02d:%02d", view_sunset_hour_onResponse, view_sunset_minute_onResponse);
                        }
                    });

                    sunriseSunsetView.startAnimate();



                    /**
                     * set weather icon depends on stiuation
                     */

                    if (stiuation_icon.equals("01d")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon_01d);

                    } else if (stiuation_icon.equals("01n")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon_01n);

                    } else if (stiuation_icon.equals("02d")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon_02d);

                    } else if (stiuation_icon.equals("02n")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon_02n);

                    } else if (stiuation_icon.equals("03d")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon03d);

                    } else if (stiuation_icon.equals("03n")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon03d);

                    } else if (stiuation_icon.equals("04d") || stiuation_icon.equals("04n")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon_04n);

                    } else if (stiuation_icon.equals("09d") || stiuation_icon.equals("09n")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon_9d);

                    } else if (stiuation_icon.equals("10d")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon_10d);

                    } else if (stiuation_icon.equals("10n")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon_10n);

                    } else if (stiuation_icon.equals("11d")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon_11d);

                    } else if (stiuation_icon.equals("11n")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon_11n);

                    } else if (stiuation_icon.equals("13d") || stiuation_icon.equals("13n")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon_13n);

                    } else if (stiuation_icon.equals("50d") || stiuation_icon.equals("50n")) {

                        weather_stiuation_image.setImageResource(R.drawable.icon_50d);

                    }

                    if (currentResult.getWeather().get(0).getIcon().equals("01d") || currentResult.getWeather().get(0).getIcon().equals("01n")) {

                        drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.clean_sky));

                    } else if (currentResult.getWeather().get(0).getIcon().equals("02d") || currentResult.getWeather().get(0).getIcon().equals("02n")) {

                        drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.sunny_and_cloudy));

                    } else if (currentResult.getWeather().get(0).getIcon().equals("03d") || currentResult.getWeather().get(0).getIcon().equals("03n") ||
                            currentResult.getWeather().get(0).getIcon().equals("04d") || currentResult.getWeather().get(0).getIcon().equals("04n")) {

                        drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.cloudly));

                    } else if (currentResult.getWeather().get(0).getIcon().equals("09d") || currentResult.getWeather().get(0).getIcon().equals("09n") ||
                            currentResult.getWeather().get(0).getIcon().equals("10d") || currentResult.getWeather().get(0).getIcon().equals("10n")) {

                        drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.rainy));

                    } else if (currentResult.getWeather().get(0).getIcon().equals("11d") || currentResult.getWeather().get(0).getIcon().equals("11n")) {

                        drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.thunderstorm));

                    } else if (currentResult.getWeather().get(0).getIcon().equals("13d") || currentResult.getWeather().get(0).getIcon().equals("13n")) {

                        drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.snowy));

                    } else if (currentResult.getWeather().get(0).getIcon().equals("50d") || currentResult.getWeather().get(0).getIcon().equals("50n")) {

                        drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.mist));

                    }

                    /**
                     * TODO set shared preferensces datas
                     */

                    if (preferenceManager.getBoolean("radio_fahrenheit")) {

                        preferenceManager.putInteger("current_temprature_f", farenheit_temprature);

                    } else {

                        preferenceManager.putInteger("current_temprature_c", cast_temprature);

                    }
                    preferenceManager.putString("current_date", current_date);
                    preferenceManager.putString("current_time_zone", time_zone);
                    preferenceManager.putString("current_description", capitalDescription);
                    String shared_pressure = currentResult.getMain().getPressure() + "";
                    preferenceManager.putString("current_pressure", shared_pressure);
                    preferenceManager.putString("current_wind_direction", direction);
                    preferenceManager.putString("current_sunrise", formated_date1);
                    preferenceManager.putString("current_sunset", formated_date2);
                    preferenceManager.putString("lat", lat);
                    preferenceManager.putString("lon", lon);
                    String shared_icon = currentResult.getWeather().get(0).getIcon();
                    preferenceManager.putString("current_icon", shared_icon);

                    lat = currentResult.getCoord().getLat() + "";
                    lon = currentResult.getCoord().getLon() + "";

                    /*
                    we call this method for lat and lon because we can't get lat and lon with easy way.This is easily way!!!
                     */

                    dailyResponse(lat, lon);
                    hourlyResponse(lat, lon);
                    windResponse(city_name);

                }

            }

            @Override
            public void onFailure(Call<CurrentResult> call, Throwable t) {


                // for sunrise and sunset view
                int view_sunrise_hour = Integer.parseInt(preferenceManager.getString("current_sunrise").substring(0,2));
                int view_sunrise_minute = Integer.parseInt(preferenceManager.getString("current_sunrise").substring(3));
                int view_sunset_hour = Integer.parseInt(preferenceManager.getString("current_sunset").substring(0,2));
                int view_sunset_minute = Integer.parseInt(preferenceManager.getString("current_sunset").substring(3));

                sunriseSunsetView.setSunriseTime(new Time(view_sunrise_hour, view_sunrise_minute));
                sunriseSunsetView.setSunsetTime(new Time(view_sunset_hour, view_sunset_minute));

                sunriseView.setText("Sunrise: " + preferenceManager.getString("current_sunrise"));
                sunsetView.setText("Sunset: " + preferenceManager.getString("current_sunset"));

                int hour2 = ((view_sunset_hour * 60 + view_sunset_minute) - (view_sunrise_hour * 60 + view_sunrise_minute)) / 60;

                int minute2 = ((view_sunset_hour * 60 + view_sunset_minute) - (view_sunrise_hour * 60 + view_sunrise_minute)) - (hour2 * 60);

                durationOFDay.setText("Duration of the day: " + hour2 + " h, " + minute2 + " m");

                sunriseSunsetView.startAnimate();


                if (preferenceManager.getBoolean("radio_fahrenheit")) {

                    temprature.setText(preferenceManager.getInteger("current_temprature_f") + "°F");
                    feels_like.setText("Feels like: " + preferenceManager.getInteger("current_temprature_f") + "°F");

                } else {

                    temprature.setText(preferenceManager.getInteger("current_temprature_c") + "°C");
                    feels_like.setText("Feels like: " + preferenceManager.getInteger("current_temprature_c") + "°C");

                }

                weather_stiuation.setText(preferenceManager.getString("current_description") + "");

                /**
                 * this cases for wind speed metr/second or km/hour
                 * visiblity is for km/hour and metr/second
                 */

                if (preferenceManager.getBoolean("radio_km_h")) {

                    wind_speed1.setText(preferenceManager.getString("current_wind_km") + " km/h from " + preferenceManager.getString("current_wind_direction"));
                    wind_speed2.setText(preferenceManager.getString("current_wind_km") + "");

                    txt_m_sec.setVisibility(View.INVISIBLE);
                    txt_km_h.setVisibility(View.VISIBLE);

                } else {


                    wind_speed1.setText(preferenceManager.getString("current_wind_m") + " m/sec from " + preferenceManager.getString("current_wind_direction"));
                    wind_speed2.setText(preferenceManager.getString("current_wind_m") + "");

                    txt_km_h.setVisibility(View.INVISIBLE);
                    txt_m_sec.setVisibility(View.VISIBLE);

                }

                clock.setText(preferenceManager.getString("current_date") + "/(" + preferenceManager.getString("current_time_zone") + ")");
                pressure.setText("Pressure: " + preferenceManager.getString("current_pressure") + " hPa");
                sunrise.setText("Sunrise: " + preferenceManager.getString("current_sunrise") + "");
                sunset.setText("Sunset: " + preferenceManager.getString("current_sunset") + "");
                String shared_lat = preferenceManager.getString("lat");
                String shared_lon = preferenceManager.getString("lon");

                preferenceManager.getString("current_icon");

                if (preferenceManager.getString("current_icon").equals("01d")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon_01d);

                } else if (preferenceManager.getString("current_icon").equals("01n")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon_01n);

                } else if (preferenceManager.getString("current_icon").equals("02d")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon_02d);

                } else if (preferenceManager.getString("current_icon").equals("02n")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon_02n);

                } else if (preferenceManager.getString("current_icon").equals("03d")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon03d);

                } else if (preferenceManager.getString("current_icon").equals("03n")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon03d);

                } else if (preferenceManager.getString("current_icon").equals("04d") || preferenceManager.getString("current_icon").equals("04n")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon_04n);

                } else if (preferenceManager.getString("current_icon").equals("09d") || preferenceManager.getString("current_icon").equals("09n")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon_9d);

                } else if (preferenceManager.getString("current_icon").equals("10d")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon_10d);

                } else if (preferenceManager.getString("current_icon").equals("10n")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon_10n);

                } else if (preferenceManager.getString("current_icon").equals("11d")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon_11d);

                } else if (preferenceManager.getString("current_icon").equals("11n")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon_11n);

                } else if (preferenceManager.getString("current_icon").equals("13d") || preferenceManager.getString("current_icon").equals("13n")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon_13n);

                } else if (preferenceManager.getString("current_icon").equals("50d") || preferenceManager.getString("current_icon").equals("50n")) {

                    weather_stiuation_image.setImageResource(R.drawable.icon_50d);

                }


                if (preferenceManager.getString("current_icon").equals("01d") || preferenceManager.getString("current_icon").equals("01n")) {

                    drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.clean_sky));

                } else if (preferenceManager.getString("current_icon").equals("02d") || preferenceManager.getString("current_icon").equals("02n")) {

                    drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.sunny_and_cloudy));

                } else if (preferenceManager.getString("current_icon").equals("03d") || preferenceManager.getString("current_icon").equals("03n") ||
                        preferenceManager.getString("current_icon").equals("04d") || preferenceManager.getString("current_icon").equals("04n")) {

                    drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.cloudly));

                } else if (preferenceManager.getString("current_icon").equals("09d") || preferenceManager.getString("current_icon").equals("09n") ||
                        preferenceManager.getString("current_icon").equals("10d") || preferenceManager.getString("current_icon").equals("10n")) {

                    drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.rainy));

                } else if (preferenceManager.getString("current_icon").equals("11d") || preferenceManager.getString("current_icon").equals("11n")) {

                    drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.thunderstorm));

                } else if (preferenceManager.getString("current_icon").equals("13d") || preferenceManager.getString("current_icon").equals("13n")) {

                    drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.snowy));

                } else if (preferenceManager.getString("current_icon").equals("50d") || preferenceManager.getString("current_icon").equals("50n")) {

                    drawerLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.mist));

                }


                /*
                    we call this method for lat and lon because we can't get lat and lon with easy way.This is easily way!!!
                     */
                dailyResponse(shared_lat, shared_lon);
                hourlyResponse(shared_lat, shared_lon);
                windResponse(city_name);

            }
        });


    }

    /**
     * getting daily informations with helping this method
     */
    private void dailyResponse(String lat, String lon) {


        final Call<WeeklyResult> call = ManagerAll.getInstance().getWeatherWeeklyInformations(lat, lon, "hourly,daily", units, api_key);
        call.enqueue(new Callback<WeeklyResult>() {
            @Override
            public void onResponse(Call<WeeklyResult> call, Response<WeeklyResult> response) {

                if (response.isSuccessful()) {
                    weeklyResult = response.body();

                    JsonSaveDataWeekly(weeklyResult.getDaily());
                    WeeklyAdapter weeklyAdapter = new WeeklyAdapter(context, weeklyResult.getDaily());
                    weeklyAdapter.notifyDataSetChanged();
                    dailyRecyclerView.setAdapter(weeklyAdapter);

                    int int_max = (int) weeklyResult.getDaily().get(0).getTemp().getMax();
                    preferenceManager.putString("first_day_max_temp", (int_max + ""));

                    int int_min = (int) weeklyResult.getDaily().get(0).getTemp().getMin();
                    preferenceManager.putString("first_day_min_temp", (int_min + ""));
                }
            }

            @Override
            public void onFailure(Call<WeeklyResult> call, Throwable t) {

                loadWeeklyData();

            }
        });


    }

    /**
     * getting hourly informations with helping this method
     */
    private void hourlyResponse(String lat, String lon) {


        final Call<WeeklyResult> hourlyCallResult = ManagerAll.getInstance().getWeatherWeeklyInformations(lat, lon, "hourly,daily", units, api_key);

        hourlyCallResult.enqueue(new Callback<WeeklyResult>() {
            @Override
            public void onResponse(Call<WeeklyResult> call, Response<WeeklyResult> response) {
                if (response.isSuccessful()) {

                    weeklyResult = response.body();
                    JsonSaveDataHourly(weeklyResult.getHourly());

                    HourlyAdapter hourlyAdapter = new HourlyAdapter(context, weeklyResult.getHourly());
                    hourlyAdapter.notifyDataSetChanged();
                    hourlyRecyclerView.setAdapter(hourlyAdapter);
                }
            }

            @Override
            public void onFailure(Call<WeeklyResult> call, Throwable t) {

                loadHourlyData();
                Toast.makeText(context, "Check your connection !", Toast.LENGTH_SHORT).show();

            }
        });

    }

    /**
     * getting hourly informations with helping this method
     */
    private void windResponse(String city_name) {


        Call<JsonMember5DaysResult> jsonMember5DaysResultCall = ManagerAll.getInstance().getWeather5DaysInformations(city_name, api_key);

        jsonMember5DaysResultCall.enqueue(new Callback<JsonMember5DaysResult>() {
            @Override
            public void onResponse(Call<JsonMember5DaysResult> call, Response<JsonMember5DaysResult> response) {
                if (response.isSuccessful()) {
                    jsonMember5DaysResult = response.body();
                    JsonSaveDataWind(jsonMember5DaysResult.getList());

                    WindAdapter windAdapter = new WindAdapter(context, jsonMember5DaysResult.getList(), preferenceManager);
                    windAdapter.notifyDataSetChanged();
                    windRecyclerView.setAdapter(windAdapter);

                }
            }

            @Override
            public void onFailure(Call<JsonMember5DaysResult> call, Throwable t) {

                loadWindData();

            }
        });

    }


    /**
     * current location for notification
     */


    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_CODE);

            return;
        }

        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    String lat_for_notification = String.format("%.2f", currentLocation.getLatitude());
                    String lon_for_notification = String.format("%.2f", currentLocation.getLongitude());

                    currentResponseWithLatLon(lat_for_notification, lon_for_notification);
                    dailyResponseForNotification(lat_for_notification, lon_for_notification);

                }
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    fetchLastLocation();

                }

                break;

        }
    }

    private void currentResponseWithLatLon(String lat, String lon) {

        final Call<CurrentResult> currentResultCall = ManagerAll.getInstance().getWeatherInformationsWithLatLon(lat, lon, units, api_key);

        currentResultCall.enqueue(new Callback<CurrentResult>() {
            @Override
            public void onResponse(Call<CurrentResult> call, Response<CurrentResult> response) {
                if (response.isSuccessful()) {

                    current_notificationResult = response.body();

                    int notification_temprature = (int) current_notificationResult.getMain().getTemp();
                    preferenceManager.putInteger("current_temprature_notification", notification_temprature);
                    preferenceManager.putString("current_description_notification", current_notificationResult.getWeather().get(0).getDescription());
                    preferenceManager.putString("city_name_for_notification", current_notificationResult.getName());
                    String shared_icon_notification = current_notificationResult.getWeather().get(0).getIcon();
                    preferenceManager.putString("current_icon_notification", shared_icon_notification);

                }
            }

            @Override
            public void onFailure(Call<CurrentResult> call, Throwable t) {

            }
        });

    }

    private void dailyResponseForNotification(String lat, String lon) {


        final Call<WeeklyResult> call = ManagerAll.getInstance().getWeatherWeeklyInformations(lat, lon, "hourly,daily", units, api_key);
        call.enqueue(new Callback<WeeklyResult>() {
            @Override
            public void onResponse(Call<WeeklyResult> call, Response<WeeklyResult> response) {

                if (response.isSuccessful()) {

                    weekly_notificationResult = response.body();

                    int int_max_temp = (int) weekly_notificationResult.getDaily().get(0).getTemp().getMax();
                    preferenceManager.putString("first_day_max_temp_for_notification", (int_max_temp + ""));

                    int int_min_temp = (int) weekly_notificationResult.getDaily().get(0).getTemp().getMin();
                    preferenceManager.putString("first_day_min_temp_for_notification", (int_min_temp + ""));


                }
            }

            @Override
            public void onFailure(Call<WeeklyResult> call, Throwable t) {


            }
        });


    }


    private void setupBottomNavigationViewEx() {

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_viewEx);
        bottomNavigationView.setSelectedItemId(R.id.id_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.id_hourly:

                        interstitialAd.setAdListener(new AdListener(){
                            @Override
                            public void onAdClosed() {

                                startActivity(new Intent(context, Hourly.class));
                                finish();

                            }
                        });

                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        } else {
                            startActivity(new Intent(context, Hourly.class));
                            finish();
                        }

                        break;

                    case R.id.id_wind:

                        interstitialAd.setAdListener(new AdListener(){
                            @Override
                            public void onAdClosed() {

                                startActivity(new Intent(context, Wind.class));
                                finish();

                            }
                        });

                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();

                        } else {
                            startActivity(new Intent(context, Wind.class));
                            finish();
                        }

                        break;

                    case R.id.id_daily:

                        interstitialAd.setAdListener(new AdListener(){
                            @Override
                            public void onAdClosed() {

                                startActivity(new Intent(context, Daily.class));
                                finish();

                            }
                        });

                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        } else {
                            startActivity(new Intent(context, Daily.class));
                            finish();
                        }
                        break;
                }

                return false;

            }
        });

    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }

        if (interstitialAd.isLoaded()){

            interstitialAd.show();

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.add_new_location:

                interstitialAd.setAdListener(new AdListener(){

                    @Override
                    public void onAdClosed() {

                        startActivity(new Intent(context, AddNewLocation.class));

                    }
                });

                if (interstitialAd.isLoaded()) {

                    interstitialAd.show();

                } else {

                    Intent intent = new Intent(context, AddNewLocation.class);
                    startActivity(intent);

                }

                break;

            case R.id.about_the_application:
                startActivity(new Intent(context, AboutTheApplication.class));
                break;

            case R.id.my_location:
                startActivity(new Intent(context, MyLocation.class));
                break;

            case R.id.delete_location:

                if (spinnerDatabase.getData().getCount() > 1) {

                    deleteLocationFromDatabase(spinnerItem);

                } else {

                    Toast.makeText(context, "You don't delete last locaiton !", Toast.LENGTH_SHORT).show();

                }

                spinnerSetAdapter();

                break;

            case R.id.settings:
                startActivity(new Intent(context, Settings.class));
                break;

            case R.id.contact_us:
                startActivity(new Intent(context, ContactUs.class));
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }


    // <<<<<------------------------------------------------- All Shared Preference set up methods ------------------------------------------------->>>>>

    /**
     * Data saved method
     *
     * @param hourlyItemList
     */

    private void JsonSaveDataHourly(List<HourlyItem> hourlyItemList) {

        SharedPreferences sharedPreferences = getSharedPreferences("hourly_item_list", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonArray = gson.toJson(hourlyItemList);
        editor.putString("task_list", jsonArray);
        editor.apply();

    }

    private void loadHourlyData() {

        SharedPreferences sharedPreferences = getSharedPreferences("hourly_item_list", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task_list", null);
        Type type = new TypeToken<ArrayList<HourlyItem>>() {
        }.getType();

        hourlyItemList = gson.fromJson(json, type);

        HourlyAdapter hourlyAdapter = new HourlyAdapter(context, hourlyItemList);

        hourlyRecyclerView.setAdapter(hourlyAdapter);

        hourlyAdapter.notifyDataSetChanged();

    }


    private void JsonSaveDataWeekly(List<DailyItem> dailyItemList) {

        SharedPreferences sharedPreferences = getSharedPreferences("daily_item_list", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String jsonArray = gson.toJson(dailyItemList);

        editor.putString("task_list", jsonArray);
        editor.apply();

    }

    private void loadWeeklyData() {

        SharedPreferences sharedPreferences1 = getSharedPreferences("daily_item_list", Context.MODE_PRIVATE);
        Gson gson1 = new Gson();
        String json1 = sharedPreferences1.getString("task_list", null);
        Type type1 = new TypeToken<ArrayList<DailyItem>>() {
        }.getType();

        dailyItemList = gson1.fromJson(json1, type1);

        WeeklyAdapter weeklyAdapter = new WeeklyAdapter(context, dailyItemList);

        dailyRecyclerView.setAdapter(weeklyAdapter);

        weeklyAdapter.notifyDataSetChanged();

    }


    private void JsonSaveDataWind(List<ListItem> listItems) {

        SharedPreferences sharedPreferences2 = getSharedPreferences("list_data_wind", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();

        Gson gson2 = new Gson();
        String jsonArray2 = gson2.toJson(listItems);

        editor2.putString("task_list", jsonArray2);

        editor2.apply();

    }

    private void loadWindData() {

        SharedPreferences sharedPreferences2 = getSharedPreferences("list_data_wind", MODE_PRIVATE);

        Gson gson2 = new Gson();
        String json2 = sharedPreferences2.getString("task_list", null);

        Type type2 = new TypeToken<ArrayList<ListItem>>() {
        }.getType();

        listItems = gson2.fromJson(json2, type2);

        WindAdapter windAdapter = new WindAdapter(context, listItems, preferenceManager);

        windRecyclerView.setAdapter(windAdapter);

        windAdapter.notifyDataSetChanged();

    }


    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.on_create_option, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.settingsOption:
                startActivity(new Intent(context, Settings.class));
                break;

            //TODO THIS IS ## interstitialAd ADVERTISING $$
            case R.id.help:

                interstitialAd.setAdListener(new AdListener(){

                    @Override
                    public void onAdClosed() {

                        startActivity(new Intent(context, Help.class));

                    }
                });

                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    Intent helpIntent = new Intent(context, Help.class);
                    startActivity(helpIntent);
                }
                break;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onDestroy() {
        stopService();
        super.onDestroy();
    }
}
