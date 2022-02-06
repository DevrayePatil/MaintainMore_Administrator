package com.administrator.maintainmore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;

import android.view.ViewGroup;
import android.view.Window;

import com.administrator.maintainmore.BottomSheetFragments.AddHomeAppliances;
import com.administrator.maintainmore.BottomSheetFragments.AddHomeService;
import com.administrator.maintainmore.BottomSheetFragments.AddPersonalService;
import com.administrator.maintainmore.Fragment.DashboardFragment;
import com.administrator.maintainmore.Fragment.ProfileFragment;
import com.administrator.maintainmore.Fragment.SearchFragment;
import com.administrator.maintainmore.Fragment.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

//    private FirebaseAuth firebaseAuth;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton buttonFab;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        buttonFab = findViewById(R.id.buttonFab);

        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelected);

        buttonFab.setOnClickListener(view -> directGo());


        DashboardFragment dashboardFragment = new DashboardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, dashboardFragment);
        fragmentTransaction.commit();
    }

    private void directGo() {
        AddPersonalService addPersonalService = new AddPersonalService();
        addPersonalService.show(getSupportFragmentManager(), addPersonalService.getTag());
    }

    private void AddServicesBottomSheet() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_add_services);

        ConstraintLayout personalService, homeService, homeAppliance;

        personalService = dialog.findViewById(R.id.personalService);
        homeService = dialog.findViewById(R.id.homeService);
        homeAppliance = dialog.findViewById(R.id.homeAppliances);

        personalService.setOnClickListener(view1 -> {
            AddPersonalService addPersonalService = new AddPersonalService();
            addPersonalService.show(getSupportFragmentManager(), addPersonalService.getTag());
            dialog.dismiss();
        });
        homeService.setOnClickListener(view1 -> {
            AddHomeService addServices = new AddHomeService();
            addServices.show(getSupportFragmentManager(),addServices.getTag());
            dialog.dismiss();
        });
        homeAppliance.setOnClickListener(view1 -> {
            AddHomeAppliances addServices = new AddHomeAppliances();
            addServices.show(getSupportFragmentManager(),addServices.getTag());
            dialog.dismiss();
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }


    public BottomNavigationView.OnNavigationItemSelectedListener itemSelected = item -> {

        Fragment setFragment = null;

        if (item.getItemId() == R.id.dashboard){
            setFragment = new DashboardFragment();
        } else if(item.getItemId() == R.id.users){
            setFragment = new UsersFragment();
        }else if(item.getItemId() == R.id.profile){
            setFragment = new ProfileFragment();
        }else if(item.getItemId() == R.id.services){
            setFragment = new SearchFragment();
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        assert setFragment != null;
        fragmentTransaction.replace(R.id.fragmentContainer, setFragment);
        fragmentTransaction.commit();

        return true;
    };

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_exit);
        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> finishAffinity());
        builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.show();


    }
}