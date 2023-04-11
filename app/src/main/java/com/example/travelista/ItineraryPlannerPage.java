package com.example.travelista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ItineraryPlannerPage extends AppCompatActivity {

    Button btnAddTrip;
    ImageView btnBack;
    TextView pageName;
    TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_planner_page);

        pageName = findViewById(R.id.pageName);
        pageName.setText("Itinerary Planner");

        textView3 = findViewById(R.id.textView3);

        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItineraryPlannerPage.this, TripDetails.class);
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

        btnAddTrip = findViewById(R.id.btnAddTrip);
        btnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItineraryPlannerPage.this, AddTrip.class);
                startActivity(intent);
            }
        });
    }
}