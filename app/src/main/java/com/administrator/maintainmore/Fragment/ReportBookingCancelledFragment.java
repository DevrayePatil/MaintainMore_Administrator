package com.administrator.maintainmore.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.administrator.maintainmore.Adapters.ServiceReportAdapter;
import com.administrator.maintainmore.Models.ServiceReportModal;
import com.administrator.maintainmore.R;
import com.administrator.maintainmore.ReportDetailsActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class ReportBookingCancelledFragment extends Fragment implements ServiceReportAdapter.ViewHolder.OnServiceReportCardChickListener {

    private static final String TAG = "ReportBookingCancelledFragmentInfo";

    RecyclerView recyclerView_serviceReport;

    FirebaseFirestore db;

    public ReportBookingCancelledFragment() {
        // Required empty public constructor
    }

    ArrayList<ServiceReportModal> serviceReportModals = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_booking_canclled, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerView_serviceReport = view.findViewById(R.id.recyclerView_serviceReport);

        db.collection("Bookings Cancelled").addSnapshotListener((value, error) -> {
            serviceReportModals.clear();
            assert value != null;
            for (DocumentSnapshot snapshot: value){
                serviceReportModals.add(new ServiceReportModal(snapshot.getId(),
                        snapshot.getString("whoBookedService"),
                        snapshot.getString("assignedTechnician")
                        ));



            }
            ServiceReportAdapter serviceReportAdapter = new ServiceReportAdapter(serviceReportModals, getContext(), this);
            recyclerView_serviceReport.setAdapter(serviceReportAdapter);
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView_serviceReport.setLayoutManager(linearLayoutManager);


        return view;
    }

    @Override
    public void onServiceCardClickListener(int position) {
        String userID = serviceReportModals.get(position).getWhoBookedService();
        String technicianID = serviceReportModals.get(position).getAssignedTechnician();
        String serviceID = serviceReportModals.get(position).getServiceID();

        Intent intent = new Intent(requireActivity(), ReportDetailsActivity.class);

        intent.putExtra("userID", userID);
        intent.putExtra("technicianID", technicianID);
        intent.putExtra("serviceID", serviceID);

        intent.putExtra("tableName", "Bookings Cancelled");

        startActivity(intent);
    }
}