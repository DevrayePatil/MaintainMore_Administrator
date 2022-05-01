package com.administrator.maintainmore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;


public class UpdateServiceActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST_ID = 1;
    private static final int BACKGROUND_IMAGE_REQUEST_ID = 2;

    private static final String TAG = "UpdateServiceActivityInfo";

    String iconUrl, backgroundImageUrl;

    FirebaseFirestore db;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DocumentReference reference;


    Toolbar toolbar;
    EditText editServiceName, editServiceDescription, editRequiredTime, editServicePrice;
    ImageView iconImage, backgroundImage;

    Uri uri, uriBackground;

    Button buttonChangeIcon, buttonChangeBackgroundImage;
    Button buttonCancel, buttonSave;



    String serviceID, serviceName, whichService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_service);

        db = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.toolbar);

        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        GetDataFromCard();


        reference = db.collection(whichService).document();


        editServiceName = findViewById(R.id.editServiceName);
        editServiceDescription = findViewById(R.id.editServiceDescription);
        editRequiredTime = findViewById(R.id.editRequiredTime);
        editServicePrice = findViewById(R.id.editServicePrice);

        iconImage = findViewById(R.id.iconImage);
        backgroundImage = findViewById(R.id.backgroundImage);

        buttonChangeIcon = findViewById(R.id.buttonChangeIcon);
        buttonChangeBackgroundImage = findViewById(R.id.buttonChangeBackgroundImage);
        
        buttonCancel = findViewById(R.id.buttonCancel);
        buttonSave = findViewById(R.id.buttonSave);

        buttonChangeBackgroundImage.setOnClickListener(view -> UpdateBackgroundImage());
        buttonChangeIcon.setOnClickListener(view -> UpdateIcon());
        
        buttonCancel.setOnClickListener(view -> finish());
        buttonSave.setOnClickListener(view -> UpdateServiceInformation());

        setSupportActionBar(toolbar);

        GetServiceDetails();

    }

    private void UpdateBackgroundImage() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose Image"),BACKGROUND_IMAGE_REQUEST_ID);
    }

    private void UpdateIcon() {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Choose Image"),IMAGE_REQUEST_ID);
    }

    private void UpdateServiceInformation() {


        String serviceName = Objects.requireNonNull(editServiceName.getText()).toString();
        String serviceDescription = editServiceDescription.getText().toString();
        String requiredTime = Objects.requireNonNull(editRequiredTime.getText()).toString();
        String servicePrice = Objects.requireNonNull(editServicePrice.getText()).toString();

        db.collection(whichService).document(serviceID).update(
                "serviceName", serviceName,
                "serviceDescription", serviceDescription,
                "requiredTime", requiredTime,
                "servicePrice", servicePrice


        ).addOnSuccessListener(unused ->
                Toast.makeText(getApplicationContext(), "Service Updated", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(getApplicationContext(), "Failed to update service" + e,
                                Toast.LENGTH_SHORT).show());

    }


    private void GetServiceDetails() {
        db.collection(whichService).document(serviceID)
                .addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
            if (value != null && value.exists()){

                Objects.requireNonNull(getSupportActionBar()).setTitle(value.getString("serviceName"));


                editServiceName.setText(value.getString("serviceName"));
                editServiceDescription.setText(value.getString("serviceDescription"));
                editRequiredTime.setText(value.getString("requiredTime"));
                editServicePrice.setText(value.getString("servicePrice"));

                Glide.with(getApplicationContext()).load(value.getString("iconUrl"))
                        .into(iconImage);

                Glide.with(getApplicationContext()).load(value.getString("backgroundImageUrl"))
                        .into(backgroundImage);


            }
        });
    }



    @SuppressLint("LongLogTag")
    private void GetDataFromCard(){
        Intent intent = getIntent();

        serviceID = intent.getStringExtra("serviceID");
        serviceName = intent.getStringExtra("serviceName");

        whichService = intent.getStringExtra("whichService");
        Log.i(TAG,"ID: " + serviceID);
    }

    @SuppressLint("LongLogTag")
    private void SetImageURL(String imageUrl, String uri) {

        db.collection("Personal Services").document(serviceID).update(
                imageUrl, uri
        ).addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), "link created", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed to create link" + e, Toast.LENGTH_SHORT).show());

        Log.i(TAG, "Saved link: " + uri);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_ID && resultCode == RESULT_OK && data != null &
                (data != null ? data.getData() : null) != null){
            uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                iconImage.setImageBitmap(bitmap);

                String randomID = UUID.randomUUID().toString();

                storageReference = storageReference.child("Service Pictures/" + randomID);
                storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        SetImageURL("iconUrl", String.valueOf(uri));
                        iconUrl = String.valueOf(uri);
                    });
                    Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_SHORT).show();

                })
                        .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        if (requestCode == BACKGROUND_IMAGE_REQUEST_ID && resultCode == RESULT_OK && data != null &
                (data != null ? data.getData() : null) != null){
            uriBackground = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uriBackground);
                backgroundImage.setImageBitmap(bitmap);

                String randomID = UUID.randomUUID().toString();

                storageReference = storageReference.child("Service Pictures/" + randomID);
                storageReference.putFile(uriBackground).addOnSuccessListener(taskSnapshot -> {

                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

                        backgroundImageUrl = String.valueOf(uri);
                        SetImageURL("backgroundImageUrl", String.valueOf(uri));

                    });
                    Toast.makeText(getApplicationContext(), "Picture Saved", Toast.LENGTH_SHORT).show();

                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }


    }
}