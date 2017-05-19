package com.bynnean.cartoon.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.service.ReadSearchService;
import com.bynnean.cartoon.adapter.QuerymoreAdapter;
import com.bynnean.cartoon.bean.Querymore;

import java.util.ArrayList;

public class SearchItemActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private Messenger messenger;
    private StartHandler mHandler;
    private Messenger handlerMessenger;
    public  StartServiceConnection serviceConnection=new StartServiceConnection();
    private  ArrayList<Querymore> mQuerymoreList;
    private ListView mListView;
    private boolean bound = false;
    private QuerymoreAdapter mAdapter;//ListView适配器
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        Log.d("lishuhua","-----tag-----"+tag);

//        ImageView imageRetrun= (ImageView) ;
        //返回监听
        findViewById(R.id.return_search_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mListView= (ListView) findViewById(R.id.listview_search_item);

        TextView title= (TextView) findViewById(R.id.title_search_item);
        title.setText(tag);
        mListView.setOnItemClickListener(this);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(SearchItemActivity.this,RecommendTopicActivity.class);
        intent.putExtra("topicId",mQuerymoreList.get(position).getId());
        intent.putExtra("username",mQuerymoreList.get(position).getNickname());
        intent.putExtra("topic_title",mQuerymoreList.get(position).getTitle());
        intent.putExtra("vertical_image_url",mQuerymoreList.get(position).getCover_image_url());
        startActivity(intent);
    }

    public class StartServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            bound = true;
            handlerMessenger = new Messenger(mHandler);
            Message msg = Message.obtain(null, ReadSearchService.MSG_WHAT_SEARCHITEM, 0, 0);
            Bundle bundle=new Bundle();
            bundle.putString("tag",tag);
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
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        }


    }

    class StartHandler extends Handler {
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
                    mAdapter=new QuerymoreAdapter(SearchItemActivity.this,mQuerymoreList);
                    mListView.setAdapter(mAdapter);
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
