package com.example.teplus.ciscourses.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.teplus.ciscourses.R;
import com.example.teplus.ciscourses.fragment.SubjectsFragment;

public class SubjectsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    String courses_name_th;
    String courses_desc;
    String sections_name_th;
    String groups_name_th;
    String groups_credit;
    String id_groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        initInstances();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, SubjectsFragment.newInstance
                            (courses_name_th, courses_desc, id_groups, groups_name_th, groups_credit, sections_name_th))
                    .commit();
        }
    }

    private void initInstances() {
        //init toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        courses_name_th = intent.getStringExtra("courses_name_th");
        courses_desc = intent.getStringExtra("courses_desc");
        sections_name_th = intent.getStringExtra("sections_name_th");
        id_groups = intent.getStringExtra("id_groups");
        groups_name_th = intent.getStringExtra("groups_name_th");
        groups_credit = intent.getStringExtra("groups_credit");
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
