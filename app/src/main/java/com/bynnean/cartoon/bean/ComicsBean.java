package com.bynnean.cartoon.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author：Administrator on 2015/11/16 13:47
 * Email：709272013@qq.com
 */
public class ComicsBean implements Parcelable{

    public String comments_count;
    public String cover_image_url;
    public String created_at;
    public String title;
    public String id;
    public String is_liked;
    public String likes_count;
    public String shared_count;
    public String updated_at;
    public String url;
    public TopicBean topicBean;

    public ComicsBean(String comments_count, String cover_image_url,
                      String created_at, String title, String id,
                      String is_liked, String likes_count,
                      String shared_count, String updated_at,
                      String url, TopicBean topicBean) {
        this.comments_count = comments_count;
        this.cover_image_url = cover_image_url;
        this.created_at = created_at;
        this.title = title;
        this.id = id;
        this.is_liked = is_liked;
        this.likes_count = likes_count;
        this.shared_count = shared_count;
        this.updated_at = updated_at;
        this.url = url;
        this.topicBean = topicBean;
    }

    public ComicsBean() {
    }

    protected ComicsBean(Parcel in) {
        comments_count = in.readString();
        cover_image_url = in.readString();
        created_at = in.readString();
        title = in.readString();
        id = in.readString();
        is_liked = in.readString();
        likes_count = in.readString();
        shared_count = in.readString();
        updated_at = in.readString();
        url = in.readString();
        topicBean = in.readParcelable(TopicBean.class.getClassLoader());
    }

    public static final Creator<ComicsBean> CREATOR = new Creator<ComicsBean>() {
        @Override
        public ComicsBean createFromParcel(Parcel in) {
            return new ComicsBean(in);
        }

        @Override
        public ComicsBean[] newArray(int size) {
            return new ComicsBean[size];
        }
    };

    @Override
    public String toString() {
        return "ComicsBean{" +
                "comments_count='" + comments_count + '\'' +
                ", cover_image_url='" + cover_image_url + '\'' +
                ", created_at='" + created_at + '\'' +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", is_liked='" + is_liked + '\'' +
                ", likes_count='" + likes_count + '\'' +
                ", shared_count='" + shared_count + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", url='" + url + '\'' +
                ", topicBean=" + topicBean +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(comments_count);
        dest.writeString(cover_image_url);
        dest.writeString(created_at);
        dest.writeString(title);
        dest.writeString(id);
        dest.writeString(is_liked);
        dest.writeString(likes_count);
        dest.writeString(shared_count);
        dest.writeString(updated_at);
        dest.writeString(url);
        dest.writeParcelable(topicBean, flags);
    }
}
  /*  "comments_count":1777,
            "cover_image_url":"http://i.kuaikanmanhua.com/image/151116/za5lw95ix.jpg-w640",
            "created_at":1447688905,
            "id":7282,
            "is_liked":false,
            "likes_count":27091,
            "shared_count":1135,
            "title":"第16话",
            "topic":Object{...},
            "updated_at":1447641602,
//            "url":"http://www.kuaikanmanhua.com/comics/7282"*/


