package com.bynnean.cartoon.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.bean.FindTopic;
import com.bynnean.cartoon.ui.RecommendTopicActivity;

import org.xutils.x;

import java.util.List;

/**
 * Created by 李树华 on 2015/11/16.
 */
public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<FindTopic> dataList;
    private LayoutInflater inflater;

    public GridViewAdapter(Context context,List<FindTopic> dataList) {
        this.context=context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.gridview_item, null);
            holder = new ViewHolder();

            holder.title = (TextView) convertView.findViewById(R.id.tv_title_gridview_item);
            holder.nickname = (TextView) convertView.findViewById(R.id.tv_nickname_gridview_item);
            holder.iamgePath = (ImageView) convertView.findViewById(R.id.iv_vertical_gridview_item);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        x.image().bind(holder.iamgePath,dataList.get(position).getImagePath());

        holder.nickname.setText(dataList.get(position).getNickname());
        holder.title.setText(dataList.get(position).getTitle());

        holder.iamgePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lishuhua", "------" +dataList.get(position).getId());
                Intent intent = new Intent();
                intent.putExtra("topicId",dataList.get(position).getId());
                intent.setClass(context, RecommendTopicActivity.class);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView iamgePath;
        TextView title, nickname;
    }
}
