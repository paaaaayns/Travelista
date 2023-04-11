package com.example.travelista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddPlan extends AppCompatActivity {

    LinearLayout layoutDirections, layoutActivity, layoutRestaurant, layoutLodging;
    ImageView btnBack;
    TextView pageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plan);

        pageName = findViewById(R.id.pageName);
        pageName.setText("Add Plan");



        layoutDirections = findViewById(R.id.layoutDirections);
        layoutActivity = findViewById(R.id.layoutActivity);
        layoutRestaurant = findViewById(R.id.layoutRestaurant);
        layoutLodging = findViewById(R.id.layoutLodging);

        layoutDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPlan.this, AddPlanDirections.class);
                startActivity(intent);
            }
        });

        layoutRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPlan.this, AddPlanRestaurant.class);
                startActivity(intent);
            }
        });

        layoutLodging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPlan.this, AddPlanLodging.class);
                startActivity(intent);
            }
        });

        layoutActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPlan.this, AddPlanActivity.class);
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