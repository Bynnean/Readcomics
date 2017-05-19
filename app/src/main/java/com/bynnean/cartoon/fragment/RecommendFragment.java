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
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.adapter.RecommendAdapter;
import com.bynnean.cartoon.bean.ComicsBean;
import com.bynnean.cartoon.service.ReadSearchService;
import com.bynnean.cartoon.ui.SearchActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private boolean bound = false;
    private Messenger messenger;
    private StartHandler mHandler;
    private Messenger handlerMessenger;
    public StartServiceConnection serviceConnection = new StartServiceConnection();
    private ArrayList<ComicsBean> datas;
    private int offset = 0;//每页的偏移量
    private RecommendAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshWidget;
    private LinearLayoutManager mLayoutManager;
    private static final String TAG = "Fragment";
    public boolean isRefreshing=false;//是否正在刷新
    public static FragmentActivity activity;


    public RecommendFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recommend, container, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.re_recyclerView);
        mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);


        // 这句话是为了，第一次进入页面的时候显示加载进度条
        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mSwipeRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshWidget.setRefreshing(false);
                    }
                },10 * 1000);
            }
        });
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);

        if (datas==null){
            datas=new ArrayList<>();
        }

        if (mAdapter==null){

        mAdapter = new RecommendAdapter(datas, getActivity());

        }

        mRecyclerView.setAdapter(mAdapter);



        //上拉加载，下拉刷新
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (mLayoutManager.findLastVisibleItemPosition() == mAdapter.getItemCount() - 1) {
                    offset += 20;
                    Message msg = Message.obtain(null, ReadSearchService.MSG_WHAT_TOPIC, 0, 0);
                    Bundle bundle = new Bundle();
                    bundle.putString("pageIndex", "" + offset);
                    msg.setData(bundle);
                    msg.replyTo = new Messenger(mHandler);
                    try {
                        messenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        view.findViewById(R.id.search_tuijian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });


    }

    public class StartServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            bound = true;
            handlerMessenger = new Messenger(mHandler);
            Message msg = Message.obtain(null, ReadSearchService.MSG_WHAT_TOPIC, 0, 0);
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
    public void onStart() {
        super.onStart();
        mHandler = new StartHandler();

        if (!bound) {
            Intent intent = new Intent(getActivity(),ReadSearchService.class);
            getContext().bindService(intent, serviceConnection, getContext().BIND_AUTO_CREATE);
        }


    }

    class StartHandler extends Handler {


        @Override
        public void handleMessage(Message msg) {


            switch (msg.what) {
                case ReadSearchService.RESULT_OK:

                    Bundle bundle = msg.getData();
                    ArrayList<ComicsBean> readsearchdata =
                            bundle.getParcelableArrayList("recommonddata");
                    isRefreshing=true;
                    if (datas == null) {
                        datas = new ArrayList<>();
                        mAdapter=new RecommendAdapter(datas,getActivity());
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    datas.addAll(readsearchdata);

                    mAdapter.notifyDataSetChanged();


                    break;
            }

            super.handleMessage(msg);
        }
    }

}
