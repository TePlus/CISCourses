package com.example.teplus.ciscourses.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teplus.ciscourses.R;
import com.example.teplus.ciscourses.item.CoursesItem;

import java.util.List;

public class CoursesRecyclerAdapter extends RecyclerView.Adapter<CoursesRecyclerAdapter.CustomViewHolder>{
    private List<CoursesItem> feedItemList;
    private Context mContext;
    private String course_name;
    private ImageView imageView;

    public CoursesRecyclerAdapter(Context context, List<CoursesItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_courses, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {

        CoursesItem feedItem = feedItemList.get(i);
        customViewHolder.nameTh.setText(Html.fromHtml(feedItem.getNameTh()));

        course_name = String.valueOf(Html.fromHtml(feedItem.getNameTh()));

        if(course_name.matches("วิทยาการคอมพิวเตอร์")){
            imageView.setImageResource(R.drawable.cs);
        }else if(course_name.matches("เทคโนโลยีสารสนเทศ")){
            imageView.setImageResource(R.drawable.it);
        }else{
          //  imageView.setImageResource(R.drawable.default_photo);
        }

        customViewHolder.desc.setText(feedItem.getDescTh().concat(String.valueOf(feedItem.getYear())));
     //   customViewHolder.credit.setText("จำนวนหน่วยกิตรวม ไม่น้อยกว่า " + feedItem.getCredit() + " หน่วยกิต");
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        protected TextView nameTh;
        protected TextView desc;
        protected TextView credit;

        public CustomViewHolder(View view) {
            super(view);

            this.nameTh = (TextView) view.findViewById(R.id.txt_courses);
            this.desc = (TextView) view.findViewById(R.id.txt_desc);
            imageView = (ImageView) view.findViewById(R.id.ivImg);
            this.credit = (TextView) view.findViewById(R.id.txt_credit);
        }
    }
}