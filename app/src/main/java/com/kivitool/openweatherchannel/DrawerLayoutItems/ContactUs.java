package com.kivitool.openweatherchannel.DrawerLayoutItems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kivitool.openweatherchannel.Home.MainActivity;
import com.kivitool.openweatherchannel.R;

public class ContactUs extends AppCompatActivity {

    ImageView backFromContactUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        backFromContactUs = findViewById(R.id.backArrowForContact);

        backFromContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactUs.this, MainActivity.class));
                finish();
            }
        });

    }
}