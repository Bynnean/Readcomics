package com.bynnean.cartoon.fragment;


import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alipay.sdk.pay.ui.PayDemoActivity;
import com.bhsoft.android.pay.MiGuPay;
import com.bynnean.cartoon.R;
import com.bynnean.cartoon.adapter.ContentAdapter;
import com.bynnean.cartoon.service.ReadSearchService;
import com.bynnean.cartoon.ui.LoginActivity;
import com.bynnean.cartoon.ui.RecommendItemActivity;
import com.bynnean.cartoon.bean.TopicBean;
import com.bynnean.cartoon.util.PayUtils;
import com.bynnean.cartoon.view.ActionSheet;
import com.bynnean.cartoon.view.MiGuPayActivity;
import com.bynnean.cartoon.view.OrderDialog;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContenFragment extends Fragment {

    private Messenger messenger;
    private boolean bound = false;
    private StartHandler mHandler;
    private Messenger handlerMessenger;
    public StartServiceConnection serviceConnection = new StartServiceConnection();
    public static ArrayList<TopicBean> datas = new ArrayList<>();
    private String content = "";
    private ListView mListView;
    private ContentAdapter mAdapter;

    public ContenFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conten, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        content = arguments.getString("content", "0");


        mListView = (ListView) view.findViewById(R.id.content_lv);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                if (!PayUtils.getLoginState()) {
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                if (PayUtils.isNeedPayOrder(datas.get(position).id)) {
                    OrderDialog.Builder builder = new OrderDialog.Builder((Activity) getActivity());
                    builder.setPositiveButton(new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                         /*   Intent intent = new Intent(getActivity(), PayDemoActivity.class);
                            intent.putExtra("itemId", datas.get(position).id);
                            intent.putExtra("data_title",datas.get(position).title);
                            intent.putExtra("topic_title",datas.get(position).description);
                            intent.putExtra("vertical_image_url",datas.get(position).cover_image_url);
                            intent.putExtra("pay", ""+(OrderDialog.index + 1));
                            getActivity().startActivity(intent);*/

                            ActionSheet actionSheet = new ActionSheet(getActivity())
                                    .builder()
                                    .setCancelable(false)
                                    .setCanceledOnTouchOutside(false)
                                    .setTitle("选择支付方式")
                                    .addSheetItem("支付宝", ActionSheet.SheetItemColor.Blue, new ActionSheet.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    Intent intent = new Intent(getActivity(), PayDemoActivity.class);
                                                    intent.putExtra("itemId", datas.get(position).id);
                                                    intent.putExtra("data_title", datas.get(position).title);
                                                    intent.putExtra("topic_title", datas.get(position).description);
                                                    intent.putExtra("vertical_image_url", datas.get(position).cover_image_url);
                                                    intent.putExtra("pay", "" + (OrderDialog.index + 1));
                                                    getActivity().startActivity(intent);
                                                }
                                            }
                                    )
                                    .addSheetItem("咪咕", ActionSheet.SheetItemColor.Blue,
                                            new ActionSheet.OnSheetItemClickListener() {
                                                @Override
                                                public void onClick(int which) {
                                                    MiGuPayActivity.url = MiGuPay.getPayUrl(which - 1);
                                                    getActivity().startActivity(new Intent(getActivity(), MiGuPayActivity.class));
                                                }
                                            });
                            actionSheet.show();


//                        mContext.startActivity(new Intent(mContext, PayDemoActivity.class));
                            // 设置你的操作事项
//
                        }
                    });

                    builder.setNegativeButton(new android.content.DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.create("", "", "").show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("itemId", datas.get(position).id);
                    intent.setClass(getActivity(), RecommendItemActivity.class);
                    getActivity().startActivity(intent);
                }

//                if(PayUtils.isNeedPayOrder(datas.get(position).id)){
//
//                } else {
//                    Intent intent = new Intent(getContext(), RecommendItemActivity.class);
//                    intent.putExtra("itemId",datas.get(position).id);
//                    intent.putParcelableArrayListExtra("datas",datas);
//                    startActivity(intent);
//                }

            }
        });

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
            Message msg = Message.obtain(null, ReadSearchService.MSG_WHAT_RECOMMEND_ITEM_CONTENT, 0, 0);

            Bundle bundle = new Bundle();
            bundle.putString("content", content);
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
            Intent intent = new Intent(getActivity(), ReadSearchService.class);
            getContext().bindService(intent, serviceConnection, getContext().BIND_AUTO_CREATE);
        }


    }

    class StartHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case ReadSearchService.RESULT_OK:


                    Bundle bundle = msg.getData();
                    ArrayList<TopicBean> readsearchdata =
                            bundle.getParcelableArrayList("recommonddataItemContent");
                    if (datas == null) {
                        datas = new ArrayList<>();
                    }
                    datas.addAll(readsearchdata);

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
        if (mAdapter == null) {
            mAdapter = new ContentAdapter(getContext(), datas);
        }
        mListView.setAdapter(mAdapter);
    }


}
