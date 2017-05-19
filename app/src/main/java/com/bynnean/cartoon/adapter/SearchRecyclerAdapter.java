package com.bynnean.cartoon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.bean.SearchBean;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Author：Administrator on 2015/11/16 10:16
 * Email：709272013@qq.com
 */
public class SearchRecyclerAdapter extends RecyclerView.Adapter <SearchRecyclerAdapter.ViewHolder>
implements View.OnClickListener{

    private static final String TAG = "SearchRecyclerAdapter";
    private ArrayList<SearchBean> items=new ArrayList<>();
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;


    private LayoutInflater inflater;



    public SearchRecyclerAdapter(ArrayList<SearchBean> items,Context context) {
        this.items = items;
        inflater=LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.read_search_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        //将创建的View注册点击事件
        v.setOnClickListener(this);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchBean item = items.get(position);
        holder.text.setText(item.title);
        x.image().bind(holder.image, item.icon,
                new ImageOptions.Builder()
                        .setRadius(360)

                        .build());
        holder.itemView.setTag(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (SearchBean) v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.read_search_iv_icon);
            text = (TextView) itemView.findViewById(R.id.read_search_tv_title);
        }
    }
    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , SearchBean data);
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
