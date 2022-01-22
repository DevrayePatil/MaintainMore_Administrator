package com.example.maintainmoreadministrator.BottomSheetFragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maintainmoreadministrator.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class AddHomeAppliances extends BottomSheetDialogFragment {



    public AddHomeAppliances() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bottom_sheet_add_home_appliances, container, false);
    }
}