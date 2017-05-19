package com.bynnean.cartoon.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.ui.SearchActivity;

import java.util.ArrayList;


public class ReadFragment extends Fragment {
    private TextView mTvRecommend;
//    private TextView mTvAtention;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private int offset;
    private Matrix matrix;
    private ArrayList<TextView> textViewList;
    private ArrayList<Fragment> mFragmentsList;
//    private ImageView iv_line;
    private int cursorWidth;

    //搜索按钮
    private ImageView mSearch;

    public ReadFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_read, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //初始化标题视图
        initTitleView(view);

        mSearch= (ImageView) view.findViewById(R.id.frag_iv_search);

        //初始化ViewPager
        initViewPager(view);
        viewPager.setCurrentItem(1);

        //设置textView的监听事件
        setListener();

        //设置搜索按钮的监听事件
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(),SearchActivity.class));

            }
        });
    }

    private void setListener() {

        for (int i = 0; i < textViewList.size(); i++) {
            textViewList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.frag_tv_recommend:
                            viewPager.setCurrentItem(0);

                            break;
//                        case R.id.frag_tv_attention:
//                            viewPager.setCurrentItem(1);
//                            break;
                    }

                }
            });
        }
    }

    private void initViewPager(View view) {
        viewPager = (ViewPager)view.findViewById(R.id.frag_vp_show);

        mFragmentsList = new ArrayList<>();
        final Fragment recommendFragment = new RecommendFragment();
//

//        final Fragment attentionFragment = new AttentionFragment();

//        mFragmentsList.add(attentionFragment);
        mFragmentsList.add(recommendFragment);

        //define the FragmentPagerAdapter
        adapter = new FragmentStatePagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                return mFragmentsList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentsList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {

                return "Page " + position;
            }


        };
        viewPager.setAdapter(adapter);//set the adapter


//        viewPager.setCurrentItem(1);//set the first one
        //the onClick event of the viewPager
//        viewPager.addOnPageChangeListener(new ReadPagerListener(offset, cursorWidth, iv_line, textViewList, getActivity()));
    }


    private void initTitleView(View view) {
        mTvRecommend = (TextView) view.findViewById(R.id.frag_tv_recommend);
//        mTvAtention = (TextView) view.findViewById(R.id.frag_tv_attention);
        //add the all titel to the List
        textViewList = new ArrayList<>();

        textViewList.add(mTvRecommend);
//        textViewList.add(mTvAtention);
        textViewList.get(0).setTextColor(Color.BLACK);


    }
}
