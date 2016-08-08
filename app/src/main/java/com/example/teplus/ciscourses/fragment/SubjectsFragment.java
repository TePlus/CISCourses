package com.example.teplus.ciscourses.fragment;

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

import com.example.teplus.ciscourses.R;
import com.example.teplus.ciscourses.adapter.SubjectsRecyclerAdapter;
import com.example.teplus.ciscourses.item.SubGroupsItem;
import com.example.teplus.ciscourses.item.SubjectsItem;
import com.example.teplus.ciscourses.model.DataModel;
import com.example.teplus.ciscourses.util.DividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SubjectsFragment extends Fragment {

    public static final String KEY_COURSES_NAME_TH = "courses_name_th";
    public static final String KEY_COURSES_DESC = "courses_desc";

    public static final String KEY_ID = "id_groups";
    public static final String KEY_GROUPS_NAME_TH = "groups_name_th";
    public static final String KEY_GROUPS_CREDIT = "groups_credit";
    public static final String KEY_SECTIONS_NAME_TH = "sections_name_th";

    private String courses_name_th;
    private String courses_desc;

    private String sections_name_th;
    private String groups_name_th;
    private String groups_credit;
    private String id_groups;

    private TextView courses;
    private TextView desc_courses;
    private TextView tv_sections_name_th;
    private TextView tv_groups_name_th;
    private TextView tv_groups_credit;

    private static final String TAG = "TePlus";
    private List<SubjectsItem> feedsSubjects;
    private List<SubGroupsItem> feedsSubGroups;
    private ProgressBar progressBar;

    private RecyclerView mRecyclerView;
    private SubjectsRecyclerAdapter adapter;

    List<DataModel> allSampleData;

    public SubjectsFragment() {
        super();
    }

    public static SubjectsFragment newInstance
            (String courses_name_th, String courses_desc, String id_groups, String groups_name_th, String groups_credit, String sections_name_th) {
        SubjectsFragment fragment = new SubjectsFragment();
        Bundle args = new Bundle();
        args.putString(KEY_ID, id_groups);
        args.putString(KEY_COURSES_NAME_TH, courses_name_th);
        args.putString(KEY_COURSES_DESC, courses_desc);
        args.putString(KEY_SECTIONS_NAME_TH, sections_name_th);
        args.putString(KEY_GROUPS_NAME_TH, groups_name_th);
        args.putString(KEY_GROUPS_CREDIT, groups_credit);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_subjects, container, false);
        initInstances(rootView);

        Bundle bundle = getArguments();
        if (bundle != null) {

            courses_name_th = bundle.getString(KEY_COURSES_NAME_TH);
            courses_desc = bundle.getString(KEY_COURSES_DESC);

            id_groups = bundle.getString(KEY_ID);
            sections_name_th = bundle.getString(KEY_SECTIONS_NAME_TH);
            groups_name_th = bundle.getString(KEY_GROUPS_NAME_TH);
            groups_credit = bundle.getString(KEY_GROUPS_CREDIT);
            Log.d(TAG, "TePlus" + id_groups);
        }

        String path = id_groups;

        courses.setText(courses_name_th);
        desc_courses.setText(courses_desc);
        tv_sections_name_th.setText(sections_name_th);
        tv_groups_name_th.setText(groups_name_th);
        tv_groups_credit.setText(groups_credit);

        // Downloading data from below url
        final String ip = getResources().getString(R.string.ip_address);
        final String url_subjects = "http://" + ip + "/cis/public/api/subjects/" + path + "";
        final String url_sub_groups = "http://" + ip + "/cis/public/api/sub_groups/" + path + "";

        new AsyncHttpTask().execute(url_sub_groups, url_subjects);

        return rootView;
    }

    private void initInstances(View rootView) {

        allSampleData = new ArrayList<DataModel>();

        courses = (TextView) rootView.findViewById(R.id.txt_courses);
        desc_courses = (TextView) rootView.findViewById(R.id.txt_desc_courses);
        tv_sections_name_th = (TextView) rootView.findViewById(R.id.txt_sections_name_th);
        tv_groups_name_th = (TextView) rootView.findViewById(R.id.txt_group_name_th);
        tv_groups_credit = (TextView) rootView.findViewById(R.id.txt_credit);

        //RecyclerView
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(getContext(), null));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        //Progressbar
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            getActivity().setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection_sub_groups;
            HttpURLConnection urlConnection_subjects;
            try {
                URL url_sub_groups = new URL(params[0]);
                URL url_subjects = new URL(params[1]);
                urlConnection_sub_groups = (HttpURLConnection) url_sub_groups.openConnection();
                urlConnection_subjects = (HttpURLConnection) url_subjects.openConnection();
                int statusCode_sub_groups = urlConnection_sub_groups.getResponseCode();
                int statusCode_subjects = urlConnection_sub_groups.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode_sub_groups == 200 && statusCode_subjects == 200) {
                    BufferedReader r_sub_groups = new BufferedReader(new InputStreamReader(urlConnection_sub_groups.getInputStream()));
                    BufferedReader r_subjects = new BufferedReader(new InputStreamReader(urlConnection_subjects.getInputStream()));

                    StringBuilder response_sub_groups = new StringBuilder();
                    StringBuilder response_subjects = new StringBuilder();

                    String line_sub_groups;
                    String line_subjects;
                    while ((line_sub_groups = r_sub_groups.readLine()) != null) {
                        response_sub_groups.append(line_sub_groups);
                    }

                    while ((line_subjects = r_subjects.readLine()) != null) {
                        response_subjects.append(line_subjects);
                    }

                    parseResultSubGroups(response_sub_groups.toString());
                    parseResultSubjects(response_subjects.toString());

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

                ReadData();

                adapter = new SubjectsRecyclerAdapter(SubjectsFragment.newInstance
                        (courses_name_th, courses_desc, id_groups, groups_name_th, groups_credit, sections_name_th)
                        .getContext(), allSampleData);
                mRecyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResultSubGroups(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("result");
            feedsSubGroups = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                SubGroupsItem item = new SubGroupsItem();

                item.setNameTh(post.optString("name_th"));
                //item.setNameEn(post.optString("name_en"));
                item.setDescTh(post.optString("desc_th"));
                feedsSubGroups.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseResultSubjects(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("result");
            feedsSubjects = new ArrayList<>();

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                SubjectsItem item = new SubjectsItem();

                item.setId(post.optInt("id_subjects"));
                item.setCode(post.optString("code"));
                item.setNameTh(post.optString("name_th"));
                item.setNameEn(post.optString("name_en"));
                item.setSubGroupsNameTh(post.optString("sub_groups_name_th"));
                item.setCredit(post.optInt("credit"));

                feedsSubjects.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void ReadData() {

        for (int i = 0; i < feedsSubGroups.size(); i++) {

            DataModel dm = new DataModel();

            SubGroupsItem sub_groups = feedsSubGroups.get(i);
            dm.setHeaderTitle(String.valueOf(Html.fromHtml(sub_groups.getNameTh())));
            dm.setDescTitle(String.valueOf(Html.fromHtml(sub_groups.getDescTh())));

            ArrayList<String> nameThItem = new ArrayList<>();
            ArrayList<String> nameEnItem = new ArrayList<>();
            ArrayList<String> creditItem = new ArrayList<>();
            ArrayList<String> codeItem = new ArrayList<>();
            ArrayList<String> idItem = new ArrayList<>();

            for (int j = 0; j < feedsSubjects.size(); j++) {

                SubjectsItem subjects = feedsSubjects.get(j);
                if (sub_groups.getNameTh().matches(subjects.getSubGroupsNameTh())) {

                    nameThItem.add(String.valueOf(Html.fromHtml(subjects.getNameTh())));
                    nameEnItem.add(String.valueOf(Html.fromHtml(subjects.getNameEn())));
                    creditItem.add(String.valueOf(Html.fromHtml(String.valueOf(subjects.getCredit()))));
                    codeItem.add(String.valueOf(Html.fromHtml(String.valueOf(subjects.getCode()))));
                    idItem.add(String.valueOf(Html.fromHtml(String.valueOf(subjects.getId()))));

                }
            }
            dm.setNameThItems(nameThItem);
            dm.setNameEnItems(nameEnItem);
            dm.setCreditItems(creditItem);
            dm.setCodeItems(codeItem);
            dm.setIdItems(idItem);
            allSampleData.add(dm);

        }
    }
}
