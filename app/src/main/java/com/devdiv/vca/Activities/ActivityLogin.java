package com.devdiv.vca.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.devdiv.vca.Adapters.TabVPAdapter;
import com.devdiv.vca.Fragment.SignUPFragment;
import com.devdiv.vca.R;
import com.google.android.material.tabs.TabLayout;

public class ActivityLogin extends AppCompatActivity {

    TabLayout tablayout;
    ViewPager viewpager;

    // define adapter for viewpager
    TabVPAdapter tabVPAdapter;

    float v = 0;
    boolean openF2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // hide toolbar & status bar
        getSupportActionBar().hide();
        initabviews();

//        Bundle extras = getIntent().getExtras();
//        if (extras != null && extras.containsKey("openF2"))
//            openF2 = extras.getBoolean("openF2");
//        if (openF2) {
//            //add or replace fragment F2 in container
////            SignUPFragment signUPFragment = new SignUPFragment();
//
//            tabVPAdapter = new TabVPAdapter(getSupportFragmentManager(), 1);
//            viewpager.setAdapter(tabVPAdapter);
//
//        }
    }

    private void initabviews() {
        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);

        // here we set name that we want to show in tabs
        tablayout.addTab(tablayout.newTab().setText("Log In"));
        tablayout.addTab(tablayout.newTab().setText("Sign UP"));

        tabVPAdapter = new TabVPAdapter(getSupportFragmentManager(), tablayout.getTabCount());
        viewpager.setAdapter(tabVPAdapter);

        tablayout.setTranslationY(300);

        tablayout.setAlpha(v);

        tablayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();

        // connect tablayout with pager
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // connect pager with tab layout
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
    }
}