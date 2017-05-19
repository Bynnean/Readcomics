package com.bynnean.cartoon.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.adapter.SearchRecyclerAdapter;
import com.bynnean.cartoon.service.ReadSearchService;
import com.bynnean.cartoon.bean.SearchBean;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private RecyclerView mRecyclerView;
    private SearchRecyclerAdapter mAdapter;
    private boolean bound = false;
    private Messenger messenger;
    private StartHandler mHandler;
    private Messenger handlerMessenger;
    public  StartServiceConnection serviceConnection=new StartServiceConnection();
    private  ArrayList<SearchBean> datas=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerView);

        //这里用线性宫格显示类似于grid view
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter=new SearchRecyclerAdapter(datas,SearchActivity.this);
        mAdapter.setOnItemClickListener(new SearchRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, SearchBean data) {

                Intent intent=new Intent(SearchActivity.this,SearchItemActivity.class);
                intent.putExtra("tag",data.title);
                startActivity(intent);

            }
        });

        findViewById(R.id.search_input).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, SearchInputActivity.class));
            }
        });
        //取消
        findViewById(R.id.search_bar_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class StartServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            bound = true;
            handlerMessenger = new Messenger(mHandler);
            Message msg = Message.obtain(null, ReadSearchService.MSG_WHAT_TITLE, 0, 0);
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
        mHandler=new StartHandler();

        if (!bound){
            Intent intent=new Intent(this,ReadSearchService.class);
            bindService(intent,serviceConnection,BIND_AUTO_CREATE);
        }


    }

    class StartHandler extends Handler {
        @Override
        public void handleMessage(Message msg){

            switch (msg.what){
                case ReadSearchService.RESULT_OK:
//                    Intent intent=new Intent();
//                    intent.putExtras(msg.getData());


                    Bundle bundle=msg.getData();
                    ArrayList<SearchBean> readsearchdata =
                            bundle.getParcelableArrayList("readsearchdata");
                    if (datas==null){
                        datas=new ArrayList<>();
                    }
                      datas.addAll(readsearchdata);
                    mRecyclerView.setAdapter(mAdapter);

                    break;
            }

            super.handleMessage(msg);
        }
    }

}
