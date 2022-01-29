package com.administrator.maintainmore.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.administrator.maintainmore.Adapters.UserAdapter;
import com.administrator.maintainmore.Models.UserModal;
import com.administrator.maintainmore.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;


public class UserFragment extends Fragment {

    RecyclerView recyclerView_Users;

    FirebaseFirestore db;

    public UserFragment() {
        // Required empty public constructor
    }

    ArrayList<UserModal> userModals = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerView_Users = view.findViewById(R.id.recycleView_Users);


        db.collection("Users").addSnapshotListener((value, error) -> {
            userModals.clear();
            assert value != null;
            for (DocumentSnapshot snapshot: value){
                userModals.add(new UserModal(snapshot.getId(),snapshot.getString("name"), snapshot.getString("email")
                        ,snapshot.getString("imageUrl")));
            }
            UserAdapter userAdapter = new UserAdapter(userModals, getContext());
            recyclerView_Users.setAdapter(userAdapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView_Users.setLayoutManager(linearLayoutManager);

        });



        return view;
    }


}