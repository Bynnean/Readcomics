package com.bynnean.cartoon.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.fragment.RecommendFragment;
import com.bynnean.cartoon.fragment.FindFragment;
import com.bynnean.cartoon.fragment.IndividualFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments;
    private RadioButton[] mRadioButtons;
    private LinearLayout tuijianLayout,searchLayout,mineLayout;
    private List<LinearLayout> layoutList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.main_vp_show);
        tuijianLayout = (LinearLayout)findViewById(R.id.tuijian);
        tuijianLayout.setOnClickListener(this);
        searchLayout = (LinearLayout)findViewById(R.id.search);
        searchLayout.setOnClickListener(this);
        mineLayout = (LinearLayout)findViewById(R.id.mine);
        mineLayout.setOnClickListener(this);
        addLayoutItems();
        //初始化标题
        initTitles();
        //对viewPager进行适配数据
        initViewPager();
        setSelcted(tuijianLayout);
    }

    private void addLayoutItems(){
        layoutList = new ArrayList<>();
        layoutList.add(tuijianLayout);
        layoutList.add(searchLayout);
        layoutList.add(mineLayout);
    }

    private void initViewPager() {

        mFragments = new ArrayList<Fragment>();

        Fragment fragment1 = new RecommendFragment();

        //发现
        Fragment fragment2 = new FindFragment();

        //个人
        Fragment fragment3 = new IndividualFragment();

        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);

        //设置适配器
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelcted(layoutList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOffscreenPageLimit(3);
    }

    /**
     * 初始化标题
     */
    private void initTitles() {
       for(LinearLayout layout : layoutList){
           for(int i = 0; i<layout.getChildCount();i++)
               layout.getChildAt(i).setSelected(false);
       }
    }

    private void setSelcted(LinearLayout layout){
        initTitles();
        for(int i= 0 ;i<layout.getChildCount();i++){
            layout.getChildAt(i).setSelected(true);
        }
    }

    @Override
    public void onClick(View view) {
        if(view instanceof  LinearLayout){
            setSelcted((LinearLayout)view);
            String idS = view.getTag().toString();
            mViewPager.setCurrentItem(Integer.parseInt(idS));
        }
    }
}