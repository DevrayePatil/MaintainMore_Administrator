package com.administrator.maintainmore.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.administrator.maintainmore.Adapters.PagerAdapter;
import com.administrator.maintainmore.R;
import com.google.android.material.tabs.TabLayout;


public class ServicesFragment extends Fragment {


    public ServicesFragment() {
        // Required empty public constructor
    }

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);

        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setViewPager(ViewPager viewPager) {

        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        pagerAdapter.addFragment(new PersonalServiceFragment(),"Personal Service");
        pagerAdapter.addFragment(new HomeServiceFragment(),"Home Service");
        pagerAdapter.addFragment(new RepairHomeAppliancesFragment(),"Repair Appliances Service");

        viewPager.setAdapter(pagerAdapter);
    }
}