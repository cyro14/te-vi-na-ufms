package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.AndroidException;

import com.example.myapplication.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    private ViewPager viewPager;

    ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        addTabs();
    }

    private void addTabs() {
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_home_24));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_search_24));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_add_24));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_star_24));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_baseline_person_outline_24));

        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch(tab.getPosition()){

                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_search_24);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_add_24);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_baseline_star_24);
                        break;
                    case 4:
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_baseline_person_24);
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch(tab.getPosition()){

                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_outline_home_24);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_search_24);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_add_24);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.ic_baseline_star_border_24);
                        break;
                    case 4:
                        tabLayout.getTabAt(4).setIcon(R.drawable.ic_baseline_person_outline_24);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void init(){

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
    }

}