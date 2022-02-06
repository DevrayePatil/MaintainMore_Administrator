package com.administrator.maintainmore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    EditText EmailId, Password;
    Button buttonLogin;

    @Override
    protected void onStart() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user!=null) {
            startActivity(new Intent(this, MainActivity.class));
        }

        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        EmailId = findViewById(R.id.editTextEmail);
        Password = findViewById(R.id.editTextPassword);

        buttonLogin= findViewById(R.id.buttonLogin);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        buttonLogin.setOnClickListener(view -> SignIn());



    }

    public void SignIn() {

        String emailId = EmailId.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if (TextUtils.isEmpty(emailId)){
            EmailId.setError("Please Enter EmailId");
            return;
        }
        if (TextUtils.isEmpty(password)){
            Password.setError("Please Enter Password");

            return;
        }


//        progressBar.setVisibility(View.VISIBLE);
        final SweetAlertDialog progressDialog= new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.setTitleText("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(emailId, password)
                .addOnCompleteListener(LoginActivity.this, task -> {
//                        progressBar.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {

//                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        if (emailId.equals("admin@maintainmore.com")){
                            SweetAlertDialog sweetAlertDialog= new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.SUCCESS_TYPE);
                            sweetAlertDialog.setTitleText("Login Successful");
                            sweetAlertDialog.setConfirmClickListener(sweetAlertDialog1 -> {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                                sweetAlertDialog1.dismissWithAnimation();
                            }).setCanceledOnTouchOutside(false);
                            sweetAlertDialog.show();
                        }
                        else {
                            new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE).setTitleText("This is not admin account").show();
                            FirebaseAuth.getInstance().signOut();
                        }



                    } else {

//                            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE).setTitleText(Objects.requireNonNull(task.getException()).getMessage()).show();
                    }
                });

    }

}