package com.example.travelista;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class DirectionsPage extends AppCompatActivity {

    ImageView btnBack;

    EditText edtOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_page);

        edtOrigin = findViewById(R.id.edtOrigin);

        edtOrigin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (edtOrigin.getRight() - edtOrigin.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        edtOrigin.setText("");
                        return true;
                    }
                }

                return false;
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