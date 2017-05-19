package com.bynnean.cartoon.config;

/**
 * Created by Administrator on 2015/11/14.
 */
public class API {

    /**
     * GET请求
     * 推荐的API，offset为参数，当offset=0代表只有一页的数据为20条，当offset=20代表有2页的数据，总共40条20条
     * limit=20代表每页数据显示20条
     */

    public static final String RECOMMENDAPI=
            "http://api.kuaikanmanhua.com/v1/comic_lists/1?limit=20&offset=";


    /**
     * 推荐页面的item点击事件
     */
    public static final String RECOMMEND_ITEM_IMAGE=
            "http://api.kuaikanmanhua.com/v1/comics/";




    /**
     * GET请求
     * 推荐页面的搜索
     */
    public static final String SERACH =
            "http://api.kuaikanmanhua.com/v1/tag/suggestion ";

//    /**
//     * 推荐页面的每一个item的的最新评论
//     */
//    public static final String LATEST_COMMENTS =
//            "http://api.kuaikanmanhua.com/v1/comics/7262/comments?order=&limit=20&offset=";
//
//
//    /**
//     * 推荐页面的每一个item的的最热评论
//      */
//    public static final String HOT_COMMENTS=
//            "http://api.kuaikanmanhua.com/v1/comics/7262/comments?order=score&limit=20&offset=";

    /**
     * 发现这个页面的主页面
     */
    public static final String FIND_MIXED =
            "http://api.kuaikanmanhua.com/v1/topic_lists/mixed";

    /**
     * 发现这个页面的头部AIP
     */
    public static final String FIND_BANNERS =
            "http://api.kuaikanmanhua.com/v1/banners";

//    public static final String FIND_BANNERS_ITEM =
//            "http://api.kuaikanmanhua.com/v1/comics/6196/hot_comments/";

     /* *
     * 发现页面的查看更多
     * http://api.kuaikanmanhua.com/v1/topic_lists/1?offset=0
     * */
    public static final String MORE1=
            "http://api.kuaikanmanhua.com/v1/";
    public static final String MORE2=
            "?offset=0 ";


    /**
     * 点击搜索的时候的AIP
     */
    public static String READ_SEARCH="http://api.kuaikanmanhua.com/v1/tag/suggestion";


    public static String SEARCH_KEYWORD =
            "http://api.kuaikanmanhua.com/v1/topics/search?offset=0&limit=20&keyword=";

    /**
     * 点击搜索界面的item的监听事件
     */
    public static String SEARCH_HOT="http://api.kuaikanmanhua.com/v1/topics?offset=0&limit=20&tag=";

    /**
     * 推荐页面点击用户头像的时候使用到的api
     */
    public static  String RECOMMEND_ITEM_USER =
            "http://api.kuaikanmanhua.com/v1/users/";

    /**
     * 推荐页面的topic内容
     */
    public static String RECOMMEND_ITEM_TOPIC=
            "http://api.kuaikanmanhua.com/v1/topics/";
}
