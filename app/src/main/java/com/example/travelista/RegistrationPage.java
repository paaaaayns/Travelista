package com.example.travelista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationPage extends AppCompatActivity {

    ImageView btnBack;
    TextView pageName;
    TextInputEditText edtRegName, edtRegEmail, edtRegContactNumber, edtRegPassword1, edtRegPassword2;

    Button btnCreate;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://useful-maxim-382603-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        // Tool Bar
        pageName = findViewById(R.id.pageName);
        pageName.setText("Create Account");

        edtRegName = findViewById(R.id.edtRegName);
        edtRegEmail = findViewById(R.id.edtRegEmail);
        edtRegContactNumber = findViewById(R.id.edtRegContactNumber);
        edtRegPassword1 = findViewById(R.id.edtRegPassword1);
        edtRegPassword2 = findViewById(R.id.edtRegPassword2);

        btnCreate = findViewById(R.id.btnCreate);

        mAuth = FirebaseAuth.getInstance();

        // Button - Create Account
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtRegName.getText().toString();
                String email = edtRegEmail.getText().toString();
                String contact = "0" + edtRegContactNumber.getText().toString();
                String password1 = edtRegPassword1.getText().toString();
                String password2 = edtRegPassword2.getText().toString();

                // Check if fields are empty
                if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(RegistrationPage.this, "Please complete all fields.", Toast.LENGTH_SHORT).show();
                }

                // Check if passwords are correct
                else if (!password1.equals(password2)) {
                    Toast.makeText(RegistrationPage.this, "Incorrect password.", Toast.LENGTH_SHORT).show();
                }

                // Store user information into Firebase
                else {
                    mAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                String uID = user.getUid();

                                // Store user information into Realtime Database
                                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        databaseReference.child("userID").child(uID).child("name").setValue(name);
                                        databaseReference.child("userID").child(uID).child("contact").setValue(contact);
                                        databaseReference.child("userID").child(uID).child("email").setValue(email);
                                        databaseReference.child("userID").child(uID).child("password").setValue(password1);

                                        Toast.makeText(RegistrationPage.this, "Account registered successfully", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(RegistrationPage.this, LoginPage.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                // Sign the user out
                                mAuth.signOut();

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegistrationPage.this, "Email already exists.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
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