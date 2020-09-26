package com.kivitool.openweatherchannel.DrawerLayoutItems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kivitool.openweatherchannel.Home.MainActivity;
import com.kivitool.openweatherchannel.R;

public class AboutTheApplication extends AppCompatActivity {

    ImageView backAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_the_application);

        backAbout = findViewById(R.id.backArrowForAbout);

        backAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AboutTheApplication.this, MainActivity.class));
                finish();

            }
        });
    }

}