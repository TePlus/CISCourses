package com.example.teplus.ciscourses.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teplus.ciscourses.Listener.RecyclerClickListener;
import com.example.teplus.ciscourses.Listener.RecyclerTouchListener;
import com.example.teplus.ciscourses.R;
import com.example.teplus.ciscourses.activity.GroupsActivity;
import com.example.teplus.ciscourses.activity.SubjectsActivity;
import com.example.teplus.ciscourses.adapter.GroupsRecyclerAdapter;
import com.example.teplus.ciscourses.item.GroupsItem;
import com.example.teplus.ciscourses.item.SectionsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GroupsFragment extends Fragment {

    public static final String KEY_COURSES_NAME_TH = "courses_name_th";
    public static final String KEY_COURSES_DESC = "courses_desc";

    public static final String KEY_ID = "id_sections";
    public static final String KEY_SECTIONS_NAME_TH = "sections_name_th";
    public static final String KEY_SECTIONS_DESC_TH = "sections_desc_th";
    public static final String KEY_SECTIONS_CREDIT = "sections_credit";

    private String courses_name_th;
    private String courses_desc;

    private String sections_credit;
    private String sections_name_th;
    private String sections_desc_th;
    private String id_sections;

    private TextView courses;
    private TextView desc_courses;
    private TextView tv_sections_name_th;
    private TextView tv_sections_desc_th;
    private TextView tv_sections_credit;

    private static final String TAG = "TePlus";
    private List<GroupsItem> feedsList;
    private RecyclerView mRecyclerView;
    private GroupsRecyclerAdapter adapter;
    private ProgressBar progressBar;

    public GroupsFragment() {
        super();
    }

    public static GroupsFragment newInstance
            (String courses_name_th, String courses_desc, String id_sections, String sections_name_th, String sections_desc_th,String sections_credit) {
        GroupsFragment fragment = new GroupsFragment();
        Bundle args = new Bundle();
        args.putString(KEY_ID, id_sections);
        args.putString(KEY_COURSES_NAME_TH, courses_name_th);
        args.putString(KEY_COURSES_DESC, courses_desc);
        args.putString(KEY_SECTIONS_NAME_TH, sections_name_th);
        args.putString(KEY_SECTIONS_DESC_TH, sections_desc_th);
        args.putString(KEY_SECTIONS_CREDIT, sections_credit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_groups, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        courses = (TextView) rootView.findViewById(R.id.txt_courses);
        desc_courses = (TextView) rootView.findViewById(R.id.txt_desc_courses);
        tv_sections_name_th = (TextView) rootView.findViewById(R.id.txt_sections_name_th);
        tv_sections_desc_th = (TextView) rootView.findViewById(R.id.txt_sections_desc_th);
        tv_sections_credit = (TextView) rootView.findViewById(R.id.txt_credit);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();
        if (bundle != null) {

            courses_name_th = bundle.getString(KEY_COURSES_NAME_TH);
            courses_desc = bundle.getString(KEY_COURSES_DESC);

            id_sections = bundle.getString(KEY_ID);
            sections_name_th = bundle.getString(KEY_SECTIONS_NAME_TH);
            sections_desc_th = bundle.getString(KEY_SECTIONS_DESC_TH);
            sections_credit = bundle.getString(KEY_SECTIONS_CREDIT);
        }

        String path = id_sections;

        courses.setText(courses_name_th);
        desc_courses.setText(courses_desc);

        tv_sections_name_th.setText(sections_name_th);
        tv_sections_desc_th.setText(sections_desc_th);
        tv_sections_credit.setText(sections_credit);

        // Downloading data from below url
        final String ip = getResources().getString(R.string.ip_address);
        final String url = "http://" + ip + "/cis/public/api/groups/" + path + "";

        new AsyncHttpTask().execute(url);


        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                super.onClick(view, position);

                GroupsItem feedItem = feedsList.get(position);
                String id_groups = String.valueOf(Html.fromHtml(String.valueOf(feedItem.getId())));
                String groups_name_th = String.valueOf(Html.fromHtml(String.valueOf(feedItem.getNameTh())));
                String groups_credit = String.valueOf(Html.fromHtml(String.valueOf(feedItem.getCredit())));

                Intent intent = new Intent(getActivity(), SubjectsActivity.class);
                intent.putExtra("courses_name_th", courses_name_th);
                intent.putExtra("courses_desc", courses_desc);
                intent.putExtra("id_groups", id_groups);
                intent.putExtra("sections_name_th",sections_name_th);
                intent.putExtra("groups_name_th",groups_name_th);
                intent.putExtra("groups_credit",groups_credit);

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                super.onLongClick(view, position);
            }
        }));
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            getActivity().setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI

            if (result == 1) {
                adapter = new GroupsRecyclerAdapter(GroupsFragment.newInstance
                        (courses_name_th,courses_desc,id_sections,sections_name_th,sections_desc_th,sections_credit)
                        .getContext(), feedsList);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("result");
            feedsList = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                GroupsItem item = new GroupsItem();

                item.setId(post.optInt("id"));
                item.setNameTh(post.optString("name_th"));
                item.setCredit(post.optInt("credit"));

                feedsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
