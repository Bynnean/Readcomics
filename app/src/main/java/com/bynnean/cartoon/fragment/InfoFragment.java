package com.bynnean.cartoon.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.bean.FindTopicList;
import com.bynnean.cartoon.config.API;
import com.bynnean.cartoon.util.HttpUtil;

import org.xutils.x;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {


    private ImageView iv_user;
    private TextView tv_des, tv_name;


    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_info, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        tv_name = (TextView) view.findViewById(R.id.user_tv_name);
        iv_user = (ImageView) view.findViewById(R.id.user_iv_show);

        tv_des = (TextView) view.findViewById(R.id.user_tv_des);


        Log.d("adddata","adddata:"+ API.FIND_MIXED);
        HttpUtil.getdata(API.FIND_MIXED ,  new HttpUtil.callback() {
                    @Override
                    public void adddata(ArrayList<FindTopicList> list) {

                            tv_name.setText(list.get(0).getList().get(0).getNickname());
                            tv_des.setText(list.get(0).getTopics_title());
                            x.image().bind(iv_user, list.get(0).getList().get(0).getImagePath());
                        }
                }, getContext()
        );


    }

}
