package com.administrator.maintainmore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.administrator.maintainmore.Adapters.PagerAdapter;

import com.administrator.maintainmore.Fragment.ReportBookingCancelledFragment;
import com.administrator.maintainmore.Fragment.ReportBookingCompletedFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class ReportActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Report");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewPager);
        tabLayout =findViewById(R.id.tabLayout);

        tabLayout.setupWithViewPager(viewPager);

        setViewPager(viewPager);
    }


    private void setViewPager(ViewPager viewPager) {

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        pagerAdapter.addFragment(new ReportBookingCompletedFragment(),"Completed Bookings");
        pagerAdapter.addFragment(new ReportBookingCancelledFragment(),"Cancelled Bookings");

        viewPager.setAdapter(pagerAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}