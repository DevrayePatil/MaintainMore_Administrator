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

import com.administrator.maintainmore.Adapters.PersonalServiceCardAdapter;
import com.administrator.maintainmore.Models.PersonalServiceCardModal;
import com.administrator.maintainmore.R;
import com.administrator.maintainmore.UpdateServiceActivity;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class PersonalServiceFragment extends Fragment
        implements PersonalServiceCardAdapter.viewHolder.OnPersonalServiceCardClickListener{

    private static final String TAG = "PersonalServiceFragmentInfo";


    RecyclerView recyclerView_personalServices;
    CoordinatorLayout coordinatorLayout;

    FirebaseFirestore db;

    public PersonalServiceFragment() {
        // Required empty public constructor
    }

    ArrayList<PersonalServiceCardModal> personalServiceCardModal = new ArrayList<>();
    ArrayList<PersonalServiceCardModal> deletedService = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_service, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerView_personalServices = view.findViewById(R.id.recycleView_personalServices);
        coordinatorLayout = view.findViewById(R.id.coordinatorLayout);


        db.collection("Personal Services").addSnapshotListener((value, error) -> {
            personalServiceCardModal.clear();
            assert value != null;
            for (DocumentSnapshot snapshot: value){
                personalServiceCardModal.add(new PersonalServiceCardModal(snapshot.getId(),
                        snapshot.getString("serviceName"),
                        snapshot.getString("serviceDescription"),
                        snapshot.getString("requiredTime"),
                        snapshot.getString("servicePrice"),
                        snapshot.getString("iconUrl"),
                        snapshot.getString("backgroundImageUrl")
                        )

                );
            }
            PersonalServiceCardAdapter serviceCardAdapter = new PersonalServiceCardAdapter(personalServiceCardModal, getContext(), this);
            recyclerView_personalServices.setAdapter(serviceCardAdapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView_personalServices.setLayoutManager(linearLayoutManager);

        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView_personalServices);



        return view;
    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            String service = personalServiceCardModal.get(position).getServiceID();
            String serviceName = personalServiceCardModal.get(position).getServiceName();

            switch (direction){
                case ItemTouchHelper.LEFT:

                    deletedService.clear();
                    deletedService.add(personalServiceCardModal.remove(position));

                    db.collection("Personal Services").document(service).delete()
                            .addOnSuccessListener(unused ->{
                                Snackbar snackbar = Snackbar.make(coordinatorLayout, serviceName + " Service Deleted", Snackbar.LENGTH_LONG);
                                snackbar.setAction("Undo", v ->
                                    db.collection("Personal Services").document(service).set(deletedService.get(0))
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
                                @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

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
    public void onPersonalServiceCardClick(int position) {

        String ID = personalServiceCardModal.get(position).getServiceID();
        String name = personalServiceCardModal.get(position).getServiceName();

        Log.i(TAG,"ID: " + ID);


        Intent intent = new Intent(getActivity(), UpdateServiceActivity.class);

        intent.putExtra("serviceID", ID);
        intent.putExtra("whichService", "Personal Services");
        intent.putExtra("serviceName", name);

        startActivity(intent);
    }
}