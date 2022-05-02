package com.administrator.maintainmore.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.administrator.maintainmore.Adapters.TechniciansAdapter;
import com.administrator.maintainmore.Models.TechniciansModal;
import com.administrator.maintainmore.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class TechnicianFragment extends Fragment {

    RecyclerView recyclerView_technician;

    FirebaseFirestore db;


    public TechnicianFragment() {
        // Required empty public constructor
    }

    ArrayList<TechniciansModal> techniciansModals = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_technician, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerView_technician = view.findViewById(R.id.recyclerView_technician);

        db.collection("Technicians").whereEqualTo("approvalStatus", "Approved").addSnapshotListener((value, error) -> {
            techniciansModals.clear();
            assert value != null;
            for (DocumentSnapshot snapshot: value){
                techniciansModals.add(new TechniciansModal(snapshot.getId(),snapshot.getString("name"),
                        snapshot.getString("email"),
                        snapshot.getString("imageUrl")));
            }
            TechniciansAdapter techniciansAdapter = new TechniciansAdapter(techniciansModals, getContext());
            recyclerView_technician.setAdapter(techniciansAdapter);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView_technician.setLayoutManager(linearLayoutManager);


        return view;
    }
}