package com.administrator.maintainmore.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.administrator.maintainmore.Adapters.UserAdapter;
import com.administrator.maintainmore.Models.UsersModal;
import com.administrator.maintainmore.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;


public class UserFragment extends Fragment implements UserAdapter.viewHolder.OnUserCardClickListener {

    private static final String TAG = "UserFragmentInfo";

    RecyclerView recyclerView_Users;

    FirebaseFirestore db;

    public UserFragment() {
        // Required empty public constructor
    }

    ArrayList<UsersModal> usersModals = new ArrayList<>();


    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerView_Users = view.findViewById(R.id.recyclerView_Users);


        db.collection("Users").addSnapshotListener((value, error) -> {
            usersModals.clear();
            assert value != null;
            for (DocumentSnapshot snapshot: value){
                usersModals.add(new UsersModal(snapshot.getId(),snapshot.getString("name"), snapshot.getString("email")
                        ,snapshot.getString("imageUrl")));
            }
            UserAdapter userAdapter = new UserAdapter(usersModals, getContext(), this);
            recyclerView_Users.setAdapter(userAdapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView_Users.setLayoutManager(linearLayoutManager);

        });



        return view;
    }


    @Override
    public void onUserCardClickListener(int position) {

        String userName = usersModals.get(position).getName();
        String userEmail = usersModals.get(position).getEmail();
        String imageUrl = usersModals.get(position).getImageUrl();


        Log.i(TAG, "User Name: "+ userName);
        Log.i(TAG, "User Email: "+ userEmail);
        Log.i(TAG, "User Photo: "+ imageUrl);

    }
}