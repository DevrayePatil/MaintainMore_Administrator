package com.example.maintainmoreadministrator.BottomSheetFragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;


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

import com.example.maintainmoreadministrator.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class AddPersonalService extends BottomSheetDialogFragment {

    private static final int IMAGE_REQUEST_ID = 1;
    private static final String TAG = "AddPersonalServiceInfo";
    Uri uri;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore db ;


    Button buttonChooseImage, buttonSave, buttonCancel;
    ImageView imageViewService;
    EditText editTextServiceName;


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


        buttonChooseImage = view.findViewById(R.id.buttonChooseImage);
        buttonCancel = view.findViewById(R.id.buttonCancel);
        buttonSave = view.findViewById(R.id.buttonSave);
        imageViewService = view.findViewById(R.id.serviceImage);
        editTextServiceName = view.findViewById(R.id.editText_serviceName);
        
        buttonChooseImage.setOnClickListener(view1 -> ChooseImage());
        buttonSave.setOnClickListener(view1 -> SaveToDatabase());

        return view;
    }

    public void SaveToDatabase() {
        String serviceName = editTextServiceName.getText().toString();

        if (TextUtils.isEmpty(serviceName)){
            Toast.makeText(getActivity(), "Enter Service name", Toast.LENGTH_SHORT).show();
            return;
        }
        String randomID = UUID.randomUUID().toString();
        Log.i(TAG,randomID);
        if (uri != null){
            storageReference = storageReference.child("Personal Service Pictures/" +randomID);
            storageReference.putFile(uri).addOnSuccessListener(taskSnapshot ->
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

                Map<String,String> service = new HashMap<>();
                service.put("serviceName",serviceName);
                service.put("serviceImage",String.valueOf(uri));

                db.collection("Personal Services").document().set(service).addOnSuccessListener(unused ->
                        Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show());
            })).addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show());

        }
        else {
            Toast.makeText(getActivity(), "Choose Image", Toast.LENGTH_SHORT).show();
        }
    }

    private void ChooseImage(){
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
                imageViewService.setImageBitmap(bitmap);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}