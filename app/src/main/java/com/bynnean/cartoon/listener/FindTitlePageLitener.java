package com.bynnean.cartoon.listener;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.bynnean.cartoon.bean.Banner;

import java.util.ArrayList;

/**
 * Author：Administrator on 2015/11/17 10:34
 * Email：709272013@qq.com
 */
public class FindTitlePageLitener implements ViewPager.OnPageChangeListener {
    private int oldPosition=0;
    private ArrayList<Banner> titleDatas;//the title text
    private int currentItem;//the current item
    private ArrayList<ImageView> dots=new ArrayList<>();//the image dots List
    public FindTitlePageLitener(int currentItem,
                              ArrayList<Banner> titleDatas,ArrayList<ImageView> dots){
        this.currentItem=currentItem;
        this.titleDatas=titleDatas;
        this.dots=dots;

    }


    /**
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * when the page is selected ,the method was used
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        currentItem = position;

        //改变圆点(焦点)
        dots.get(oldPosition).setEnabled(true);
        dots.get(position).setEnabled(false);

        oldPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
