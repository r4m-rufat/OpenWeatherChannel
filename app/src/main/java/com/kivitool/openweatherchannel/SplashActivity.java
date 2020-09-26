package com.kivitool.openweatherchannel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.kivitool.openweatherchannel.Home.MainActivity;
import com.kivitool.openweatherchannel.Utils.CheckInternetConnection;

public class SplashActivity extends Activity {

    private Context context;
    private CheckInternetConnection connection;
    private TextView owc_text;
    private Animation fade_in_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;

        owc_text = findViewById(R.id.owc_text);

        fade_in_text = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.splash_text_anim);
        owc_text.startAnimation(fade_in_text);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, SearchActivity.class);
                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();

            }
        }, 2500);


    }

}