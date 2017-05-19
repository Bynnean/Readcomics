package com.bynnean.cartoon.listener;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ReadPagerListener implements ViewPager.OnPageChangeListener{


    private int currentIndex = 0;//the current index
    private int offset;//the animation offset
    private int cursorWidth;//the  cursor width
    private ImageView imageView; //
    private List<TextView> textViewList;
    private Context context;

    public ReadPagerListener(int offset, int cursorWidth, ImageView imageView, List<TextView> textViewList, Context context) {
        this.offset = offset;
        this.cursorWidth = cursorWidth;
        this.imageView = imageView;
        this.textViewList = textViewList;
        this.context = context;
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {


        //设置标题的颜色
        for (int i = 0; i < textViewList.size(); i++) {
            textViewList.get(i).setTextColor(Color.BLACK);

        }
        textViewList.get(position).setTextColor(Color.rgb(0xAD,0xAD,0xAD));


        int len = 2 * offset + cursorWidth;//the distance from current to next

        TranslateAnimation animation = null;
        switch (position) {

            case 0:
                if (currentIndex == 1) {
                    animation = new TranslateAnimation(len, 0, 0, 0);

                }
            case 1:
                if (currentIndex == 0) {
                    animation = new TranslateAnimation(0, len, 0, 0);

                }
                break;


        }
        currentIndex = position;
        animation.setDuration(30);//set the time of animation
        //fillAfter true if the animation should apply its transformation after it ends
        animation.setFillAfter(true);
        imageView.startAnimation(animation);//start the animation


    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager#SCROLL_STATE_IDLE
     * @see ViewPager#SCROLL_STATE_DRAGGING
     * @see ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {


    }

}
