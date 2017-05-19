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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.service.ReadSearchService;
import com.bynnean.cartoon.adapter.ImageAdapter;
import com.bynnean.cartoon.bean.RecommendItem;

import org.xutils.x;

import java.util.ArrayList;

public class RecommendItemActivity extends AppCompatActivity implements View.OnClickListener{
    private boolean bound = false;
    private Messenger messenger;
    private StartHandler mHandler;
    private Messenger handlerMessenger;
    public StartServiceConnection serviceConnection = new StartServiceConnection();
    private ArrayList<RecommendItem> datas;
    private ImageView mIVback, mIVauthor;
    private TextView mTVtitle, mTVall, mTVname, mTVtopic;
    private String itemId;
    private ArrayList<String> images;
    private ListView mListView;
    private View view;
    private ProgressDialog mDialog;
//    private ImageView mIVcollect;
    private String username="";
    private String data_title="";
    private String topic_title="";
    private String vertical_image_url="";
//    private ImageView mAlbum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_item);
        Intent intent = getIntent();
        itemId = intent.getStringExtra("itemId");
         username = intent.getStringExtra("username");
        data_title = intent.getStringExtra("data_title");
        topic_title = intent.getStringExtra("topic_title");
        vertical_image_url = intent.getStringExtra("vertical_image_url");


        init();//初始化组件
        mDialog=new ProgressDialog(this);
        mDialog.setMessage("正在加载中....");
        mDialog.show();

        //注册监听事件
        registListener();


    }

    private void setContent() {
        if (datas==null){
            datas=new ArrayList<>();
        }
        for (int i = 0; i < datas.size(); i++) {
            if(datas.get(i).topicBean.user.nickname.contains("快看")){
                mTVname.setText("ManMan");
                mTVtopic.setText("ManMan漫画");
                mIVauthor.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher));
            } else {
            x.image().bind(mIVauthor, datas.get(i).topicBean.user.avatar_url);
            mTVname.setText(datas.get(i).topicBean.user.nickname);
            mTVtopic.setText(datas.get(i).topicBean.title);
            }
            images = datas.get(i).images;
            mTVtitle.setText(datas.get(i).title);

        }

        mListView.addHeaderView(view);
        mListView.setAdapter(new ImageAdapter(RecommendItemActivity.this,images));

    }

    private void registListener() {
        mIVback.setOnClickListener(this);
//        mIVcollect.setOnClickListener(this);
//        mAlbum.setOnClickListener(this);
    }

    private void init() {
        view=LayoutInflater.from(RecommendItemActivity.this).inflate(R.layout.recommend_item_title,null);
        mIVback = (ImageView) findViewById(R.id.recommend_item_iv_back);
        mIVauthor = (ImageView) view.findViewById(R.id.recommend_item_iv_author);
        mTVtitle = (TextView) findViewById(R.id.recommend_item_tv_title);
        mTVall = (TextView) findViewById(R.id.recommend_item_tv_all);
        mTVname = (TextView) view.findViewById(R.id.recommend_item_tv_name);
        mTVtopic = (TextView) view.findViewById(R.id.recommend_item_tv_topic);
        mListView = (ListView) findViewById(R.id.recommend_item_lv_image);
//        mIVcollect= (ImageView) findViewById(R.id.collection_recommend_item);
//        mAlbum= (ImageView) findViewById(R.id.album_recommend_item);






    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.recommend_item_iv_back:
                finish();
                break;
//            case R.id.collection_recommend_item:
//                DBManager manager=new DBManager(RecommendItemActivity.this);
//                ContentValues cv = new ContentValues();
//                if (itemId!=null&&data_title!=null&&topic_title!=null&&username!=null&&vertical_image_url!=null) {
//                    cv.put("id", itemId);
//                    cv.put("data_title", data_title);
//                    cv.put("topic_title", topic_title);
//                    cv.put("nickname", username);
//                    cv.put("vertical_image_url", vertical_image_url);
//                    manager.insertSQLite(RecommendItemActivity.this, DBHelper.TABLE_NAME_collect, cv);
//
//                }
////                mIVcollect.setSelected(true);
////                Toast.makeText(RecommendItemActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
////                    mIVcollect.setClickable(false);
//                break;
//            case R.id.album_recommend_item:
//                //调用系统分享 通用版
//                Intent intentsh = new Intent(Intent.ACTION_SEND);
//                intentsh.setType("image/*");
//                intentsh.putExtra(Intent.EXTRA_SUBJECT, "Share");
//                intentsh.putExtra(Intent.EXTRA_TEXT, "好像，这个是可行的哦");
//                intentsh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//               startActivity(Intent.createChooser(intentsh, "分享到---"));
//                break;


        }


    }

    public class StartServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            bound = true;
            handlerMessenger = new Messenger(mHandler);
            Message msg = Message.obtain(null, ReadSearchService.MSG_WHAT_RECOMMEND_ITEM, 0, 0);

            Bundle bundle = new Bundle();
            bundle.putString("item",  itemId);
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
                    ArrayList<RecommendItem> readsearchdata =
                            bundle.getParcelableArrayList("recommonddataItem");
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }

}
