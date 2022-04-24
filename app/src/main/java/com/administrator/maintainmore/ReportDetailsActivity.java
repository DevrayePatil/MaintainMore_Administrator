package com.administrator.maintainmore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ReportDetailsActivity extends AppCompatActivity {

    private static final String TAG = "ReportDetailsActivityInfo";

    FirebaseFirestore db;

    Toolbar toolbar;
    
    TextView displayUserName, displayUserEmail, displayUserPhoneNumber;
    TextView displayTechnicianName, displayTechnicianEmail, displayTechnicianPhoneNumber;
    TextView displayServiceName, displayServicePrice, displayTotalServices, displayTotalAmount;
    TextView displayBookingDate, displayBookingTime, displayServiceDate, displayServiceTime;
    TextView serviceDate, serviceTime;

    String userID, technicianID, serviceID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_details);

        db = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Report");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);




        displayUserName = findViewById(R.id.displayUserName);
        displayUserEmail = findViewById(R.id.displayUserEmail);
        displayUserPhoneNumber = findViewById(R.id.displayUserPhoneNumber);

        displayTechnicianName = findViewById(R.id.displayTechnicianName);
        displayTechnicianEmail = findViewById(R.id.displayTechnicianEmail);
        displayTechnicianPhoneNumber = findViewById(R.id.displayTechnicianPhoneNumber);

        displayServiceName = findViewById(R.id.displayServiceName);
        displayServicePrice = findViewById(R.id.displayServicePrice);
        displayTotalServices = findViewById(R.id.displayTotalServices);
        displayTotalAmount = findViewById(R.id.displayTotalAmount);

        displayBookingDate = findViewById(R.id.displayBookingDate);
        displayBookingTime = findViewById(R.id.displayBookingTime);
        displayServiceDate = findViewById(R.id.displayServiceDate);
        displayServiceTime = findViewById(R.id.displayServiceTime);

        serviceDate = findViewById(R.id.serviceDate);
        serviceTime = findViewById(R.id.serviceTime);

        getDataFromPreviousActivity();
        getUserInfo();
        getTechnicianInfo();

    }

    @SuppressLint({"LongLogTag", "SetTextI18n"})
    private void getServiceInfo(String collectionPath) {

        if (collectionPath.equals("Bookings Completed")){
            serviceDate.setText("Visiting Date");
            serviceTime.setText("Visiting Time");

            db.collection(collectionPath).document(serviceID)
                    .get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        displayServiceName.setText(document.getString("serviceName"));
                        displayServicePrice.setText(document.getString("servicePrice"));
                        displayTotalServices.setText(document.getString("totalServices"));
                        displayTotalAmount.setText(document.getString("totalServicesPrice"));

                        displayBookingDate.setText(document.getString("bookingDate"));
                        displayBookingTime.setText(document.getString("bookingTime"));
                        displayServiceDate.setText(document.getString("visitingDate"));
                        displayServiceTime.setText(document.getString("visitingTime"));

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            });
        }
        else if (collectionPath.equals("Bookings Cancelled")){

            serviceDate.setText("Cancellation Date");
            serviceTime.setText("Cancellation Time");

            db.collection(collectionPath).document(serviceID)
                    .get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        displayServiceName.setText(document.getString("serviceName"));
                        displayServicePrice.setText(document.getString("servicePrice"));
                        displayTotalServices.setText(document.getString("totalServices"));
                        displayTotalAmount.setText(document.getString("totalServicesPrice"));

                        displayBookingDate.setText(document.getString("bookingDate"));
                        displayBookingTime.setText(document.getString("bookingTime"));
                        displayServiceDate.setText(document.getString("visitingDate"));
                        displayServiceTime.setText(document.getString("visitingTime"));

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            });
        }


    }

    @SuppressLint("LongLogTag")
    private void getTechnicianInfo() {
        db.collection("Technicians").document(technicianID)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    displayTechnicianName.setText(document.getString("name"));
                    displayTechnicianEmail.setText(document.getString("email"));
                    displayTechnicianPhoneNumber.setText(document.getString("phoneNumber"));

                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }


    @SuppressLint("LongLogTag")
    private void getUserInfo() {

        db.collection("Users").document(userID)
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                    displayUserName.setText(document.getString("name"));
                    displayUserEmail.setText(document.getString("email"));
                    displayUserPhoneNumber.setText(document.getString("phoneNumber"));

                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });
    }

    private void getDataFromPreviousActivity() {
        Intent intent = getIntent();

        userID = intent.getStringExtra("userID");
        technicianID = intent.getStringExtra("technicianID");
        serviceID = intent.getStringExtra("serviceID");

        String whichService = intent.getStringExtra("tableName");

        getServiceInfo(whichService);


    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}