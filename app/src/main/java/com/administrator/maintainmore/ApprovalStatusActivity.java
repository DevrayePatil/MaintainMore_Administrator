package com.administrator.maintainmore;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class ApprovalStatusActivity extends AppCompatActivity {

    private static final String TAG = "ApprovalStatusActivityInfo";

    String technicianID;
    String certificateImageUrl;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db ;
    DocumentReference documentReference;


    TextView displayName,displayEmail;
    ImageView profilePicture;

    TextView displayTechnicianName, displayTechnicianEmail, displayTechnicianPhoneNumber;
    TextView displayTechnicianGender, displayTechnicianDOB, displayTechnicianRole;
    ImageView serviceCertificate;

    Button buttonCancel, buttonApprove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_status);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        technicianID = Objects.requireNonNull(firebaseUser).getUid();


        buttonCancel = findViewById(R.id.buttonCancel);
        buttonApprove = findViewById(R.id.buttonApprove);

        LinkComponents();
        GetDataFromPreviousActivity();
        LoginUserInfo();
        SetDataToComponent();

        buttonCancel.setOnClickListener(view -> CancelApplication());
        buttonApprove.setOnClickListener(view -> ApproveApplication());
    }

    private void ApproveApplication() {
        db.collection("Technicians").document(technicianID).update(
                "approvalStatus", "Approved"
        ).addOnSuccessListener(unused -> {
            Toast.makeText(this, "Application Approved", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e ->
                Toast.makeText(this, "Failed" + e, Toast.LENGTH_SHORT).show()
        );
    }

    private void CancelApplication() {
        db.collection("Technicians").document(technicianID).update(
                "approvalStatus", "Rejected"
        ).addOnSuccessListener(unused -> {
            Toast.makeText(this, "Application Rejected", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e ->
                Toast.makeText(this, "Failed" + e, Toast.LENGTH_SHORT).show()
        );
    }

    private void LinkComponents() {

        displayName = findViewById(R.id.displayName);
        displayEmail = findViewById(R.id.displayEmail);
        profilePicture = findViewById(R.id.profilePicture);

        displayTechnicianName = findViewById(R.id.displayTechnicianName);
        displayTechnicianEmail = findViewById(R.id.displayTechnicianEmail);
        displayTechnicianPhoneNumber = findViewById(R.id.displayTechnicianPhoneNumber);
        displayTechnicianGender = findViewById(R.id.displayTechnicianGender);
        displayTechnicianDOB = findViewById(R.id.displayTechnicianDOB);
        displayTechnicianRole = findViewById(R.id.displayTechnicianRole);
        serviceCertificate = findViewById(R.id.serviceCertificate);

    }

    private void LoginUserInfo() {

        documentReference = db.collection("Technicians").document(technicianID);

        documentReference.addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            if (value != null && value.exists()){
                displayName.setText(value.getString("name"));
                displayEmail.setText(value.getString("email"));
                Glide.with(getApplicationContext()).load(value.getString("imageUrl"))
                        .placeholder(R.drawable.ic_person).into(profilePicture);
            }
        });
    }

    private void SetDataToComponent() {

        documentReference = db.collection("Technicians").document(technicianID);

        documentReference.addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            if (value != null && value.exists()){
                displayTechnicianName.setText(value.getString("name"));
                displayTechnicianEmail.setText(value.getString("email"));
                displayTechnicianPhoneNumber.setText(value.getString("phoneNumber"));
                displayTechnicianGender.setText(value.getString("gender"));
                displayTechnicianDOB.setText(value.getString("dob"));
                displayTechnicianRole.setText(value.getString("technicalRole"));

                certificateImageUrl = value.getString("serviceCertificate");
                Glide.with(getApplicationContext()).load(value.getString("serviceCertificate"))
                        .placeholder(R.drawable.ic_person).into(serviceCertificate);
                
                
                serviceCertificate.setOnClickListener(view -> CertificateView());
            }
        });
    }

    private void CertificateView() {

        Intent intent = new Intent(this, CertificateViewActivity.class);
        intent.putExtra("imageView", certificateImageUrl);
        startActivity(intent);
    }

    @SuppressLint("LongLogTag")
    private void GetDataFromPreviousActivity(){
        Intent intent = getIntent();

        technicianID = intent.getStringExtra("technicianID");

        Log.i(TAG,"ID: " + technicianID);
    }
}