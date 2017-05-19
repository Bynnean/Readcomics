package com.bynnean.cartoon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.util.ScreenUtils;

import org.xutils.x;

import java.util.ArrayList;

/**
 * Author：Administrator on 2015/11/17 15:08
 * Email：709272013@qq.com
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> images;
    public ImageAdapter(Context context,ArrayList<String> images){
        this.context=context;
        this.images=images;

    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView= LayoutInflater.from(context).inflate(R.layout.recommend_list_image,null);
        ImageView iv= (ImageView) convertView.findViewById(R.id.recommend_list_iv_image);
        initImageViewParameters(iv);
        x.image().bind(iv,images.get(position));
        return convertView;
    }

    private void initImageViewParameters(ImageView view){
        int screenWidth = ScreenUtils.getScreenWidth(context);
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = screenWidth;
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        view.setLayoutParams(lp);

        view.setMaxWidth(screenWidth);
        view.setMaxHeight(screenWidth * 2);
    }
}
