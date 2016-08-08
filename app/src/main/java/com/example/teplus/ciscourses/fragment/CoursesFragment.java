package com.example.teplus.ciscourses.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.teplus.ciscourses.Listener.RecyclerClickListener;
import com.example.teplus.ciscourses.Listener.RecyclerTouchListener;
import com.example.teplus.ciscourses.R;
import com.example.teplus.ciscourses.activity.CoursesActivity;
import com.example.teplus.ciscourses.activity.SectionsActivity;
import com.example.teplus.ciscourses.adapter.CoursesRecyclerAdapter;
import com.example.teplus.ciscourses.item.CoursesItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CoursesFragment extends Fragment {

    private static final String TAG = "TePlus";
    private List<CoursesItem> feedsList;
    private RecyclerView mRecyclerView;
    private CoursesRecyclerAdapter adapter;
    private ProgressBar progressBar;

    public CoursesFragment() {
        super();
    }

    public static CoursesFragment newInstance() {
        CoursesFragment fragment = new CoursesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        // Downloading data from below url
        final String ip = getResources().getString(R.string.ip_address);
        final String url = "http://"+ ip +"/cis/public/api/courses";

        Log.d(TAG, "initInstances Te: " + url);

        new AsyncHttpTask().execute(url);

       mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                super.onClick(view, position);

                    /*SectionsFragment fragment = SectionsFragment.newInstance();
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.contentContainer , fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();*/

                CoursesItem feedItem = feedsList.get(position);
                String id_courses = String.valueOf(Html.fromHtml(String.valueOf(feedItem.getId())));
                String courses_name_th = String.valueOf(Html.fromHtml(String.valueOf(feedItem.getNameTh())));
                String courses_desc_th = String.valueOf(Html.fromHtml(String.valueOf(feedItem.getDescTh())));
                String courese_year = String.valueOf(Html.fromHtml(String.valueOf(feedItem.getYear())));
                String courses_credit = String.valueOf(Html.fromHtml(String.valueOf(feedItem.getCredit())));

                Intent intent = new Intent(getActivity() , SectionsActivity.class);
                intent.putExtra("id_courses",id_courses);
                intent.putExtra("courses_name_th",courses_name_th);
                intent.putExtra("courses_desc_th_year",courses_desc_th + courese_year);
                intent.putExtra("courses_credit",courses_credit);

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
                adapter = new CoursesRecyclerAdapter(CoursesFragment.newInstance().getContext(), feedsList);
                mRecyclerView.setAdapter(adapter);

            } else {
                Toast.makeText(getContext(),"Failed to fetch data!",Toast.LENGTH_SHORT).show();
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
                CoursesItem item = new CoursesItem();

                item.setId(post.optInt("id"));
                item.setNameTh(post.optString("name_th"));
                item.setDescTh(post.optString("desc_th"));
                item.setYear(post.optInt("year"));
                item.setCredit(post.optInt("credit"));

                feedsList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
