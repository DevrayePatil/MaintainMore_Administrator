package com.administrator.maintainmore.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.administrator.maintainmore.R;
import com.google.firebase.firestore.FirebaseFirestore;


public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragmentInfo";

    TextView displayNumberOfUsers, displayNumberOfTechnicians;
    TextView displayNumberOfPersonalServices, displayNumberOfHomeServices, displayNumberOfRepairAppliances;
    TextView displayNumberOfBookings;

    FirebaseFirestore db;

    public DashboardFragment() {
        // Required empty public constructor
    }

    int numberOfCounts;

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


        DashboardCounter();


        return view;
    }

    private void DashboardCounter() {


        db.collection("Users").addSnapshotListener((value, error) -> {
            assert value != null;
            numberOfCounts = value.size();
            Log.i(TAG, "Users: " + numberOfCounts);
            displayNumberOfUsers.setText(String.valueOf(numberOfCounts));
        });

        db.collection("Technicians").addSnapshotListener((value, error) -> {
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
}