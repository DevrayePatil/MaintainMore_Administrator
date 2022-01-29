package com.administrator.maintainmore.BottomSheetFragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;


import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.administrator.maintainmore.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class AddPersonalService extends BottomSheetDialogFragment {

    private static final int IMAGE_REQUEST_ID = 1;
    private static final int BACKGROUND_IMAGE_REQUEST_ID = 2;
    private static final String TAG = "AddPersonalServiceInfo";
    Uri uri, uriBackground;

    String documentID;


    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore db ;


    Button buttonChooseImage,buttonChooseBackgroundImage, buttonSave, buttonCancel;
    ImageView serviceIcon,serviceBackgroundImage;
    EditText editTextServiceName, editTextServiceDescription, editTextRequiredTime, editTextServicePrice;


    public AddPersonalService() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.bottom_sheet_add_personal_service, container, false);

        db = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        buttonChooseImage = view.findViewById(R.id.buttonChooseIcon);
        buttonChooseBackgroundImage = view.findViewById(R.id.buttonChooseBackgroundImage);

        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonSave = view.findViewById(R.id.buttonSave);

        serviceIcon = view.findViewById(R.id.serviceIcon);
        serviceBackgroundImage = view.findViewById(R.id.serviceBackgroundImage);

        editTextServiceName = view.findViewById(R.id.editText_serviceName);
        editTextServiceDescription = view.findViewById(R.id.editText_serviceDescription);
        editTextRequiredTime = view.findViewById(R.id.editText_requiredTime);
        editTextServicePrice = view.findViewById(R.id.editText_servicePrice);

        buttonChooseImage.setOnClickListener(view1 -> ChooseIcon());
        buttonChooseBackgroundImage.setOnClickListener(view1 -> ChooseBackgroundImage());
        buttonSave.setOnClickListener(view1 -> SaveToDatabase());

        return view;
    }



    public void SaveToDatabase() {


        String serviceName = editTextServiceName.getText().toString();
        String serviceDescription = editTextServiceDescription.getText().toString();
        String requiredTime = editTextRequiredTime.getText().toString();
        String servicePrice = editTextServicePrice.getText().toString();

        Map<String,String> service = new HashMap<>();
        String randomID = UUID.randomUUID().toString();
        Log.i(TAG,randomID);

        if (TextUtils.isEmpty(serviceName)){
            Toast.makeText(getActivity(), "Enter Service name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(serviceDescription)){
            Toast.makeText(getActivity(), "Enter Service Description", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(requiredTime)){
            Toast.makeText(getActivity(), "Enter Required time", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(servicePrice)){
            Toast.makeText(getActivity(), "Enter Service Price", Toast.LENGTH_SHORT).show();
            return;
        }
        if (uri == null){
            Toast.makeText(getActivity(), "Choose Icon", Toast.LENGTH_SHORT).show();
            return;
        }
        if (uriBackground == null){
            Toast.makeText(getActivity(), "Choose Background Image", Toast.LENGTH_SHORT).show();
            return;
        }


        service.put("serviceName",serviceName);
        service.put("serviceDescription",serviceDescription);
        service.put("requiredTime",requiredTime);
        service.put("servicePrice",servicePrice);



        if (uri != null) {
            storageReference = storageReference.child("Service icons/" + randomID);

            storageReference.putFile(uri).addOnSuccessListener(taskSnapshot ->
                    storageReference.getDownloadUrl().addOnSuccessListener(uri ->
                            SaveIconURL(String.valueOf(uri))
                    )
            ).addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show());
        }
        else {
            Toast.makeText(getActivity(), "Choose Icon", Toast.LENGTH_SHORT).show();
            return;
        }


        new Handler().postDelayed(() -> {
            if (uriBackground != null){
                storageReference = storageReference.child("Service Pictures/" +randomID);

                storageReference.putFile(uriBackground).addOnSuccessListener(taskSnapshot ->
                        storageReference.getDownloadUrl().addOnSuccessListener(uri ->
                                SaveBackgroundImageURL(String.valueOf(uri))
                        )
                ).addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show());
            }
            else {
                Toast.makeText(getActivity(), "Choose Icon", Toast.LENGTH_SHORT).show();
            }
        },2000);




        DocumentReference reference = db.collection("Personal Services").document();

        documentID = reference.getId();

        reference.set(service).addOnSuccessListener(unused ->
                        Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show()
        );

        Log.i(TAG,"ID :" + documentID);
    }

    private void SaveIconURL(String url){

        db.collection("Personal Services").document(documentID).update(
                "iconUrl", url
        ).addOnSuccessListener(unused -> Log.i(TAG, "Icon link created"))
                .addOnFailureListener(e -> Log.i(TAG,"Icon Failed " + e));

        Log.i(TAG, "Icon link: " + url);
    }

    private void SaveBackgroundImageURL( String url) {
        db.collection("Personal Services").document(documentID).update(
                "backgroundImageURL", url
        ).addOnSuccessListener(unused -> Log.i(TAG, "Background image link created"))
                .addOnFailureListener(e -> Log.i(TAG,"Background Failed " + e));

        Log.i(TAG, "Background Image link: " + url);
    }



    private void ChooseBackgroundImage() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Choose Image"),BACKGROUND_IMAGE_REQUEST_ID);
    }

    private void ChooseIcon(){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Choose Image"),IMAGE_REQUEST_ID);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST_ID && resultCode == RESULT_OK && data != null &
                (data != null ? data.getData() : null) != null){
            uri = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                serviceIcon.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        if (requestCode == BACKGROUND_IMAGE_REQUEST_ID && resultCode == RESULT_OK && data != null &
                (data != null ? data.getData() : null) != null){
            uriBackground = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uriBackground);
                serviceBackgroundImage.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }


}