package com.administrator.maintainmore.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.administrator.maintainmore.LoginActivity;
import com.administrator.maintainmore.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class ProfileFragment extends Fragment {

   Button buttonLogout;
   TextView textViewEmail;


    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        buttonLogout = view.findViewById(R.id.buttonLogout);
        textViewEmail = view.findViewById(R.id.textViewEmail);

        buttonLogout.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            requireActivity().finish();
        });


        String mail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        textViewEmail.setText(mail);

        return view;
    }
}