package com.bynnean.cartoon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.bean.TopicBean;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by 李树华 on 2015/11/16.
 */
public class ContentAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<TopicBean> list;
    public ContentAdapter(Context context, ArrayList<TopicBean> list) {
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
        ImageView iv = null;
        TextView tv = null;
        if(convertView==null){
            convertView=View.inflate(context,
                    R.layout.activity_content_item, null);


        }
        iv= (ImageView) convertView.findViewById(R.id.content_iv_url);
        tv= (TextView) convertView.findViewById(R.id.content_title);
        x.image().bind(iv,list.get(position).cover_image_url);
        tv.setText(list.get(position).title);
        return convertView;
    }

}