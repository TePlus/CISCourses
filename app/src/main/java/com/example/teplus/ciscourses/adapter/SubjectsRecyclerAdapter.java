package com.example.teplus.ciscourses.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teplus.ciscourses.R;
import com.example.teplus.ciscourses.activity.SubjectsDetailActivity;
import com.example.teplus.ciscourses.model.DataModel;
import com.example.teplus.ciscourses.util.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SubjectsRecyclerAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder> {

    private static final String TAG = "TePlus";
    private Context mContext;

    private List<DataModel> allData;

    public SubjectsRecyclerAdapter(Context context, List<DataModel> data) {
        this.mContext = context;
        this.allData = data;
    }

    @Override
    public int getSectionCount() {
        return allData.size();
    }

    @Override
    public int getItemCount(int section) {
        return allData.get(section).getNameThItems().size();
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {
        String sectionName = allData.get(section).getHeaderTitle();
        String sectionDesc = allData.get(section).getDescTitle();
        SectionViewHolder sectionViewHolder = (SectionViewHolder) holder;


        if (sectionName.matches("-")) {
            /*
            sectionViewHolder.sectionTitle.setText("");
            sectionViewHolder.rootLayout.setBackgroundColor(Color.parseColor("#fff"));*/
            sectionViewHolder.rootLayout.setVisibility(LinearLayout.GONE);
            sectionViewHolder.sectionTitle.setVisibility(TextView.GONE);
            sectionViewHolder.sectionDesc.setVisibility(TextView.GONE);

        } else {
            sectionViewHolder.sectionTitle.setText(sectionName);
            if (sectionDesc.isEmpty()){
                sectionViewHolder.sectionDesc.setVisibility(TextView.GONE);
            }else{
                sectionViewHolder.sectionDesc.setText(sectionDesc);
            }
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int section, int relativePosition, int absolutePosition) {

        ArrayList<String> itemsInSection = allData.get(section).getNameThItems();
        ArrayList<String> itemsNameEn = allData.get(section).getNameEnItems();
        ArrayList<String> itemsCredit = allData.get(section).getCreditItems();
        ArrayList<String> itemsCode = allData.get(section).getCodeItems();
        ArrayList<String> itemsId = allData.get(section).getIdItems();

        String itemNameTh = itemsInSection.get(relativePosition);
        String itemNameEN = itemsNameEn.get(relativePosition);
        String itemCode = itemsCode.get(relativePosition);
        String itemCredit = itemsCredit.get(relativePosition);
        String itemId = itemsId.get(relativePosition);

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        itemViewHolder.nameTh.setText(itemNameTh);
        itemViewHolder.nameEn.setText(itemNameEN);
        itemViewHolder.code.setText(itemCode);
        itemViewHolder.credit.setText(itemCredit);
        itemViewHolder.id.setText(itemId);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, boolean header) {
        View v = null;

        if (header) {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item_subjects_header, viewGroup, false);

            return new SectionViewHolder(v);
        } else {
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item_subjects_items, viewGroup, false);
            return new ItemViewHolder(v);
        }
    }

    // SectionViewHolder Class for Sections
    public static class SectionViewHolder extends RecyclerView.ViewHolder {

        protected TextView sectionTitle;
        protected TextView sectionDesc;
        protected LinearLayout rootLayout;

        public SectionViewHolder(View itemView) {
            super(itemView);
            this.sectionTitle = (TextView) itemView.findViewById(R.id.sectionTitle);
            this.sectionDesc = (TextView) itemView.findViewById(R.id.sectionDesc);
            this.rootLayout = (LinearLayout) itemView.findViewById(R.id.bg_header);
        }
    }

    // ItemViewHolder Class for Items in each Section
    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        protected TextView code;
        protected TextView nameTh;
        protected TextView nameEn;
        protected TextView credit;
        protected TextView id;
        protected LinearLayout rootLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.code = (TextView) itemView.findViewById(R.id.txt_subject_code);
            this.nameEn = (TextView) itemView.findViewById(R.id.txt_subject_name_en);
            this.nameTh = (TextView) itemView.findViewById(R.id.txt_subject_name_th);
            this.credit = (TextView) itemView.findViewById(R.id.txt_subject_credit);
            this.id = (TextView) itemView.findViewById(R.id.txt_subject_id);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), SubjectsDetailActivity.class);
                    intent.putExtra("id_subjects", id.getText());

                    Log.d(TAG, "onClick: " + id);
                    Log.d(TAG, "onClick: " + nameTh);
                    v.getContext().startActivity(intent);


                }
            });
        }
    }
}