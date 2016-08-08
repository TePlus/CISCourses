package com.example.teplus.ciscourses.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.teplus.ciscourses.R;
import com.example.teplus.ciscourses.fragment.SectionsFragment;

public class SectionsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    String id_courses;
    String courses_name_th;
    String courses_desc_th_year;
    String courses_credit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);

        initInstances();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, SectionsFragment.newInstance(id_courses,courses_name_th,courses_desc_th_year,courses_credit))
                    .commit();
        }
    }

    private void initInstances() {
        //init toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id_courses = intent.getStringExtra("id_courses");
        courses_name_th = intent.getStringExtra("courses_name_th");
        courses_desc_th_year = intent.getStringExtra("courses_desc_th_year");
        courses_credit= intent.getStringExtra("courses_credit");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
