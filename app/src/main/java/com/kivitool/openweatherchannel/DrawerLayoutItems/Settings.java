package com.kivitool.openweatherchannel.DrawerLayoutItems;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.kivitool.openweatherchannel.Home.MainActivity;
import com.kivitool.openweatherchannel.PreferenceManager;
import com.kivitool.openweatherchannel.R;
import com.kivitool.openweatherchannel.Services.NotificationService;

public class Settings extends AppCompatActivity {

    private Switch notification_switch;
    private ImageView backarrow;
    private Dialog dialog;
    RadioButton celcius, fahrenheit, m_sec, km_h;
    RelativeLayout temprature_settings, about_application, wind_settings;
    private Button confirm, cancel, confirm_wind, cancel_wind;

    Context context;


    public static final String ID = "Notification";
//    public static final int NOTIFICATION_ID = 1;

    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = this;
        preferenceManager = new PreferenceManager(context);

        setupWidgets();

        temprature_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomTempratureDialog();
            }
        });

        wind_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomWindDialog();
            }
        });

        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });


        notification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    preferenceManager.putBoolean("switch_boolean", true);
                    notification_switch.setChecked(true);
                    startService();

                }else{
                    preferenceManager.putBoolean("switch_boolean", false);
                    notification_switch.setChecked(false);
                    stopService();
                }

            }
        });

        notification_switch.setChecked(preferenceManager.getBoolean("switch_boolean"));


        about_application.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, AboutTheApplication.class));
            }
        });

    }

    public void startService(){

        Intent serviceIntent = new Intent(context, NotificationService.class);
        startService(serviceIntent);
    }

    public void stopService(){

        Intent serviceIntent = new Intent(context, NotificationService.class);
        stopService(serviceIntent);

    }

    private void setupWidgets(){

        notification_switch = findViewById(R.id.notification_switch);
        backarrow = findViewById(R.id.backArrowForSettings);
        temprature_settings = findViewById(R.id.rel_time_units_settings);
        about_application = findViewById(R.id.rel_about_application);
        wind_settings = findViewById(R.id.rel_wind_units);

    }

    private void mCustomTempratureDialog(){

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_unit_dialog);
        dialog.setTitle("Temprature unit choices");
        celcius = dialog.findViewById(R.id.radio_celcius);
        fahrenheit = dialog.findViewById(R.id.radio_fahrenheit);
        confirm = dialog.findViewById(R.id.confirm);
        cancel = dialog.findViewById(R.id.cancel);

        preferenceManager.putBoolean("radio_celcius", true);
        celcius.setChecked(preferenceManager.getBoolean("radio_celcius"));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.putBoolean("radio_celcius", celcius.isChecked());
                preferenceManager.putBoolean("radio_fahrenheit", fahrenheit.isChecked());
                dialog.dismiss();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        celcius.setChecked(preferenceManager.getBoolean("radio_celcius"));
        fahrenheit.setChecked(preferenceManager.getBoolean("radio_fahrenheit"));

        dialog.show();


    }

    private void mCustomWindDialog(){

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_wind_dilog);
        dialog.setTitle("Wind speed choices");
        m_sec = dialog.findViewById(R.id.radio_m_sec);
        km_h = dialog.findViewById(R.id.radio_km_h);
        confirm_wind = dialog.findViewById(R.id.confirm_wind);
        cancel_wind = dialog.findViewById(R.id.cancel_wind);

        preferenceManager.putBoolean("radio_m_sec", true);
        m_sec.setChecked(preferenceManager.getBoolean("radio_m_sec"));

        confirm_wind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceManager.putBoolean("radio_m_sec", m_sec.isChecked());
                preferenceManager.putBoolean("radio_km_h", km_h.isChecked());
                dialog.dismiss();
            }
        });


        cancel_wind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        m_sec.setChecked(preferenceManager.getBoolean("radio_m_sec"));
        km_h.setChecked(preferenceManager.getBoolean("radio_km_h"));

        dialog.show();


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

}