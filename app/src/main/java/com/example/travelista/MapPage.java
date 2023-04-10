package com.example.travelista;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapPage extends AppCompatActivity implements OnMapReadyCallback {

    DrawerLayout drawerLayout;
    ImageView btnMenu;
    LinearLayout layoutHome, layoutDirections, layoutGoToPlaces, layoutItinerary, layoutAbout, layoutSignOut;

    TextView lblName, lblEmail;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://useful-maxim-382603-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_page);

        drawerLayout = findViewById(R.id.drawerLayout);
        btnMenu = findViewById(R.id.btnMenu);
        layoutHome = findViewById(R.id.layoutHome);
        layoutDirections = findViewById(R.id.layoutDirections);
        layoutGoToPlaces = findViewById(R.id.layoutGoToPlaces);
        layoutItinerary = findViewById(R.id.layoutItinerary);
        layoutAbout = findViewById(R.id.layoutAbout);
        layoutSignOut = findViewById(R.id.layoutSignOut);
        lblName = findViewById(R.id.lblName);
        lblEmail = findViewById(R.id.lblEmail);

        // Get User Information
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
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

        // Open Navigation Drawer
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDrawer(drawerLayout);
            }
        });

        // Redirect Page - Home Page/Map Page
        layoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        // Redirect Page - Directions Page
        layoutDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectActivity(MapPage.this, DirectionsPage.class);
            }
        });

        // Redirect - Sign Out
        layoutSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MapPage.this, "Account signed out.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        // Get Location
        locationProvider = LocationServices.getFusedLocationProviderClient(this);

        getLocation();



    }

    final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient locationProvider;

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }


        Task<Location> task = locationProvider.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Map);
                    mapFragment.getMapAsync(MapPage.this);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Please turn on Location Permissions.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    GoogleMap gMaps;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMaps = googleMap;

        LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        gMaps.addMarker(new MarkerOptions().position(myLocation).title("You are here!"));
        gMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f));
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}
