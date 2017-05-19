package com.bynnean.cartoon.fragment;


import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.adapter.FindTitlePagerAdapter;
import com.bynnean.cartoon.bean.Banner;
import com.bynnean.cartoon.bean.FindTopicList;
import com.bynnean.cartoon.listener.FindTitlePageLitener;
import com.bynnean.cartoon.service.ReadSearchService;
import com.bynnean.cartoon.ui.MainActivity;
import com.bynnean.cartoon.ui.SearchActivity;
import com.bynnean.cartoon.adapter.FindListViewAdapter;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FindFragment extends Fragment {


    private ViewPager mViewPager;
    private View mView;
    private ImageView ivDot1,ivDot2,ivDot3,ivDot4,ivDot5;//原点图片
    private ArrayList<ImageView> imageViewList;
    private ListView mListView;
    private FindListViewAdapter mListAdapter;
    private int currentItem = 0; // the index of the current image
    private ScheduledExecutorService scheduledExecutorService;//Time periodic execution of assigned tasks

    private boolean bound = false;
    private Messenger messenger;
    private StartHandler mHandler;
    private Messenger handlerMessenger;
    public  StartServiceConnection serviceConnection = new StartServiceConnection();
    private  ArrayList<FindTopicList> topicsList;
    public static MainActivity activity;
    private ArrayList<Banner> banners;

    // 切换当前显示的图片
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mViewPager.setCurrentItem(currentItem);// 切换当前显示的图片
        }
    };
    private FindTitlePagerAdapter mPagerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity= (MainActivity) getActivity();

        ImageView search=(ImageView)view.findViewById(R.id.search_find);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });


        mListView = (ListView) view.findViewById(R.id.listview_find);
        mView = LayoutInflater.from(getActivity()).inflate(R.layout.find_banner, null);
        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager_banner);


        //初始化圆点
        initDot();
        //初始化公告的每一页
        initPage();
        if (topicsList==null){
            topicsList=new ArrayList<>();
        }
        if (mListAdapter==null){
            mListAdapter=new FindListViewAdapter(getContext(),topicsList);
        }
        mListView.addHeaderView(mView);
        mListView.setAdapter(mListAdapter);
    }

    /**
     * 初始化圆点
     * by 李树华
     */
    private void initDot() {
        ivDot1=(ImageView)mView.findViewById(R.id.iv_dot1_banner);
        ivDot2=(ImageView)mView.findViewById(R.id.iv_dot2_banner);
        ivDot3=(ImageView)mView.findViewById(R.id.iv_dot3_banner);
        ivDot4=(ImageView)mView.findViewById(R.id.iv_dot4_banner);
        ivDot5=(ImageView)mView.findViewById(R.id.iv_dot5_banner);

        imageViewList = new ArrayList<ImageView>();
        imageViewList.add(ivDot1);
        imageViewList.add(ivDot2);
        imageViewList.add(ivDot3);
        imageViewList.add(ivDot4);
        imageViewList.add(ivDot5);
        imageViewList.get(0).setEnabled(false);
    }
    /**
     * 初始化page
     * by 李树华
     */
    private void initPage() {
        if (banners==null){
            banners=new ArrayList<>();
        }
        if (mPagerAdapter==null){

         mPagerAdapter = new FindTitlePagerAdapter(getActivity(), banners);
        }
        Log.d("banne", "" + banners.size());
        //绑定适配器
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(0);
        //设置滑动监听器
        setListener();
    }


    /**
     * viewpager滑动的监听事件
     * by 李树华
     */
    private void setListener() {
        mViewPager.addOnPageChangeListener(
                new FindTitlePageLitener(currentItem, banners, imageViewList));
    }



    @Override
    public void onStop() {
        //停止图片切换
        scheduledExecutorService.shutdown();

        super.onStop();
    }

    public class StartServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("aaaaaaaaa","111111111111111111111");
            messenger = new Messenger(service);
            bound = true;
            handlerMessenger = new Messenger(mHandler);
            Message msg = Message.obtain(null, ReadSearchService.MSG_WHAT_FIND, 0, 0);
            Message msg_banner = Message.obtain(null, ReadSearchService.MSG_WHAT_BANNER, 0, 0);
            msg.replyTo = handlerMessenger;
            msg_banner.replyTo=handlerMessenger;
            try {
                messenger.send(msg);
                Log.d("messenger", "messenger");

                messenger.send(msg_banner);
                Log.d("msg_banner", "msg_banner");

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
            bound = false;
            Log.d("aa","时报");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mHandler=new StartHandler();

        if (!bound){
            Intent intent=new Intent(getActivity(),ReadSearchService.class);
            getActivity().bindService(intent, serviceConnection, getActivity().BIND_AUTO_CREATE);
        }
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //通过定时器 来完成 每2秒钟切换一个图片
        //经过指定的时间后，执行所指定的任务
        //scheduleAtFixedRate(command, initialDelay, period, unit)
        //command 所要执行的任务
        //initialDelay 第一次启动时 延迟启动时间
        //period  每间隔多次时间来重新启动任务
        //unit 时间单位
        scheduledExecutorService.scheduleAtFixedRate(new ViewPagerTask(), 3, 3, TimeUnit.SECONDS);

    }

    class StartHandler extends Handler {
        @Override
        public void handleMessage(Message msg){

            switch (msg.what){
                case ReadSearchService.RESULT_OK:
                    Bundle bundle=msg.getData();
                    ArrayList<FindTopicList> list =bundle.getParcelableArrayList("list");

                    if (topicsList==null){
                        topicsList=new ArrayList<FindTopicList>();
                    }
                    topicsList.addAll(list);
                    break;
                case ReadSearchService.RESULT_O:
                    Log.d("RESULT_O","RESULT_O");
                    Bundle bundle1=msg.getData();
                    ArrayList<Banner> imageList = bundle1.getParcelableArrayList("imageList");
                    Log.d("imageList",":"+imageList.size());
                    if (banners==null){
                        banners=new ArrayList<Banner>();
                    }
                    banners.addAll(imageList);
                    mPagerAdapter.notifyDataSetChanged();
                    Log.d("imageList+banners", ":" + banners.size());


            }

            super.handleMessage(msg);
        }
    }

    //用来完成图片切换的任务
    private class ViewPagerTask implements Runnable{

        public void run() {
            //实现我们的操作
            //改变当前页面
            currentItem = (currentItem + 1) % imageViewList.size();
            //Handler来实现图片切换
            handler.obtainMessage().sendToTarget();
        }
    }

}