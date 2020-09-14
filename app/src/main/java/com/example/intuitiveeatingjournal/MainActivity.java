package com.example.intuitiveeatingjournal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.BundleCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Bundle bundle;
    public static ArrayList<String> entries;
    public static ArrayList<String> befores;
    public static ArrayList<String> afters;
    public static ArrayList<Integer> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Colors
        colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.color0));
        colors.add(getResources().getColor(R.color.color1));
        colors.add(getResources().getColor(R.color.color2));
        colors.add(getResources().getColor(R.color.color3));
        colors.add(getResources().getColor(R.color.color4));
        colors.add(getResources().getColor(R.color.color5));
        colors.add(getResources().getColor(R.color.color6));
        colors.add(getResources().getColor(R.color.color7));
        colors.add(getResources().getColor(R.color.color8));
        colors.add(getResources().getColor(R.color.color9));
        colors.add(getResources().getColor(R.color.color10));

        // Tab Layout
        TabLayout tabLayout = findViewById(R.id.tabBar);
        TabItem tabToday = findViewById(R.id.tabToday);
        TabItem tabStats = findViewById(R.id.tabStats);
        TabItem tabHistory = findViewById(R.id.tabHistory);
        final ViewPager viewPager = findViewById(R.id.viewPager);

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Get journal items
        entries = new ArrayList<String>();
        befores = new ArrayList<String>();
        afters = new ArrayList<String>();
        readItems();

        // Get new journal item and write to file
        Bundle results = getIntent().getBundleExtra("item");
        if (results != null) {
            String entry = results.getString("entry");
            String before = results.getString("before");
            String after = results.getString("after");
            entries.add(entry);
            befores.add(before);
            afters.add(after);
            writeItems();
        }
    }

    // Save and persist items to file
    private void readItems() {
        File filesDir = getFilesDir();
        File f1 = new File(filesDir, "journal.txt");
        File f2 = new File(filesDir, "befores.txt");
        File f3 = new File(filesDir, "afters.txt");
        try {
            entries = new ArrayList<String>(FileUtils.readLines(f1));
            befores = new ArrayList<String>(FileUtils.readLines(f2));
            afters = new ArrayList<String>(FileUtils.readLines(f3));
        } catch (IOException e) {
            entries = new ArrayList<String>();
            befores = new ArrayList<String>();
            afters = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File f1 = new File(filesDir, "journal.txt");
        File f2 = new File(filesDir, "befores.txt");
        File f3 = new File(filesDir, "afters.txt");
        try {
            FileUtils.writeLines(f1, entries);
            FileUtils.writeLines(f2, befores);
            FileUtils.writeLines(f3, afters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}