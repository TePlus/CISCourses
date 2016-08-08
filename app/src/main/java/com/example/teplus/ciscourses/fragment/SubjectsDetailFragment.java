package com.example.teplus.ciscourses.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teplus.ciscourses.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SubjectsDetailFragment extends Fragment {

    private static final String TAG = "TePlus";
    private ProgressBar progressBar;

    private TextView tv_nameTh;
    private TextView tv_nameEn;
    private TextView tv_credit;
    private TextView tv_descTh;
    private TextView tv_descEn;
    private TextView tv_hrs_describe;
    private TextView tv_hrs_practice;
    private TextView tv_hrs_study;
    private TextView tv_state;
    private TextView tv_pre_requisite;
    private TextView tv_code;

    private String name_th;
    private String name_en;
    private String desc_th;

    public static final String KEY_ID = "id_subjects";

    private String id_subjects;
    private String desc_en;
    private String hrs_describe;
    private String pre_requisite;
    private String hrs_practice;
    private String state;
    private String hrs_study;
    private int credit;
    private String code;

    public SubjectsDetailFragment() {
        super();
    }

    public static SubjectsDetailFragment newInstance(String id_subjects) {
        SubjectsDetailFragment fragment = new SubjectsDetailFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        args.putString(KEY_ID, id_subjects);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subject_detail, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View view) {

        tv_code = (TextView) view.findViewById(R.id.txt_code);
        tv_nameTh = (TextView) view.findViewById(R.id.txt_name_th);
        tv_nameEn = (TextView) view.findViewById(R.id.txt_name_en);
        tv_credit = (TextView) view.findViewById(R.id.txt_credit);
        tv_descTh = (TextView) view.findViewById(R.id.txt_desc_th);
        tv_descEn = (TextView) view.findViewById(R.id.txt_desc_en);
        tv_hrs_describe = (TextView) view.findViewById(R.id.txt_hrs_describe);
        tv_hrs_practice = (TextView) view.findViewById(R.id.txt_hrs_practice);
        tv_hrs_study = (TextView) view.findViewById(R.id.txt_hrs_study);
        tv_state = (TextView) view.findViewById(R.id.txt_state);
        tv_pre_requisite = (TextView) view.findViewById(R.id.txt_pre_requisite);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            id_subjects = bundle.getString(KEY_ID);
        }

        String path = id_subjects;

        Log.d(TAG, "initInstances: " + path);

        // Downloading data from below url
        final String ip = getResources().getString(R.string.ip_address);
        final String url = "http://" + ip + "/cis/public/api/subjects_detail/" + path + "";
        Log.d(TAG, "Te: " + url);

        new AsyncHttpTask().execute(url);

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
                tv_code.setText(code);
                tv_nameTh.setText(name_th);
                tv_nameEn.setText(name_en);
                tv_descTh.setText(desc_th);
                tv_descEn.setText(desc_en);
                tv_credit.setText(String.valueOf(credit));
                tv_hrs_describe.setText(String.valueOf(hrs_describe));
                tv_hrs_practice.setText(String.valueOf(hrs_practice));
                tv_hrs_study.setText(String.valueOf(hrs_study));
                tv_pre_requisite.setText(pre_requisite);

                if (state.matches("-")) {
                    tv_state.setText("");
                } else {
                    tv_state.setText(state);
                }

            } else {
                Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("result");

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);

                name_th = post.optString("name_th");
                name_en = post.optString("name_en");
                desc_th = post.optString("desc_th");
                desc_en = post.optString("desc_en");
                hrs_describe = post.optString("hrs_describe");
                hrs_practice = post.optString("hrs_practice");

                hrs_study = post.optString("hrs_study");
                state = post.optString("state");
                pre_requisite = post.optString("pre_requisite");
                credit = post.optInt("credit");
                code = post.optString("code");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
