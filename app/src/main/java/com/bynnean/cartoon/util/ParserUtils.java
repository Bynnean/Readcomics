package com.bynnean.cartoon.util;

import android.util.Log;

import com.bynnean.cartoon.bean.ComicsBean;
import com.bynnean.cartoon.bean.ComicsContent;
import com.bynnean.cartoon.bean.FindTopicList;
import com.bynnean.cartoon.bean.RecommendItem;
import com.bynnean.cartoon.bean.FindTopic;
import com.bynnean.cartoon.bean.Querymore;
import com.bynnean.cartoon.bean.SearchBean;
import com.bynnean.cartoon.bean.TopicBean;
import com.bynnean.cartoon.bean.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Author：Administrator on 2015/11/16 11:41
 * Email：709272013@qq.com
 */
public class ParserUtils {

    private static final String TAG = "ParserUtils";

    /**
     * 解析搜索的数据
     *
     * @param res
     * @return
     */
    public static ArrayList<SearchBean> parseSearchData(JSONObject res) {
        ArrayList<SearchBean> beansList = new ArrayList<>();
        if (res != null) {

            try {

                JSONObject data = res.getJSONObject("data");

                JSONArray suggestion = data.getJSONArray("suggestion");
                for (int i = 0; i < suggestion.length(); i++) {
                    JSONObject obj = suggestion.getJSONObject(i);
                    String icon = obj.getString("icon");
                    String priority = obj.getString("priority");
                    String tag_id = obj.getString("tag_id");
                    String title = obj.getString("title");

                    SearchBean bean = new SearchBean(icon, priority, tag_id, title);
                    beansList.add(bean);
                }

            } catch (Exception e) {

            }
        } else {
            Log.d(TAG, "res is null");
        }
        return beansList;
    }

    /**
     * 解析查看更多的详细列表的信息
     * by 李树华
     * @param res
     * @return
     */
    public static ArrayList<FindTopicList> parseFind(JSONObject res) {
        ArrayList<FindTopicList> querymores = new ArrayList<>();
        if (res != null) {
            try {
                String topics_title = null;
                String action=null;
                JSONObject jsonObject = res.getJSONObject("data");

                JSONArray array = jsonObject.getJSONArray("topics");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                    action=object.getString("action");
                    topics_title = object.getString("title");

                    JSONArray array1 = object.getJSONArray("topics");
                    ArrayList<FindTopic> topicsList = new ArrayList<FindTopic>();
                    for (int j = 0; j < array1.length(); j++) {
                        JSONObject object1 = array1.getJSONObject(j);
                        String id = object1.getString("id");
                        String title = object1.getString("title");
                        String iamgePath = object1.getString("vertical_image_url");

                        JSONObject jsonObject1 = object1.getJSONObject("user");
                        String nickname = jsonObject1.getString("nickname");

                        FindTopic topic = new FindTopic(id, title, nickname, iamgePath);

                        topicsList.add(topic);
                    }
                    FindTopicList findTopic = new FindTopicList(action,topics_title,topicsList);
                    querymores.add(findTopic);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "res is null");
        }
        return querymores;
    }

    /**
     * 解析查看更多的详细列表的信息
     * by 李树华
     * @param res
     * @return
     */
    public static ArrayList<Querymore> parseQueryMore(JSONObject res) {
        ArrayList<Querymore> querymores = new ArrayList<>();
        if (res != null) {
            try {
                JSONObject data = res.getJSONObject("data");

                JSONArray topics = data.getJSONArray("topics");
                Log.d("lishuhua", "res is null:"+topics.length());
                for (int i = 0; i < topics.length(); i++) {
                    JSONObject obj = topics.getJSONObject(i);

                    String comics_count = obj.getString("comics_count");
                    String comments_count = obj.getString("comments_count");
                    String cover_image_url = obj.getString("cover_image_url");
                    String created_at = obj.getString("created_at");
                    String description = obj.getString("description");
                    String id = obj.getString("id");
                    String is_favourite = obj.getString("is_favourite");
                    String likes_count = obj.getString("likes_count");
                    String order = obj.getString("order");
                    String title = obj.getString("title");
                    String updated_at = obj.getString("updated_at");
                    String vertical_image_url = obj.getString("vertical_image_url");

                    JSONObject user = obj.getJSONObject("user");
                    String avatar_url = user.getString("avatar_url");
                    String user_id = user.getString("id");
                    String nickname = user.getString("nickname");
                    String reg_type = user.getString("reg_type");

                    Querymore querymore=new Querymore(
                            cover_image_url,
                            comments_count,
                            description,
                            id,
                            title,
                            nickname,
                            likes_count);
                    querymores.add(querymore);
                    Log.d("lishuhua", "tos---------:" + querymore.toString());
                }

            } catch (Exception e) {

            }
        } else {
            Log.d(TAG, "res is null");
        }
        return querymores;
    }



    /**
     * 解析漫画内容的详细列表的信息
     * by 李树华
     * @param res
     * @return
     */
    public static ArrayList<ComicsContent> parseComicsContent(JSONObject res) {
        ArrayList<ComicsContent> comicsContents = new ArrayList<>();

        if (res != null) {
            try {

                JSONObject data = res.getJSONObject("data");

                JSONArray comics = data.getJSONArray("comics");
                Log.d(TAG, "res is null:"+comics.length());
                for (int i = 0; i < comics.length(); i++) {
                    JSONObject obj = comics.getJSONObject(i);

                    String cover_image_url = obj.getString("cover_image_url");
                    String created_at = obj.getString("created_at");
                    String id = obj.getString("id");
                    String is_liked = obj.getString("is_liked");
                    String likes_count = obj.getString("likes_count");
                    String shared_count = obj.getString("shared_count");
                    String title = obj.getString("title");
                    String updated_at = obj.getString("updated_at");
                    String url = obj.getString("url");

                    ComicsContent comicsContent=new ComicsContent(
                            cover_image_url,
                            created_at,
                            id,
                            likes_count,
                            title,
                            url,
                            updated_at);

                    comicsContents.add(comicsContent);
                }

            } catch (Exception e) {

            }
        } else {
            Log.d(TAG, "res is null");
        }
        return comicsContents;
    }

    /**
     * 解析推荐的详细列表的信息
     *
     * @param res
     * @return
     */
    public static ArrayList<ComicsBean> parseRecommendData(JSONObject res) {
        ArrayList<ComicsBean> comicsBeans = new ArrayList<>();

        if (res != null) {
            try {

                JSONObject data = res.getJSONObject("data");

                JSONArray comics = data.getJSONArray("comics");
                Log.d(TAG, "res is null:"+comics.length());
                for (int i = 0; i < comics.length(); i++) {
                    JSONObject obj = comics.getJSONObject(i);

                    String comments_count = obj.getString("comments_count");
                    String cover_image_url = obj.getString("cover_image_url");
                    String created_at = obj.getString("created_at");
                    String id = obj.getString("id");
                    String is_liked = obj.getString("is_liked");
                    String likes_count = obj.getString("likes_count");
                    String shared_count = obj.getString("shared_count");
                    String title = obj.getString("title");
                    String updated_at = obj.getString("updated_at");
                    String url = obj.getString("url");

                    JSONObject topic = obj.getJSONObject("topic");
                    String comics_count = topic.getString("comics_count");
                    String topic_cover_image_url = topic.getString("cover_image_url");
                    String topic_created_at = topic.getString("created_at");
                    String description = topic.getString("description");
                    String topic_id = topic.getString("id");
                    String topic_order = topic.getString("order");
                    String topic_title = topic.getString("title");
                    String topic_updated_at = topic.getString("updated_at");
                    String vertical_image_url = topic.getString("vertical_image_url");


                    JSONObject user = topic.getJSONObject("user");
                    String avatar_url = user.getString("avatar_url");
                    String user_id = user.getString("id");
                    String nickname = user.getString("nickname");
                    String reg_type = user.getString("reg_type");


                    User userBean = new User(avatar_url, user_id, nickname, reg_type);


                    TopicBean topicBean = new TopicBean(
                            comics_count,
                            topic_cover_image_url,
                            topic_created_at,
                            description, topic_id,
                            topic_order,
                            topic_title,
                            topic_updated_at,
                            vertical_image_url,
                            userBean
                    );



                    ComicsBean comicsBean = new ComicsBean(
                            comments_count,
                            cover_image_url,
                            created_at,
                            title,
                            id,
                            is_liked,
                            likes_count,
                            shared_count,
                            updated_at,
                            url,
                            topicBean

                    );
                    comicsBeans.add(comicsBean);


                }

            } catch (Exception e) {

            }
        } else {
            Log.d(TAG, "res is null");
        }
        return comicsBeans;
    }

    /**
     * 解析推荐界面的item数据
     * @param res
     * @return
     */
    public static ArrayList<RecommendItem> parseRecommendItem(JSONObject res) {

         ArrayList<String> imagesUrl=null;
        ArrayList<RecommendItem> recommendItems=new ArrayList<>();

        if(res!=null){

            try {
                JSONObject data = res.getJSONObject("data");

                String created_at = data.getString("created_at");
                String id = data.getString("id");
                String likes_count = data.getString("likes_count");
                String previous_comic_id = data.getString("previous_comic_id");
                String title = data.getString("title");
                String updated_at = data.getString("updated_at");
                String url = data.getString("url");

                imagesUrl=new ArrayList<>();
                JSONArray images = data.getJSONArray("images");

                for (int i = 0; i < images.length(); i++) {
                    imagesUrl.add( images.getString(i));
                }

                JSONObject topic = data.getJSONObject("topic");
                String comics_count = topic.getString("comics_count");
                String topic_cover_image_url = topic.getString("cover_image_url");
                String topic_created_at = topic.getString("created_at");
                String description = topic.getString("description");
                String topic_id = topic.getString("id");
                String topic_order = topic.getString("order");
                String topic_title = topic.getString("title");
                String topic_updated_at = topic.getString("updated_at");
                String vertical_image_url = topic.getString("vertical_image_url");

                JSONObject user = topic.getJSONObject("user");
                String avatar_url = user.getString("avatar_url");
                String user_id = user.getString("id");
                String nickname = user.getString("nickname");
                String reg_type = user.getString("reg_type");


                User userBean = new User(avatar_url, user_id, nickname, reg_type);


                TopicBean topicBean = new TopicBean(
                        comics_count,
                        topic_cover_image_url,
                        topic_created_at,
                        description, topic_id,
                        topic_order,
                        topic_title,
                        topic_updated_at,
                        vertical_image_url,
                        userBean
                );

                RecommendItem recommendItem=
                        new RecommendItem(
                                imagesUrl,
                                created_at,
                                id,
                                likes_count,
                                previous_comic_id,
                                title,
                                updated_at,
                                url,
                                topicBean
                                ,userBean);
                recommendItems.add(recommendItem);
                Log.d(TAG, "RecommendItem:"+recommendItems.size());

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else {
            Log.d(TAG,"response is null");
        }


        return recommendItems;
    }


    /**
     * 解析user
     * @param res
     * @return
     */
    public static ArrayList<String> parseRecommendItemUser(JSONObject res) {
        ArrayList<String> list=new ArrayList<>();
      if (res!=null){
          try {

              JSONObject data = res.getJSONObject("data");
              if (data==null){

              }else {
              }
              String avatar_ur = data.getString("avatar_url");
              String intro = data.getString("intro");
              String nickname = data.getString("nickname");
              JSONArray topics = data.getJSONArray("topics");
              String title="";
              String description="";
              String cover_image_url="";
              for (int i = 0; i < topics.length(); i++) {
                  JSONObject jsonObject = topics.getJSONObject(i);
                   title = jsonObject.getString("title");
                   description = jsonObject.getString("description");
                   cover_image_url = jsonObject.getString("cover_image_url");

              }


              list.add(avatar_ur);
              list.add(intro);
              list.add(nickname);
              list.add(title);
              list.add(description);
              list.add(cover_image_url);
              Log.d(TAG, "parseRecommendItemUser res is null:"+list.size());
          }catch (Exception e){
              e.printStackTrace();
          }
      }else {
          Log.d(TAG,"parseRecommendItemUser res is null");
      }


        return list;
    }

    public static ArrayList<TopicBean> parseRecommendItemTopic(JSONObject res) {

        ArrayList<TopicBean> topicBeans=new ArrayList<>();

        if (res!=null){


            try {
                JSONObject data = res.getJSONObject("data");
                JSONArray comics = data.getJSONArray("comics");
                for (int i = 0; i < comics.length(); i++) {

                    JSONObject jsonObject = comics.getJSONObject(i);
                    String cover_image_url = jsonObject.getString("cover_image_url");
                    String id = jsonObject.getString("id");
                    String created_at = jsonObject.getString("created_at");
                    String likes_count = jsonObject.getString("likes_count");
                    String title = jsonObject.getString("title");
                    String topic_id = jsonObject.getString("topic_id");
                    String updated_at = jsonObject.getString("updated_at");
                    String url = jsonObject.getString("url");

                    TopicBean topicBean=new TopicBean(
                            likes_count,cover_image_url,created_at,
                            "",id,topic_id,title,updated_at,url,null);
                    topicBeans.add(topicBean);
                }

            }catch (Exception e){
                e.printStackTrace();
                Log.d(TAG, "parseRecommendItemTopic"+ e.getMessage());
            }

        }else {
            Log.d(TAG,"parseRecommendItemTopic res is null");
        }
        return topicBeans;
    }
}
