package com.administrator.maintainmore.BottomSheetFragments;

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
    String iconUrl, backgroundImageUrl;


    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseFirestore db ;
    DocumentReference reference;


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


        reference = db.collection("Personal Services").document();
        documentID = reference.getId();

        Log.i(TAG, documentID);

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

        buttonCancel.setOnClickListener(view1 -> Toast.makeText(getActivity(), "Link" + iconUrl, Toast.LENGTH_SHORT).show());
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


        service.put("serviceType","Personal Service");
        service.put("serviceName",serviceName);
        service.put("serviceDescription",serviceDescription);
        service.put("requiredTime",requiredTime);
        service.put("servicePrice",servicePrice);
        service.put("iconUrl",iconUrl);
        service.put("backgroundImageUrl",backgroundImageUrl);


        reference.set(service).addOnSuccessListener(unused -> {
            Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
            dismiss();

        });

        Log.i(TAG,"serviceName :" + serviceName);
        Log.i(TAG,"serviceDescription :" + serviceDescription);
        Log.i(TAG,"requiredTime :" + requiredTime);
        Log.i(TAG,"servicePrice :" + servicePrice);
        Log.i(TAG,"iconUrl :" + iconUrl);
        Log.i(TAG,"backgroundImageUrl :" + backgroundImageUrl);

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

                String randomID = UUID.randomUUID().toString();

                storageReference = storageReference.child("Service Pictures/" + randomID);
                storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> iconUrl = String.valueOf(uri));
                    Toast.makeText(getActivity(), "Picture Saved", Toast.LENGTH_SHORT).show();

                })
                        .addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show());
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

                String randomID = UUID.randomUUID().toString();

                storageReference = storageReference.child("Service Pictures/" + randomID);
                storageReference.putFile(uriBackground).addOnSuccessListener(taskSnapshot -> {

                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> backgroundImageUrl = String.valueOf(uri));
                    Toast.makeText(getActivity(), "Picture Saved", Toast.LENGTH_SHORT).show();

                }).addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }


    }




}