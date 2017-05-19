package com.bynnean.cartoon.ui;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.bynnean.cartoon.R;
import com.bynnean.cartoon.fragment.ContenFragment;
import com.bynnean.cartoon.listener.TopicPagerListener;
import com.bynnean.cartoon.service.ReadSearchService;
import com.bynnean.cartoon.bean.TopicBean;
import com.bynnean.cartoon.fragment.InfoFragment;

import org.xutils.x;

import java.util.ArrayList;

public class RecommendTopicActivity extends AppCompatActivity {
    private Messenger messenger;
    private boolean bound = false;
    private StartHandler mHandler;
    private Messenger handlerMessenger;
    public StartServiceConnection serviceConnection = new StartServiceConnection();
    private ArrayList<TopicBean> datas;
    private String topicId = "";
    private ProgressDialog mDialog;
    private ObservableScrollView scrollView;
    private TextView mTVLikeCount, mTVCommonCount, mTVName;
    private ImageView mImage;
    private String comments_count;
    private ViewPager mViewPager;
    private View inflate;
    private TextView tv_content;
    private TextView tv_profile;
    private ArrayList<Fragment> mFragments;
    private ImageView iv_line;
    private int cursorWidth = 0;
    private int offset = 0;
    private Matrix matrix;
    private ArrayList<TextView> textViewList;
    private ContenFragment fragment1;
    private InfoFragment fragment2;
    private String userName = "";
    private String avatar_url = "";
    private String description = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_topic);
        Intent intent = getIntent();
        topicId = intent.getStringExtra("topicId");
        comments_count = intent.getStringExtra("comments_count");
        userName = intent.getStringExtra("userName");
        avatar_url = intent.getStringExtra("avatar_url");
        description = intent.getStringExtra("description");

        init();//初始化组件


        //初始化标题视图
        initTitleView();

        //初始化平移动画
        initAnimation();

        //初始化ViewPager
        initViewPager();
        mViewPager.setCurrentItem(1);

        //设置textView的监听事件
        setListener();
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("正在加载中....");
        mDialog.show();


    }

    private void setListener() {
        for (int i = 0; i < textViewList.size(); i++) {
            textViewList.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.tab_comic_list:
                            mViewPager.setCurrentItem(0);

                            break;
                        case R.id.tab_info:
                            mViewPager.setCurrentItem(1);
                            break;
                    }

                }
            });
        }
    }

    private void initViewPager() {


        mFragments = new ArrayList<>();

        //内容
        fragment1 = new ContenFragment();

        Bundle bundle = new Bundle();
        bundle.putString("content", topicId);
        fragment1.setArguments(bundle);
        //简介
        fragment2 = new InfoFragment();

        Bundle bundle1 = new Bundle();

//        bundle1.putString("userName1", userName);
//        bundle1.putString("avatar_url1", avatar_url);
        bundle1.putString("topicId", topicId);

        fragment2.setArguments(bundle1);
        mFragments.add(fragment2);
        mFragments.add(fragment1);

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

            @Override
            public CharSequence getPageTitle(int position) {

                return "Page " + position;
            }
        });

        mViewPager.addOnPageChangeListener(new TopicPagerListener(offset, cursorWidth, iv_line,
                textViewList, RecommendTopicActivity.this));
        mViewPager.setOffscreenPageLimit(2);

    }

    private void initAnimation() {
        iv_line = (ImageView) findViewById(R.id.topic_line);


        //get the screen width
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;

        //Dynamic setting image length
        ViewGroup.LayoutParams iv = iv_line.getLayoutParams();
        iv.width = widthPixels / 2;
        iv_line.setLayoutParams(iv);


        //get the cursor width
        cursorWidth = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();

        //calculate the offset of the animation
        offset = (widthPixels / 2 - cursorWidth) / 2;
        matrix = new Matrix();
        //postconcats the matrix with the specified translation.
        matrix.postTranslate(offset, 0);

        iv_line.setImageMatrix(matrix);
    }

    private void initTitleView() {
        tv_content = (TextView) findViewById(R.id.tab_comic_list);
        tv_profile = (TextView) findViewById(R.id.tab_info);
        textViewList = new ArrayList<>();

        textViewList.add(tv_content);
        textViewList.add(tv_profile);

    }

    /**
     * 初始化组件
     */
    private void init() {
        inflate = LayoutInflater.from(RecommendTopicActivity.this).
                inflate(R.layout.activity_recommend_topic, null);
        scrollView = (ObservableScrollView) findViewById(R.id.container);
        mTVLikeCount = (TextView) findViewById(R.id.topic_detail_header_like_count);
        mTVCommonCount = (TextView) findViewById(R.id.topic_detail_header_comment_count);
        mImage = (ImageView) findViewById(R.id.image);
        mTVName = (TextView) findViewById(R.id.topic_detail_header_topic_name);
        mViewPager = (ViewPager) findViewById(R.id.topic_detail_viewpager);


    }

    /**
     * 服务的连接
     */
    public class StartServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            bound = true;
            handlerMessenger = new Messenger(mHandler);
            Message msg = Message.obtain(null, ReadSearchService.MSG_WHAT_RECOMMEND_ITEM_Topic, 0, 0);

            Bundle bundle = new Bundle();
            bundle.putString("topicId", topicId);
            msg.setData(bundle);
            msg.replyTo = handlerMessenger;
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
            bound = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHandler = new StartHandler();

        if (!bound) {
            Intent intent = new Intent(this,ReadSearchService.class);
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        }


    }

    class StartHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case ReadSearchService.RESULT_OK:


                    Bundle bundle = msg.getData();
                    ArrayList<TopicBean> readsearchdata =
                            bundle.getParcelableArrayList("recommonddataItemTopic");
                    if (datas == null) {
                        datas = new ArrayList<>();
                    }
                    datas.addAll(readsearchdata);
                    mDialog.dismiss();
                    //给各个组件设置内容
                    setContent();


                    break;
            }

            super.handleMessage(msg);
        }
    }

    /**
     * 给组件设置内容
     */
    private void setContent() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        for (int i = 0; i < datas.size(); i++) {
            x.image().bind(mImage, datas.get(i).cover_image_url);
//            mTVName.setText(datas.get(i).title);

            mTVLikeCount.setText(datas.get(i).comics_count);
        }
        mTVCommonCount.setText(comments_count);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
