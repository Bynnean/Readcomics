package com.bynnean.cartoon.util;

import android.content.Context;
import android.net.ConnectivityManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Author：Administrator on 2015/11/19 07:46
 * Email：709272013@qq.com
 */
public class VolleyHttpUtils {
    private static RequestQueue mQueue;
    private static VolleyHttpUtils mInstance;

    public VolleyHttpUtils (Context context) {
        mQueue = Volley.newRequestQueue(context);
    }

    /**
     * 获取HttpUtils 单例
     * @param context 上下文
     */
    public static VolleyHttpUtils newInstance(Context context) {
        if (mInstance == null) {
            synchronized (VolleyHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new VolleyHttpUtils(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 把一个请求放入请求队列中
     * @param req 一个请求，StringRequest, JsonObjectRequest, JsonArrayRequest, ImageRequest 中的一个
     */
    public void sendRequest(Request req) {
        mQueue.add(req);
    }

    /**
     * 获取请求队列
     * @return RequestQueue
     */
    public static RequestQueue getRequestQueue() {
        return mQueue;
    }

    /**
     * 判断是否联网
     * @return 已联网返回true
     */
    public static boolean isNetworkConn (Context context) {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return !(manager.getActiveNetworkInfo() == null);
    }

}


