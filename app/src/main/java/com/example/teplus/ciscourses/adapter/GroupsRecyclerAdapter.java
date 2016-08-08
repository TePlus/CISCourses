package com.example.teplus.ciscourses.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.teplus.ciscourses.R;
import com.example.teplus.ciscourses.item.GroupsItem;
import com.example.teplus.ciscourses.item.SectionsItem;

import java.util.List;

public class GroupsRecyclerAdapter extends RecyclerView.Adapter<GroupsRecyclerAdapter.CustomViewHolder> {
    private List<GroupsItem> feedItemList;
    private Context mContext;

    public GroupsRecyclerAdapter(Context context, List<GroupsItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_groups, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        GroupsItem feedItem = feedItemList.get(i);

        //Setting text view title
        customViewHolder.nameTh.setText(Html.fromHtml(feedItem.getNameTh()));
        customViewHolder.credit.setText(Html.fromHtml(String.valueOf(feedItem.getCredit())));
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView nameTh;
        protected TextView credit;

        public CustomViewHolder(View view) {
            super(view);
            this.nameTh = (TextView) view.findViewById(R.id.txtGroups);
            this.credit = (TextView) view.findViewById(R.id.txtCredit);
        }
    }
}