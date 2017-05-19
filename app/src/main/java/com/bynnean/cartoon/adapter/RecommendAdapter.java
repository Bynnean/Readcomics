package com.bynnean.cartoon.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.pay.ui.PayDemoActivity;
import com.bhsoft.android.pay.MiGuPay;
import com.bynnean.cartoon.R;
import com.bynnean.cartoon.bean.ComicsBean;
import com.bynnean.cartoon.ui.LoginActivity;
import com.bynnean.cartoon.ui.RecommendItemActivity;
import com.bynnean.cartoon.ui.RecommendTopicActivity;
import com.bynnean.cartoon.util.PayUtils;
import com.bynnean.cartoon.view.ActionSheet;
import com.bynnean.cartoon.view.MiGuPayActivity;
import com.bynnean.cartoon.view.OrderDialog;

import org.xutils.x;

import java.util.ArrayList;


public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private static final String TAG = "SearchRecyclerAdapter";
    private ArrayList<ComicsBean> items = new ArrayList<>();

    private LayoutInflater inflater;

    private Context mContext;


    public RecommendAdapter(ArrayList<ComicsBean> items, Context context) {
        this.mContext = context;
        this.items = items;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.recommend_item, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ComicsBean item = items.get(position);


//        holder.text_count.setText(item.likes_count);


        if (item.topicBean.user.nickname.contains("快看") || item.topicBean.user.nickname.contains("ManMan")) {
            item.topicBean.user.nickname = "ManMan";
            item.topicBean.title = "ManMan" + "漫画";
            item.title = item.title.replace("快看", "ManMan");
            holder.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher));
        } else x.image().bind(holder.image, item.topicBean.user.avatar_url);
        holder.text_author.setText(item.topicBean.user.nickname);
//        holder.text_common.setText(item.comments_count);
//        holder.text_share.setText(item.shared_count);
        holder.text_title.setText(item.topicBean.title);
        x.image().bind(holder.image_cover, item.cover_image_url);
        holder.comic_title.setText(item.title);


        //点击事件
        holder.image_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PayUtils.getLoginState()) {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    return;
                }
                if (PayUtils.isNeedPayOrder(item.id)) {
                    OrderDialog.Builder builder = new OrderDialog.Builder((Activity) mContext);
                    builder.setPositiveButton(new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ActionSheet actionSheet = new ActionSheet(mContext)
                                    .builder()
                                    .setCancelable(false)
                                    .setCanceledOnTouchOutside(false)
                                    .setTitle("选择支付方式")
                                    .addSheetItem("支付宝", ActionSheet.SheetItemColor.Blue, new ActionSheet.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    Intent intent = new Intent(mContext, PayDemoActivity.class);
                                                    intent.putExtra("itemId", item.id);
                                                    intent.putExtra("username", item.topicBean.user.nickname);
                                                    intent.putExtra("data_title", item.topicBean.title);
                                                    intent.putExtra("topic_title", item.title);
                                                    intent.putExtra("vertical_image_url", item.topicBean.vertical_image_url);
                                                    intent.putExtra("pay", "" + (OrderDialog.index + 1));
                                                    mContext.startActivity(intent);
                                                }
                                            }
                                    )
                                    .addSheetItem("咪咕", ActionSheet.SheetItemColor.Blue,
                                            new ActionSheet.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    MiGuPayActivity.url = MiGuPay.getPayUrl(which - 1);
                                                    mContext.startActivity(new Intent(mContext, MiGuPayActivity.class));
                                                }
                                            });
                            actionSheet.show();


//                                Intent intent = new Intent(mContext, PayDemoActivity.class);
//                                intent.putExtra("itemId", item.id);
//                                intent.putExtra("username", item.topicBean.user.nickname);
//                                intent.putExtra("data_title", item.topicBean.title);
//                                intent.putExtra("topic_title", item.title);
//                                intent.putExtra("vertical_image_url", item.topicBean.vertical_image_url);
//                                intent.putExtra("pay", "" + (OrderDialog.index + 1));
//                                mContext.startActivity(intent);


//                        mContext.startActivity(new Intent(mContext, PayDemoActivity.class));
                            // 设置你的操作事项
//
                        }
                    });

                    builder.setNegativeButton(new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.create("", "", "").show();
                } else {
                    Intent intent = new Intent(mContext, RecommendItemActivity.class);
                    intent.putExtra("itemId", item.id);
                    intent.putExtra("username", item.topicBean.user.nickname);
                    intent.putExtra("data_title", item.topicBean.title);
                    intent.putExtra("topic_title", item.title);
                    intent.putExtra("vertical_image_url", item.topicBean.vertical_image_url);
                    mContext.startActivity(intent);
                }

            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(mContext,RecommendItemUserActivity.class);
//                intent.putExtra("userid",item.topicBean.user.id);
//
//                mContext.startActivity(intent);
            }
        });

        holder.itemView.setTag(items.get(position));

        holder.text_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecommendTopicActivity.class);
                intent.putExtra("topicId", item.topicBean.id);
                intent.putExtra("comments_count", item.comments_count);
                intent.putExtra("userName", item.topicBean.user.nickname);
                intent.putExtra("avatar_url", item.topicBean.user.avatar_url);
                intent.putExtra("description", item.topicBean.description);

                mContext.startActivity(intent);
            }
        });


//        //分享
//        holder.text_share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //调用系统分享 通用版
//                Intent intentsh = new Intent(Intent.ACTION_SEND);
//                intentsh.setType("image/*");
//                intentsh.putExtra(Intent.EXTRA_SUBJECT, "Share");
//                intentsh.putExtra(Intent.EXTRA_TEXT, "好像，这个是可行的哦");
//                intentsh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                RecommendFragment.activity.startActivity(Intent.createChooser(intentsh, "分享到---"));
//            }
//        });
        //点赞
//        holder.comic_like_ic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int i=Integer.parseInt(holder.text_count.getText().toString());
//                i+=1;
//                holder.text_count.setText(""+i);
//                holder.comic_like_ic.setSelected(true);
//                holder.comic_like_ic.setClickable(false);//设置不可载点击
//            }
//        });

    }

    @Override
    public int getItemCount() {

        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public ImageView image_cover;
        public TextView text_title;
        public TextView comic_title;
        public TextView text_author;
        //        public TextView text_share;
//        public TextView text_common;
//        public TextView text_count;
        public LinearLayout linearLayout;
//        public ImageView comic_like_ic;


        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.comic_author_avatar);
            text_title = (TextView) itemView.findViewById(R.id.topic_title);//专题旁边的
            text_author = (TextView) itemView.findViewById(R.id.comic_author_name);
            image_cover = (ImageView) itemView.findViewById(R.id.cover_image);//大图片
            comic_title = (TextView) itemView.findViewById(R.id.comic_title);
//            text_share = (TextView) itemView.findViewById(R.id.comic_list_share_tv);//分享
//            text_common = (TextView) itemView.findViewById(R.id.comic_comment_count);
//            text_count = (TextView) itemView.findViewById(R.id.comic_likes_count);
//            comic_like_ic=(ImageView)itemView.findViewById(R.id.comic_like_ic);//点赞
//            comic_like_ic.setSelected(false);
        }
    }

}
