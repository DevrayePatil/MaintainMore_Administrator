package com.administrator.maintainmore.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.administrator.maintainmore.Adapters.NewTechnicianAdapter;
import com.administrator.maintainmore.ApprovalStatusActivity;
import com.administrator.maintainmore.Models.NewTechnicianModal;
import com.administrator.maintainmore.R;
import com.administrator.maintainmore.ReportActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class DashboardFragment extends Fragment implements NewTechnicianAdapter.viewHolder.OnNewTechnicianCardClickListener {

    private static final String TAG = "DashboardFragmentInfo";

    TextView displayNumberOfUsers, displayNumberOfTechnicians;
    TextView displayNumberOfPersonalServices, displayNumberOfHomeServices, displayNumberOfRepairAppliances;
    TextView displayNumberOfBookings;
    RecyclerView recyclerView_new_users;

    CardView cardReport;


    FirebaseFirestore db;

    public DashboardFragment() {
        // Required empty public constructor
    }

    int numberOfCounts;


    ArrayList<NewTechnicianModal> newTechnicianModals = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        db = FirebaseFirestore.getInstance();

        displayNumberOfUsers = view.findViewById(R.id.displayNumberOfUsers);
        displayNumberOfTechnicians = view.findViewById(R.id.displayNumberOfTechnicians);

        displayNumberOfPersonalServices = view.findViewById(R.id.displayNumberOfPersonalServices);
        displayNumberOfHomeServices = view.findViewById(R.id.displayNumberOfHomeServices);
        displayNumberOfRepairAppliances = view.findViewById(R.id.displayNumberOfRepairAppliances);

        displayNumberOfBookings = view.findViewById(R.id.displayNumberOfBookings);
        recyclerView_new_users = view.findViewById(R.id.recyclerView_new_users);

        cardReport = view.findViewById(R.id.cardReport);


        DashboardCounter();

        db.collection("Technicians").whereEqualTo("approvalStatus", "Registered").addSnapshotListener((value, error) -> {
            newTechnicianModals.clear();
            assert value != null;
            for (DocumentSnapshot snapshot: value){
                newTechnicianModals.add(new NewTechnicianModal(snapshot.getId(),snapshot.getString("name"),
                        snapshot.getString("email"), snapshot.getString("phoneNumber")
                        ,snapshot.getString("imageUrl")));
            }
            NewTechnicianAdapter techniciansAdapter = new NewTechnicianAdapter(newTechnicianModals, getContext(),this);
            recyclerView_new_users.setAdapter(techniciansAdapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView_new_users.setLayoutManager(linearLayoutManager);
        });



        cardReport.setOnClickListener(v -> startActivity(new Intent(requireActivity(),ReportActivity.class)));



        return view;
    }

    private void DashboardCounter() {


        db.collection("Users")
                .addSnapshotListener((value, error) -> {
            assert value != null;
            numberOfCounts = value.size();
            Log.i(TAG, "Users: " + numberOfCounts);
            displayNumberOfUsers.setText(String.valueOf(numberOfCounts));
        });

        db.collection("Technicians").whereEqualTo("approvalStatus", "Approved")
                .addSnapshotListener((value, error) -> {
            assert value != null;
            numberOfCounts = value.size();
            Log.i(TAG, "Technicians: " + numberOfCounts);
            displayNumberOfTechnicians.setText(String.valueOf(numberOfCounts));
        });


        db.collection("Personal Services").addSnapshotListener((value, error) -> {
            assert value != null;
            numberOfCounts = value.size();
            Log.i(TAG, "Personal Services: " + numberOfCounts);
            displayNumberOfPersonalServices.setText(String.valueOf(numberOfCounts));
        });

        db.collection("Home Services").addSnapshotListener((value, error) -> {
            assert value != null;
            numberOfCounts = value.size();
            Log.i(TAG, "Home Services: " + numberOfCounts);
            displayNumberOfHomeServices.setText(String.valueOf(numberOfCounts));
        });

        db.collection("Repair Appliance Services").addSnapshotListener((value, error) -> {
            assert value != null;
            numberOfCounts = value.size();
            Log.i(TAG, "Repair Appliances Services: " + numberOfCounts);
            displayNumberOfRepairAppliances.setText(String.valueOf(numberOfCounts));
        });


        db.collection("Bookings").addSnapshotListener((value, error) -> {
            assert value != null;
            numberOfCounts = value.size();
            Log.i(TAG, "Bookings: " + numberOfCounts);
            displayNumberOfBookings.setText(String.valueOf(numberOfCounts));
        });




    }

    @Override
    public void onNewTechnicianCardClickListener(int position) {

        String technicianID = newTechnicianModals.get(position).getTechnicianID();
        String technicianName = newTechnicianModals.get(position).getTechnicianName();
        String technicianEmail = newTechnicianModals.get(position).getTechnicianEmail();
        String technicianPhoneNumber = newTechnicianModals.get(position).getTechnicianPhoneNumber();

        Intent intent = new Intent(requireActivity(), ApprovalStatusActivity.class);

        intent.putExtra("technicianID", technicianID);
        intent.putExtra("technicianName", technicianName);
        intent.putExtra("technicianEmail", technicianEmail);
        intent.putExtra("technicianPhoneNumber", technicianPhoneNumber);

        startActivity(intent);
        Log.i(TAG,"ID:" + technicianID);
        Log.i(TAG, "Name:" + technicianName);
        Log.i(TAG, "Email:" + technicianEmail);
        Log.i(TAG, "Phone Number:" + technicianPhoneNumber);

    }
}