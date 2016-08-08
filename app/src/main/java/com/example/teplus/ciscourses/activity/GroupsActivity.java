package com.example.teplus.ciscourses.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.teplus.ciscourses.R;
import com.example.teplus.ciscourses.fragment.GroupsFragment;

public class GroupsActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    String courses_name_th;
    String courses_desc;
    String sections_name_th;
    String sections_desc_th;
    String sections_credit;
    String id_sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        initInstances();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, GroupsFragment.newInstance
                            (courses_name_th,courses_desc,id_sections,sections_name_th,sections_desc_th,sections_credit))
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
        courses_name_th = intent.getStringExtra("courses_name_th");
        courses_desc = intent.getStringExtra("courses_desc");

        id_sections = intent.getStringExtra("id_sections");
        sections_name_th = intent.getStringExtra("sections_name_th");
        sections_desc_th = intent.getStringExtra("sections_desc_th");
        sections_credit = intent.getStringExtra("sections_credit");
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
