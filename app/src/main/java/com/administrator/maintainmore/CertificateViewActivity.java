package com.administrator.maintainmore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class CertificateViewActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate_view);

        imageView = findViewById(R.id.imageView);

        SetImageToImageView();
    }

    private void SetImageToImageView() {

        Intent intent = getIntent();
        String certificateImageUrl = intent.getStringExtra("imageView");

        Glide.with(getApplicationContext()).load(certificateImageUrl)
                .placeholder(R.drawable.ic_person).into(imageView);
    }
}