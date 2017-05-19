package com.bynnean.cartoon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.bean.Querymore;

import org.xutils.x;

import java.util.ArrayList;
/**
 * Created by 李树华 on 2015/11/17.
 */
public class QuerymoreAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Querymore> list;
	public QuerymoreAdapter(Context context,ArrayList<Querymore> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder holder;
		if (convertView == null) {
			convertView  = LayoutInflater.from(context).inflate(R.layout.activity_querymore_item, null);
			holder = new ViewHolder();

			holder.cover_image_url = (ImageView) convertView.findViewById(R.id.image_url_querymore);
			holder.title = (TextView) convertView.findViewById(R.id.title_querymore_item);
			holder.nickname = (TextView) convertView.findViewById(R.id.nickname_querymore_item);
			holder.likes_count = (TextView) convertView.findViewById(R.id.likescount_querymore_item);
			holder.comments_count = (TextView) convertView.findViewById(R.id.commentscount_querymore_item);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.cover_image_url.setImageResource(R.drawable.ic_common_placeholder_ss);
		holder.title.setText(list.get(position).getTitle());
		holder.nickname.setText(list.get(position).getNickname());
		holder.comments_count.setText(list.get(position).getComments_count());
		holder.likes_count.setText(list.get(position).getLikes_count());
		x.image().bind(holder.cover_image_url,list.get(position).getCover_image_url());
		return convertView;
	}
	class ViewHolder{
		TextView comments_count,title,nickname,likes_count;
		ImageView cover_image_url;
	}
}