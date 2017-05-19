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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.service.ReadSearchService;
import com.bynnean.cartoon.adapter.QuerymoreAdapter;
import com.bynnean.cartoon.bean.Querymore;

import java.util.ArrayList;

public class QueryMoreActivity extends AppCompatActivity {
    private TextView mTitle;
    private String action;
    private Messenger messenger;
    private StartHandler mHandler;
    private Messenger handlerMessenger;
    public  StartServiceConnection serviceConnection=new StartServiceConnection();
    private  ArrayList<Querymore> mQuerymoreList;
    private ListView mListView;
    private boolean bound = false;
    private QuerymoreAdapter mAdapter;//ListView适配器
    private ImageView mReturn;//返回键

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querymore);

        mTitle= (TextView) findViewById(R.id.tv_title_querymore);
        mReturn= (ImageView) findViewById(R.id.return_querymore);
        //返回监听
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        action=intent.getStringExtra("action");
        mTitle.setText(title);

        mListView=(ListView)findViewById(R.id.listview_querymore);
    }

    public class StartServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            bound = true;
            handlerMessenger = new Messenger(mHandler);
            Message msg = Message.obtain(null, ReadSearchService.MSG_WHAT_QUEMORE, 0, 0);
            Bundle bundle=new Bundle();
            bundle.putString("action",action);
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
    public void onStart() {
        super.onStart();
        mHandler = new StartHandler();
        if (!bound) {
            Intent intent = new Intent(this,ReadSearchService.class);
            this.bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        }


    }

    class StartHandler extends Handler implements AdapterView.OnItemClickListener {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ReadSearchService.RESULT_OK:
                    Bundle bundle = msg.getData();
                    ArrayList<Querymore> list =
                            bundle.getParcelableArrayList("querymores");
                    Log.d("lishuhua", "---------list---" + list.size());
                    if (mQuerymoreList == null) {
                        mQuerymoreList = new ArrayList<>();
                    }
                    mQuerymoreList.addAll(list);
                    mAdapter=new QuerymoreAdapter(QueryMoreActivity.this,mQuerymoreList);
                    mListView.setAdapter(mAdapter);
                    mListView.setOnItemClickListener(this);
                    break;
            }
            super.handleMessage(msg);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            intent.putExtra("topicId",mQuerymoreList.get(position).getId());
            intent.setClass(QueryMoreActivity.this, RecommendTopicActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

}
