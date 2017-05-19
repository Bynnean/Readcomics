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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.adapter.QuerymoreAdapter;
import com.bynnean.cartoon.service.ReadSearchService;
import com.bynnean.cartoon.bean.Querymore;

import java.util.ArrayList;

public class SearchInputActivity extends AppCompatActivity {
    private Messenger messenger;
    private StartHandler mHandler;
    private Messenger handlerMessenger;
    public  StartServiceConnection serviceConnection=new StartServiceConnection();
    private  ArrayList<Querymore> mQuerymoreList;
    private ListView mListView;
    private boolean bound = false;
    private QuerymoreAdapter mAdapter;//ListView适配器
    private EditText mEditText;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_input);
        mListView= (ListView) findViewById(R.id.listview_search_input);
        mEditText=(EditText)findViewById(R.id.et_search_input);

        //输入框监听
        mEditText.addTextChangedListener(textWatcher);
        //取消
        findViewById( R.id.cancel_search_input).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }

    /**
     * mEditText内容监听事件
     */
    private TextWatcher textWatcher=new TextWatcher(){
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        //内容改动时
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (bound == true) {
                unbindService(serviceConnection);
                messenger = null;
                bound = false;
                mQuerymoreList = null;
            }
            keyword = mEditText.getText().toString();
            if (keyword.trim() != null && !keyword.trim().equals("")) {
                mHandler = new StartHandler();
                if (!bound) {
                    Intent intent = new Intent(SearchInputActivity.this,ReadSearchService.class);
                    bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                }
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };


    public class StartServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            bound = true;
            handlerMessenger = new Messenger(mHandler);
            Message msg = Message.obtain(null, ReadSearchService.MSG_WHAT_SEARCHINPUT, 0, 0);
            Bundle bundle=new Bundle();
            bundle.putString("keyword",keyword);
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
                    mAdapter=new QuerymoreAdapter(SearchInputActivity.this,mQuerymoreList);
                    mListView.setAdapter(mAdapter);
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
