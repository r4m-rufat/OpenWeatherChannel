package com.kivitool.openweatherchannel.DrawerLayoutItems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;
import com.kivitool.openweatherchannel.Database.SpinnerDatabase;
import com.kivitool.openweatherchannel.Home.MainActivity;
import com.kivitool.openweatherchannel.R;

import es.dmoral.toasty.Toasty;

public class AddNewLocation extends AppCompatActivity {

    ImageView back_arrow, search_location;
    EditText add_new_location;
    public static String new_location;
    Context context;
    SpinnerDatabase spinnerDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_location);
        setUpWidgets();
        context = this;
        spinnerDatabase = new SpinnerDatabase(context);

        search_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new_location = toUpperCase(add_new_location.getText().toString().trim());
                if (!new_location.isEmpty()){

                    if (spinnerDatabase.checkIsRowExists(new_location)){

                        Intent intent = new Intent(AddNewLocation.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{

                        spinnerDatabase.InsertColumn(new_location);
                        Intent intent = new Intent(AddNewLocation.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }else if (new_location.isEmpty()){

                    Toasty.error(context, "You must write a location !", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }

    private void setUpWidgets(){

        back_arrow = findViewById(R.id.backArrow);
        add_new_location = findViewById(R.id.add_new_location);
        search_location = findViewById(R.id.search_location);

    }

    private String toUpperCase(String city_name){

        String capital = city_name.substring(0, 1).toUpperCase() + city_name.substring(1).toLowerCase();

        return capital;

    }
}