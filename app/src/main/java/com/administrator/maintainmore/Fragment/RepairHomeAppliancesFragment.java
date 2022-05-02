package com.administrator.maintainmore.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RepairHomeAppliancesFragment extends Fragment
        implements RepairApplianceServiceAdapter.ViewHolder.OnRepairApplianceServiceClickListener {


    private static final String TAG = "RepairHomeAppliancesFragmentInfo";


    RecyclerView recyclerView_repairApplianceServices;
    CoordinatorLayout coordinatorLayout;

    FirebaseFirestore db;

    public RepairHomeAppliancesFragment() {
        // Required empty public constructor
    }

    ArrayList<RepairApplianceCardModal> repairApplianceCardModals = new ArrayList<>();
    ArrayList<RepairApplianceCardModal> deletedService = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_repair_home_appliances, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerView_repairApplianceServices = view.findViewById(R.id.recycleView_repairApplianceServices);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);


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

            RepairApplianceServiceAdapter repairApplianceServiceAdapter =
                    new RepairApplianceServiceAdapter(repairApplianceCardModals, getContext(), this);
            recyclerView_repairApplianceServices.setAdapter(repairApplianceServiceAdapter);

        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView_repairApplianceServices.setLayoutManager(linearLayoutManager);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView_repairApplianceServices);

        return view;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            String service = repairApplianceCardModals.get(position).getServiceID();
            String serviceName = repairApplianceCardModals.get(position).getServiceName();

            switch (direction){
                case ItemTouchHelper.LEFT:

                    deletedService.clear();
                    deletedService.add(repairApplianceCardModals.remove(position));

                    db.collection("Repair Appliance Services").document(service).delete()
                            .addOnSuccessListener(unused ->{
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, serviceName + " Service Deleted",
                                        Snackbar.LENGTH_LONG);
                                snackbar.setAction("Undo", v ->
                                    db.collection("Repair Appliance Services").document(service)
                                            .set(deletedService.get(0))
                                );
                                snackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
                                snackbar.show();

                            });

                    break;
                case  ItemTouchHelper.RIGHT:

                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.color_danger))
                    .addActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


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