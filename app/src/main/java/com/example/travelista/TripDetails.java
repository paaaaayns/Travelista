package com.example.travelista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TripDetails extends AppCompatActivity {

    ImageView btnBack;
    TextView pageName;

    Button btnAddPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        pageName = findViewById(R.id.pageName);
        pageName.setText("Trip Details");

        btnAddPlans = findViewById(R.id.btnAddPlans);
        btnAddPlans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripDetails.this, AddPlan.class);
                startActivity(intent);
            }
        });




        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}