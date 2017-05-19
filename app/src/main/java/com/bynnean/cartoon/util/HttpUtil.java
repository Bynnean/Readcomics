package com.bynnean.cartoon.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bynnean.cartoon.bean.FindTopicList;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Author：Administrator on 2015/11/16 09:59
 * Email：709272013@qq.com
 */
public class HttpUtil {


    private static final String TAG = "HttpUtil";

    /**
     * 下载数据
     * @param path 路径
     * @param mCallback 返回数据实现的接口
     * @param context 上下文
     */
    public static void getdata(String path,   final callback mCallback,
                               Context context) {


        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                path,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        List<NewsBean> list = parser(response);
                        Log.d(TAG, ">>>>>>>>> onErrorResponse" + response.toString());
                        ArrayList<FindTopicList> list=ParserUtils.parseFind(response);
                        Log.d(TAG, ">>>>>>>>> onErrorResponse" + list.size());
                        mCallback.adddata(list);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        rereshListener.setIsSuccessDownLoad(1);
//                        downloaderror(adapter);
                        Log.d(TAG, ">>>>>>>>> onErrorResponse" + error.toString());
                    }
                });
        //将请求添加到队列中
        VolleyHttpUtils.newInstance(context).sendRequest(request);
    }


    public interface callback {
        /**
         * 返回数据的接口
         * @param list
         */
        void adddata(ArrayList<FindTopicList> list);
    }
    }

