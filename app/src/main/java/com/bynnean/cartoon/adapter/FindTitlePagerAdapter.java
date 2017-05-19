package com.bynnean.cartoon.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alipay.sdk.pay.ui.PayDemoActivity;
import com.bhsoft.android.pay.MiGuPay;
import com.bynnean.cartoon.bean.Banner;
import com.bynnean.cartoon.ui.BannerWebViewActivity;
import com.bynnean.cartoon.ui.LoginActivity;
import com.bynnean.cartoon.ui.RecommendItemActivity;
import com.bynnean.cartoon.util.PayUtils;
import com.bynnean.cartoon.view.ActionSheet;
import com.bynnean.cartoon.view.MiGuPayActivity;
import com.bynnean.cartoon.view.OrderDialog;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author：Administrator on 2015/11/17 10:25
 * Email：709272013@qq.com
 */
public class FindTitlePagerAdapter extends PagerAdapter implements ActionSheet.OnSheetItemClickListener {
    private static final String TAG = "FindTitlePagerAdapter";
    private List<ImageView> imageViews; // 滑动的图片集合
    private ArrayList<Banner> list = new ArrayList<>();
    private Context context;
    ImageView imageView;

    public FindTitlePagerAdapter(Context context, ArrayList<Banner> list) {
        this.list = list;
        this.context = context;
        imageViews = new ArrayList<>();
    }


    @Override
    public int getCount() {
        if (list == null) {
            Log.d(TAG, "addData:list is null");
            return 0;
        } else {
            return list.size();
        }
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //下载图片并适配上去
        x.image().bind(imageView, list.get(position).getPic());

        imageViews.add(imageView);
        container.addView(imageViews.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = list.get(position).getValue().toString();

                if (isInt(value)==true) {
                    if(!PayUtils.getLoginState()){
                        context.startActivity(new Intent(context, LoginActivity.class));
                        return;
                    }
                    if(PayUtils.isNeedPayOrder(list.get(position).getValue())){
                    OrderDialog.Builder builder = new OrderDialog.Builder((Activity)context);
                    builder.setPositiveButton(new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();

                            ActionSheet actionSheet = new ActionSheet(context)
                                    .builder()
                                    .setCancelable(false)
                                    .setCanceledOnTouchOutside(false)
                                    .setTitle("选择支付方式")
                                    .addSheetItem("支付宝", ActionSheet.SheetItemColor.Blue, new ActionSheet.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    Intent intent = new Intent(context, PayDemoActivity.class);
                                                    intent.putExtra("itemId", list.get(position).getValue());
                                                    intent.putExtra("data_title",list.get(position).getTitle());
                                                    intent.putExtra("topic_title",list.get(position).getTitle());
                                                    intent.putExtra("vertical_image_url",list.get(position).getPic());
                                                    intent.putExtra("pay", ""+(OrderDialog.index + 1));
                                                    context.startActivity(intent);
                                                }
                                            }
                                    )
                                    .addSheetItem("咪咕", ActionSheet.SheetItemColor.Blue,
                                            new ActionSheet.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                 MiGuPayActivity.url = MiGuPay.getPayUrl(which - 1);
                                                 context.startActivity(new Intent(context,MiGuPayActivity.class));
                                                }
                                            });
                            actionSheet.show();





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
                    }else {
                        Intent intent = new Intent();
                        intent.putExtra("itemId", list.get(position).getValue());
                        intent.setClass(context, RecommendItemActivity.class);
                        context.startActivity(intent);
                    }
//
//
//                    if(PayUtils.isNeedPayOrder(list.get(position).getValue())){
//                        Intent intent = new Intent();
//                        intent.putExtra("itemId", list.get(position).getValue());
//                        intent.setClass(context, PayDemoActivity.class);
//                        context.startActivity(intent);
//
//                    }else {
//                        Intent intent = new Intent();
//                        intent.putExtra("itemId", list.get(position).getValue());
//                        intent.setClass(context, RecommendItemActivity.class);
//                        context.startActivity(intent);
//                    }
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("url", list.get(position).getValue());
                    intent.setClass(context, BannerWebViewActivity.class);
                    context.startActivity(intent);
                }
            }
        });

        return imageViews.get(position);
    }

    public static boolean isInt(String value) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(value);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViews.get(position));
    }

    @Override
    public void onClick(int which) {

    }
}
