package com.example.travelista;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MapPage extends AppCompatActivity implements OnMapReadyCallback{

    DrawerLayout drawerLayout;
    ImageView menu;
    LinearLayout layoutHome, layoutDirections, layoutGoToPlaces, layoutItinerary, layoutAbout, layoutSignOut;

    TextView lblName, lblEmail;


    FirebaseAuth mAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://useful-maxim-382603-default-rtdb.firebaseio.com/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_page);

        drawerLayout = findViewById(R.id.drawerLayout);
        menu = findViewById(R.id.menu);
        layoutHome = findViewById(R.id.layoutHome);
        layoutDirections = findViewById(R.id.layoutDirections);
        layoutGoToPlaces = findViewById(R.id.layoutGoToPlaces);
        layoutItinerary = findViewById(R.id.layoutItinerary);
        layoutAbout = findViewById(R.id.layoutAbout);
        layoutSignOut = findViewById(R.id.layoutSignOut);
        lblName = findViewById(R.id.lblName);
        lblEmail = findViewById(R.id.lblEmail);

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        String uID = user.getUid();
        DatabaseReference userRef = databaseReference.child("userID").child(uID);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);

                lblName.setText(name);
                lblEmail.setText(email);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });



        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });

        layoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        layoutDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MapPage.this, DirectionsPage.class);
            }
        });

        layoutSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MapPage.this, "Account signed out.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });











        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Map);
        mapFragment.getMapAsync(this);


    }



















    // OPEN NAVIGATION DRAWER
    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    // CLOSE NAVIGATION DRAWER
    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    // REDIRECT ON NAVIGATION DRAWER CLICK
    public static void redirectActivity(Activity activity, Class secondActivity) {
        Intent intent = new Intent(activity, secondActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

        closeDrawer(drawerLayout);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}
