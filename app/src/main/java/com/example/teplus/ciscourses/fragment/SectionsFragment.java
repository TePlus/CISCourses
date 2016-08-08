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
import com.example.teplus.ciscourses.adapter.SectionsRecyclerAdapter;
import com.example.teplus.ciscourses.item.SectionsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SectionsFragment extends Fragment {

    public static final String KEY_ID = "id_courses";
    public static final String KEY_NAME_TH = "courses_name_th";
    public static final String KEY_DESC = "courses_desc";
    public static final String KEY_CREDIT = "courses_credit";

    private String id_courses;
    private String courses_name_th;
    private String courses_desc;
    private String courses_credit;


    private static final String TAG = "TePlus";
    private List<SectionsItem> feedsList;
    private RecyclerView mRecyclerView;
    private SectionsRecyclerAdapter adapter;
    private ProgressBar progressBar;

    private TextView courses;
    private TextView desc_courses;
    private TextView credit;


    public SectionsFragment() {
        super();
    }

    public static SectionsFragment newInstance
            (String id_courses, String courses_name_th, String courses_desc,String courses_credit) {
        SectionsFragment fragment = new SectionsFragment();
        Bundle args = new Bundle();
        args.putString(KEY_ID, id_courses);
        args.putString(KEY_NAME_TH, courses_name_th);
        args.putString(KEY_DESC, courses_desc);
        args.putString(KEY_CREDIT, courses_credit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sections, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        courses = (TextView) rootView.findViewById(R.id.txt_courses);
        desc_courses = (TextView) rootView.findViewById(R.id.txt_desc_courses);
        credit = (TextView) rootView.findViewById(R.id.txt_credit);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            id_courses = bundle.getString(KEY_ID);
            courses_name_th = bundle.getString(KEY_NAME_TH);
            courses_desc = bundle.getString(KEY_DESC);
            courses_credit = bundle.getString(KEY_CREDIT);
        }

        String path = id_courses;

        courses.setText(courses_name_th);
        desc_courses.setText(courses_desc);
        credit.setText(courses_credit);

        // Downloading data from below url
        final String ip = getResources().getString(R.string.ip_address);
        final String url = "http://" + ip + "/cis/public/api/sections/" + path + "";

        new AsyncHttpTask().execute(url);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                super.onClick(view, position);

                SectionsItem feedItem = feedsList.get(position);
                String id_sections = String.valueOf(Html.fromHtml(String.valueOf(feedItem.getId())));
                String sections_name_th = String.valueOf(Html.fromHtml(String.valueOf(feedItem.getNameTh())));
                String sections_desc_th = String.valueOf(Html.fromHtml(String.valueOf(feedItem.getDescTh())));
                String sections_credit = String.valueOf(Html.fromHtml(String.valueOf(feedItem.getCredit())));

                Intent intent = new Intent(getActivity(), GroupsActivity.class);
                intent.putExtra("courses_name_th", courses_name_th);
                intent.putExtra("courses_desc", courses_desc);
                intent.putExtra("courses_credit", courses_credit);

                intent.putExtra("id_sections", id_sections);
                intent.putExtra("sections_name_th", sections_name_th);
                intent.putExtra("sections_desc_th", sections_desc_th);
                intent.putExtra("sections_credit", sections_credit);

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
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                adapter = new SectionsRecyclerAdapter(SectionsFragment.newInstance
                        (id_courses, courses_name_th, courses_desc,courses_credit).getContext(), feedsList);
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
                SectionsItem item = new SectionsItem();

                item.setId(post.optInt("id_major_sections"));
                item.setNameTh(post.optString("sections_name_th"));
                item.setCredit(post.optInt("credit"));
                item.setDescTh(post.optString("sections_desc_th"));

                feedsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
