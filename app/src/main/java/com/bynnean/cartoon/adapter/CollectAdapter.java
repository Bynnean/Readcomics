package com.bynnean.cartoon.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.bean.Collect;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by 李树华 on 2015/11/16.
 */
public class CollectAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Collect> dataList;

    public CollectAdapter(Context context,ArrayList<Collect> dataList) {
        this.context=context;
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
            convertView =LayoutInflater.from(context).
                    inflate(R.layout.item_collection, null);
            holder = new ViewHolder();

            holder.collection_topic_title = (TextView) convertView.findViewById(R.id.collection_topic_title);
            holder.collection_author = (TextView) convertView.findViewById(R.id.collection_author);
            holder.collection_data_title = (TextView) convertView.findViewById(R.id.collection_data_title);
            holder.iamgePath = (ImageView) convertView.findViewById(R.id.collection_image);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.d("aaaaaaaaaa","aaaaaaaa"+dataList.get(position).getVertical_image_url());
        x.image().bind(holder.iamgePath,dataList.get(position).getVertical_image_url());
        holder.collection_topic_title.setText(dataList.get(position).getTopic_title());
        holder.collection_author.setText(dataList.get(position).getNickname());
        holder.collection_data_title.setText(dataList.get(position).getData_title());

        return convertView;
    }

    class ViewHolder {
        ImageView iamgePath;
        TextView collection_topic_title, collection_author,collection_data_title;
    }
}
