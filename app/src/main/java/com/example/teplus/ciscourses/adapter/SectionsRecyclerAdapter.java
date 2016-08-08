package com.example.teplus.ciscourses.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teplus.ciscourses.R;
import com.example.teplus.ciscourses.item.SectionsItem;

import java.util.List;

public class SectionsRecyclerAdapter extends RecyclerView.Adapter<SectionsRecyclerAdapter.CustomViewHolder>{
    private List<SectionsItem> feedItemList;
    private Context mContext;

    public SectionsRecyclerAdapter(Context context, List<SectionsItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_sections, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        SectionsItem feedItem = feedItemList.get(i);

        //Setting text view title
        customViewHolder.nameTh.setText(Html.fromHtml(String.valueOf(feedItem.getNameTh())));

        if(Html.fromHtml(String.valueOf(feedItem.getDescTh())) == null){
            customViewHolder.descTh.setText("");
        }else{
            customViewHolder.descTh.setText(Html.fromHtml(String.valueOf(feedItem.getDescTh())));
        }

        customViewHolder.credit.setText(Html.fromHtml(String.valueOf(feedItem.getCredit())));
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView nameTh;
        protected TextView descTh;
        protected TextView credit;

        public CustomViewHolder(View view) {
            super(view);
            this.nameTh = (TextView) view.findViewById(R.id.txt_sections_name_th);
            this.descTh = (TextView) view.findViewById(R.id.txt_sections_desc_th);
            this.credit = (TextView) view.findViewById(R.id.txt_credit);

        }
    }
}