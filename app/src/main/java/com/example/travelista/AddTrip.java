package com.example.travelista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AddTrip extends AppCompatActivity {

    ImageView btnBack;
    TextView pageName;

    Button btnSaveTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        pageName = findViewById(R.id.pageName);
        pageName.setText("Add Trip");





        btnSaveTrip = findViewById(R.id.btnSaveTrip);
        btnSaveTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTrip.this, TripDetails.class);
                startActivity(intent);
                finish();
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