package com.example.travelista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ItineraryPlannerPage extends AppCompatActivity {

    ConstraintLayout layoutTrip;
    Button btnAddTrip;
    ImageView btnBack;
    TextView pageName;
    TextView tvTripName, tvTripDate, tvTripDetails;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://useful-maxim-382603-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_planner_page);

        pageName = findViewById(R.id.pageName);
        pageName.setText("Itinerary Planner");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String uID = user.getUid();
        DatabaseReference userRef = databaseReference.child("userID").child(uID);

        LinearLayout trips = findViewById(R.id.trips);
        // Get keys of all trips
        DatabaseReference getTripList = FirebaseDatabase.getInstance().getReference().child("userID").child(uID).child("tripList");
        ArrayList<String> tripList = new ArrayList<>();
        getTripList.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Loop through the child nodes
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get the key and add it to the array
                    final String key = snapshot.getKey();
                    tripList.add(key);

                    // Inflate and add an include layout to the parent layout
                    LayoutInflater inflater = LayoutInflater.from(ItineraryPlannerPage.this);
                    View includeLayout = inflater.inflate(R.layout.trip_list, trips, false);
                    // Set margin to the top of the inflated layout
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) includeLayout.getLayoutParams();
                    layoutParams.topMargin = 40; // Set the desired margin in pixels or dp
                    includeLayout.setLayoutParams(layoutParams);

                    trips.addView(includeLayout);

                    // Get the values under each key
                    String tripName = snapshot.child("tripName").getValue(String.class);
                    String tripStartDate = snapshot.child("tripStartDate").getValue(String.class);
                    String tripEndDate = snapshot.child("tripEndDate").getValue(String.class);
                    String tripDescription = snapshot.child("tripDescription").getValue(String.class);

                    // Find the TextView in the inflated layout and set the text
                    tvTripName = includeLayout.findViewById(R.id.tvTripName);
                    tvTripName.setText(tripName);
                    tvTripDate = includeLayout.findViewById(R.id.tvTripDate);
                    tvTripDate.setText(tripStartDate + " - " + tripEndDate);
                    tvTripDetails = includeLayout.findViewById(R.id.tvTripDetails);
                    tvTripDetails.setText(tripDescription);

                    // OnClickListener to each include layout
                    includeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Get the index of the clicked layout
                            int index = trips.indexOfChild(includeLayout);
                            // Get the corresponding key from the tripList
                            String clickedKey = tripList.get(index);

                            // Start the activity and pass the clickedKey as an extra
                            Intent intent = new Intent(ItineraryPlannerPage.this, TripDetails.class);
                            intent.putExtra("key", clickedKey);
                            startActivity(intent);

                            Toast.makeText(ItineraryPlannerPage.this, "Key: " + key, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that may occur during data retrieval
                Toast.makeText(ItineraryPlannerPage.this, "Failed to retrieve data.", Toast.LENGTH_SHORT).show();
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
                finish();
            }
        });
    }
}