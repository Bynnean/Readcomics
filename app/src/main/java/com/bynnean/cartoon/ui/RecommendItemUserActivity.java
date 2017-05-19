package com.bynnean.cartoon.ui;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.service.ReadSearchService;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;

public class RecommendItemUserActivity extends AppCompatActivity {
    private boolean bound = false;
    private Messenger messenger;
    private StartHandler mHandler;
    private Messenger handlerMessenger;
    public StartServiceConnection serviceConnection = new StartServiceConnection();
    private ArrayList<String> datas;
    private String userid = "";
    private ProgressDialog mDialog;
    private ImageView mivAuthor, mIVshow;
    private TextView mtvUserName, author_intro, mTVuser, mTVdes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommen_item_user);

        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");

        init();
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("正在加载中....");
        mDialog.show();

    }

    /**
     * 初始化组件
     */
    private void init() {
        mivAuthor = (ImageView) findViewById(R.id.author_comic_avatar);
        mtvUserName = (TextView) findViewById(R.id.author_nick_name);
        author_intro = (TextView) findViewById(R.id.author_intro);
        mIVshow = (ImageView) findViewById(R.id.user_iv_show);
        mTVuser = (TextView) findViewById(R.id.user_tv_name);
        mTVdes = (TextView) findViewById(R.id.user_tv_des);

    }

    public class StartServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            bound = true;
            handlerMessenger = new Messenger(mHandler);
            Message msg = Message.obtain(null, ReadSearchService.MSG_WHAT_RECOMMEND_ITEM_USER, 0, 0);

            Bundle bundle = new Bundle();
            bundle.putString("userid", userid);
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
                    ArrayList<String> readsearchdata =
                            bundle.getStringArrayList("recommonddataItemUser");
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

    //设置组件的内容
    private void setContent() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        try {
            x.image().bind(mivAuthor, datas.get(0),
                    new ImageOptions.Builder().setRadius(360).build());
            author_intro.setText(datas.get(1));
            mtvUserName.setText(datas.get(2));
            mTVuser.setText(datas.get(3));
            mTVdes.setText(datas.get(4));
            x.image().bind(mIVshow, datas.get(5));
        } catch (Exception e){

        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
