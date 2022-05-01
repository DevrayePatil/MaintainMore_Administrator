package com.administrator.maintainmore.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.administrator.maintainmore.Adapters.RepairApplianceServiceAdapter;
import com.administrator.maintainmore.Models.RepairApplianceCardModal;
import com.administrator.maintainmore.R;
import com.administrator.maintainmore.UpdateServiceActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RepairHomeAppliancesFragment extends Fragment
        implements RepairApplianceServiceAdapter.ViewHolder.OnRepairApplianceServiceClickListener {


    private static final String TAG = "RepairHomeAppliancesFragmentInfo";


    RecyclerView recyclerView_repairApplianceServices;

    FirebaseFirestore db;

    public RepairHomeAppliancesFragment() {
        // Required empty public constructor
    }

    ArrayList<RepairApplianceCardModal> repairApplianceCardModals = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_repair_home_appliances, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerView_repairApplianceServices = view.findViewById(R.id.recycleView_repairApplianceServices);


        db.collection("Repair Appliance Services").addSnapshotListener((value, error) -> {
            repairApplianceCardModals.clear();
            assert value != null;
            for (DocumentSnapshot snapshot: value){
                repairApplianceCardModals.add(new RepairApplianceCardModal(snapshot.getId(),
                                snapshot.getString("serviceName"),
                                snapshot.getString("serviceDescription"),
                                snapshot.getString("requiredTime"),
                                snapshot.getString("servicePrice"),
                                snapshot.getString("iconUrl"),
                                snapshot.getString("backgroundImageUrl")
                        )

                );
            }

            RepairApplianceServiceAdapter repairApplianceServiceAdapter = new RepairApplianceServiceAdapter(repairApplianceCardModals, getContext(), this);
            recyclerView_repairApplianceServices.setAdapter(repairApplianceServiceAdapter);

        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView_repairApplianceServices.setLayoutManager(linearLayoutManager);

        return view;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onRepairApplianceServiceClickListener(int position) {

        String ID = repairApplianceCardModals.get(position).getServiceID();
        String serviceName = repairApplianceCardModals.get(position).getServiceName();

        Log.i(TAG,"ID: " + ID);


        Intent intent = new Intent(getActivity(), UpdateServiceActivity.class);

        intent.putExtra("serviceID", ID);
        intent.putExtra("serviceName", serviceName);
        intent.putExtra("whichService", "Repair Appliance Services");

        startActivity(intent);
    }
}