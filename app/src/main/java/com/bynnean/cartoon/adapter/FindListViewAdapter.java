package com.bynnean.cartoon.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.bean.FindTopic;
import com.bynnean.cartoon.bean.FindTopicList;
import com.bynnean.cartoon.fragment.FindFragment;
import com.bynnean.cartoon.ui.QueryMoreActivity;

import java.util.ArrayList;


public class FindListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FindTopicList> list;
    private ArrayList<FindTopic> dataList;
    public FindListViewAdapter(Context context,ArrayList<FindTopicList> list) {
        this.context = context;
        this.list =list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(context,
                    R.layout.find_list_item, null);

        }

        GridView gridView= (GridView) convertView.findViewById(R.id.grid);
        final TextView tvTitle=(TextView)convertView.findViewById(R.id.tv_title_item);

        dataList=list.get(position).getList();
        tvTitle.setText(list.get(position).getTopics_title());
        int size = dataList.size();

        DisplayMetrics dm = new DisplayMetrics();
        FindFragment.activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int allWidth = (int) (153 * size*density);
        int itemWidth = (int) (150 * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                allWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params);
        gridView.setColumnWidth(itemWidth);
        gridView.setHorizontalSpacing(10);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size);
        GridViewAdapter adapter = new GridViewAdapter(context,dataList);
        gridView.setAdapter(adapter);

        TextView tvMore=(TextView)convertView.findViewById(R.id.tv_more);
        tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("action",list.get(position).getAction());
                intent.putExtra("title",tvTitle.getText().toString());
                intent.setClass(context, QueryMoreActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}