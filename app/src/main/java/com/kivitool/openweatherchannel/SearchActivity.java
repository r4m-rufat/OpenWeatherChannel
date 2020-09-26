package com.kivitool.openweatherchannel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kivitool.openweatherchannel.Database.SpinnerDatabase;
import com.kivitool.openweatherchannel.Home.MainActivity;
import com.kivitool.openweatherchannel.Utils.CheckInternetConnection;


import es.dmoral.toasty.Toasty;

public class SearchActivity extends AppCompatActivity {

    private ImageView search;
    CheckInternetConnection connection;
    EditText location;
    String city_name;
    Context context;
    SpinnerDatabase spinnerDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        context = this;
        connection = new CheckInternetConnection(context);
        spinnerDatabase = new SpinnerDatabase(context);

        location = findViewById(R.id.edit_city_name);
        search = findViewById(R.id.search_location);


        if (spinnerDatabase.checkingNull()) {

            startActivity(new Intent(context, MainActivity.class));
            finish();

        } else {

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (connection.isNetworkAvailableAndConnected()){

                        city_name = location.getText().toString();

                        if (!city_name.isEmpty()) {

                            spinnerDatabase.InsertColumn(toUpperCase(city_name));

                            startActivity(new Intent(context, MainActivity.class));
                            finish();

                        } else {

                            Toasty.error(context, "You must write a location !", Toasty.LENGTH_SHORT).show();

                        }

                    }else{

                        Toasty.error(context, "Check internet connection !", Toasty.LENGTH_SHORT).show();

                    }

                }
            });

        }


    }

    private String toUpperCase(String city_name) {

        String capital = city_name.substring(0, 1).toUpperCase() + city_name.substring(1).toLowerCase();

        return capital;

    }


}