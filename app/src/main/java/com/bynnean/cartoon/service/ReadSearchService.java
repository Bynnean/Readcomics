package com.bynnean.cartoon.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bynnean.cartoon.bean.FindTopicList;
import com.bynnean.cartoon.bean.Banner;
import com.bynnean.cartoon.bean.Querymore;
import com.bynnean.cartoon.config.API;
import com.bynnean.cartoon.util.ParserUtils;
import static com.android.volley.Request.Method.GET;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class ReadSearchService extends Service {
    private static final String TAG = "ReadSearchService";
    public static final int RESULT_O =400 ;

    private Messenger recommendItemDes;

    public ReadSearchService() {
    }
//    public static final String SERVICE_ACTION="com.manman.service.ReadSearchService";

    public static final int RESULT_OK = 200;
    public static final int MSG_WHAT_TITLE =1;
    public static final int MSG_WHAT_TOPIC =2;
    public static final int MSG_WHAT_FIND =3;
    public static final int MSG_WHAT_BANNER=5;
    public static final int MSG_WHAT_QUEMORE=6;
    public static final int MSG_WHAT_SEARCHITEM=7;
    public static final int MSG_WHAT_SEARCHINPUT=8;

    public static final int MSG_WHAT_RECOMMEND_ITEM =4;

    public static final int MSG_WHAT_RECOMMEND_ITEM_USER =9;
    public static final int MSG_WHAT_RECOMMEND_ITEM_Topic =10;
    public static final int MSG_WHAT_RECOMMEND_ITEM_CONTENT =11;


    private Messenger serviceMessenger = new Messenger(new BackstageHandler());

    private RequestQueue mRequestQueue;

    private Messenger clientTitle;//标题信息处理

    private Messenger recommendTopic;//推荐的详细信息

    private Messenger recommendItem;//推荐的详细信息
    private Messenger find;//发现
    private Messenger banner;//广告
    private Messenger querymore;//查看更多
    private Messenger searchItem;//搜索
    private Messenger searchInput;//搜索输入框

    private Messenger recommendItemUser;//查看更多
    private Messenger recommendItemTopic;//查看更多
    private Messenger recommendItemContent;//查看更多


    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent){
        return serviceMessenger.getBinder();
    }


    class BackstageHandler extends Handler {
        @Override
        public void handleMessage(Message msg){

            switch(msg.what){
                case MSG_WHAT_TITLE:
                    clientTitle=msg.replyTo;
                    requestTitle(msg);
                    break;
                case MSG_WHAT_TOPIC:
                    recommendTopic=msg.replyTo;
                    requestTopic(msg);
                    break;
                case MSG_WHAT_RECOMMEND_ITEM:
                    recommendItem=msg.replyTo;
                    requestRecommendItem(msg);
                    break;
                case MSG_WHAT_FIND:
                    find=msg.replyTo;
                    requestFind(msg);
                    break;
                case MSG_WHAT_BANNER:
                    banner=msg.replyTo;
                    requestBanner(msg);
                    break;
                case MSG_WHAT_QUEMORE:
                    querymore=msg.replyTo;
                    requestQuerymore(msg);
                    break;
                case MSG_WHAT_RECOMMEND_ITEM_USER:
                    recommendItemUser=msg.replyTo;
                    requestItemUser(msg);
                    break;
                case MSG_WHAT_RECOMMEND_ITEM_Topic:
                    recommendItemTopic=msg.replyTo;
                    requestItemTopic(msg);
                    break;

                case MSG_WHAT_SEARCHITEM:
                    searchItem=msg.replyTo;
                    requestSearchItem(msg);
                    break;
                case MSG_WHAT_SEARCHINPUT:
                    searchInput=msg.replyTo;
                    requestSearchInput(msg);
                    break;
                case MSG_WHAT_RECOMMEND_ITEM_CONTENT:
                    recommendItemContent=msg.replyTo;
                    requestItemContent(msg);
                    break;
            }
            super.handleMessage(msg);
        }
    }


    /**
     * 内容
     * @param msg
     */
    private void requestItemContent(Message msg) {
        final String topicId = msg.getData().getString("content","0");

//        Log.d("topicId:",API.RECOMMEND_ITEM_TOPIC + topicId);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                GET,
                API.RECOMMEND_ITEM_TOPIC + topicId,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Bundle bundle=new Bundle();
                        bundle.putParcelableArrayList("recommonddataItemContent",
                                ParserUtils.parseRecommendItemTopic(response));

                        Message message=Message.obtain();

                        message.what=RESULT_OK;
                        message.setData(bundle);
                        try {
                            recommendItemContent.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            Log.d(TAG, "RemoteException");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,error.getMessage());
                    }
                }
        );
        mRequestQueue.add(jsonObjectRequest);
    }
    /**
     * 推荐页面的专题
     * @param msg
     */

    private void requestItemTopic(Message msg) {
        final String topicId = msg.getData().getString("topicId","0");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                GET,
                API.RECOMMEND_ITEM_TOPIC + topicId,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Bundle bundle=new Bundle();
                        bundle.putParcelableArrayList("recommonddataItemTopic",
                                ParserUtils.parseRecommendItemTopic(response));

                        Message message=Message.obtain();

                        message.what=RESULT_OK;
                        message.setData(bundle);
                        try {
                            recommendItemTopic.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            Log.d(TAG, "RemoteException");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,error.getMessage());
                    }
                }


        );

        mRequestQueue.add(jsonObjectRequest);

    }

    /**
     * 推荐页面点击用户头像需要下载数据
     * @param msg
     */
    private void requestItemUser(Message msg) {

        final String userid = msg.getData().getString("userid","0");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                GET,
                API.RECOMMEND_ITEM_USER + userid,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Bundle bundle=new Bundle();
                        bundle.putStringArrayList("recommonddataItemUser",
                                ParserUtils.parseRecommendItemUser(response));

                        Message message=Message.obtain();

                        message.what=RESULT_OK;
                        message.setData(bundle);
                        try {
                            recommendItemUser.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            Log.d(TAG, "RemoteException");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,error.getMessage());
                    }
                }


        );

        mRequestQueue.add(jsonObjectRequest);
    }

    /**
     * 推荐页面的每一个item
     * @param msg
     */
    private void requestRecommendItem(Message msg) {

        final String id = msg.getData().getString("item","0");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                GET,
                API.RECOMMEND_ITEM_IMAGE + id,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        Bundle bundle=new Bundle();
                        bundle.putParcelableArrayList("recommonddataItem", ParserUtils.parseRecommendItem(response));
                        Message message=Message.obtain();

                        message.what=RESULT_OK;
                        message.setData(bundle);
                        try {
                            recommendItem.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            Log.d(TAG, "RemoteException");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Log.d(TAG,error.getMessage());
                    }
                }


        );

        mRequestQueue.add(jsonObjectRequest);

    }

    /**
     * 获取推荐的详细信息
     * @param msg
     */
    private void requestTopic(Message msg) {

        final String finalPageIndex = msg.getData().getString("pageIndex","0");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                GET,
                API.RECOMMENDAPI + finalPageIndex,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Bundle bundle=new Bundle();
                        bundle.putParcelableArrayList("recommonddata", ParserUtils.parseRecommendData(response));
                        Message message=Message.obtain();
                        message.what=RESULT_OK;
                        message.setData(bundle);
                        try {
                            recommendTopic.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            Log.d(TAG, "RemoteException");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        mRequestQueue.add(jsonObjectRequest);
    }

    /**
     *
     * @param msg
     * 获取标题信息，
     */
    private void requestTitle(Message msg){

        JsonObjectRequest jsonRequest=new JsonObjectRequest(
               GET,
                API.READ_SEARCH,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){
                        Bundle bundle=new Bundle();
                        bundle.putParcelableArrayList("readsearchdata", ParserUtils.parseSearchData(response));
                        Message message=Message.obtain();
                        message.what=RESULT_OK;
                        message.setData(bundle);
                        try {
                            clientTitle.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG,error.getMessage());
            }
        });

        mRequestQueue.add(jsonRequest);

    }

    /**
     *
     * @param
     */
    public void requestFind(Message msg) {
        String url = API.FIND_MIXED;
        JsonObjectRequest request = new JsonObjectRequest(
                GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Bundle bundle=new Bundle();
                        ArrayList<FindTopicList> findTopicLists = ParserUtils.parseFind(response);
                        bundle.putParcelableArrayList("list", findTopicLists);
                        Message message=Message.obtain();
                        message.what=RESULT_OK;
                        message.setData(bundle);
                        try {
                            find.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "onJsonRequest--onErrorResponse");
                        error.printStackTrace();
                    }
                }
        );
        mRequestQueue.add(request);
    }
    public void requestBanner(Message msg) {
        Log.d("imageList","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        JsonObjectRequest request=new JsonObjectRequest(
                GET,
                API.FIND_BANNERS,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject res) {
                        if(res==null) return;
                        ArrayList<Banner> imageList=new ArrayList<Banner>();
                        try {
                            JSONObject jsonObject = res.getJSONObject("data");
                            JSONArray array = jsonObject.getJSONArray("banner_group");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                String title = object.getString("title");
                                String pic = object.getString("pic");
                                String value = object.getString("value");
                                Banner banners = new Banner(pic, title, value);
                                imageList.add(banners);

                            }

                            Bundle bundle=new Bundle();
                            bundle.putParcelableArrayList("imageList", imageList);
                            Message message=Message.obtain();
                            Log.d("imageList","imageList1:"+imageList.size());
                            message.what=RESULT_O;

                            message.setData(bundle);
                            Log.d("imageList", "imageList2:" + imageList.size());
                            if (banner!=null){

                            banner.send(message);
                            }else {
                                Log.d("imageList", "imageList2 bannew is null:");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG","onJsonRequest--onErrorResponse");
                        error.printStackTrace();
                    }
                }
        );
        mRequestQueue.add(request);
    }

    /**
     * 查看更多页面的每一个item
     * @param msg
     */
    private void requestQuerymore(Message msg) {
        final String action = msg.getData().getString("action");
        String url=API.MORE1 + action + API.MORE2;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Bundle bundle=new Bundle();
                        ArrayList<Querymore> list=ParserUtils.parseQueryMore(response);
                        bundle.putParcelableArrayList("querymores", list);
                        Message message=Message.obtain();
                        message.what=RESULT_OK;
                        message.setData(bundle);
                        try {
                            if(bundle!=null){
                                querymore.send(message);
                            }

                        } catch (RemoteException e) {
                            e.printStackTrace();
                            Log.d(TAG, "RemoteException");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.getMessage());
                    }
                }
        );
        mRequestQueue.add(jsonObjectRequest);
    }

    /**
     * 搜索页面的每一个item
     * @param msg
     */
    private void requestSearchItem(Message msg) {
        final String tag = msg.getData().getString("tag");
        String url= null;
        try {
            url = API.SEARCH_HOT+ URLEncoder.encode(tag, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Bundle bundle=new Bundle();
                        ArrayList<Querymore> list=ParserUtils.parseQueryMore(response);
                        bundle.putParcelableArrayList("querymores", list);
                        Message message=Message.obtain();
                        message.what=RESULT_OK;
                        message.setData(bundle);
                        try {
                            if(bundle!=null){
                                searchItem.send(message);
                            }

                        } catch (RemoteException e) {
                            e.printStackTrace();
                            Log.d(TAG, "RemoteException");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.getMessage());
                    }
                }
        );
        mRequestQueue.add(jsonObjectRequest);
    }
    /**
     * 搜索框的搜索结果
     * @param msg
     */
    private void requestSearchInput(Message msg) {
        final String keyword = msg.getData().getString("keyword");
        String url= null;
        try {
            url = API.SEARCH_KEYWORD+ URLEncoder.encode(keyword, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Bundle bundle=new Bundle();
                        ArrayList<Querymore> list=ParserUtils.parseQueryMore(response);
                        bundle.putParcelableArrayList("querymores", list);
                        Message message=Message.obtain();
                        message.what=RESULT_OK;
                        message.setData(bundle);
                        try {
                            if(bundle!=null){
                                searchInput.send(message);
                            }

                        } catch (RemoteException e) {
                            e.printStackTrace();
                            Log.d(TAG, "RemoteException");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.getMessage());
                    }
                }
        );
        mRequestQueue.add(jsonObjectRequest);
    }
}
