package com.bynnean.cartoon.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Author：Administrator on 2015/11/17 13:46
 * Email：709272013@qq.com
 */
public class RecommendItem implements Parcelable{


    public ArrayList<String> images;
    public String created_at;
    public String id;
    public String likes_count;
    public String previous_comic_id;
    public String title;
    public String updated_at;
    public String url;
    public TopicBean topicBean;
    public User user;

    public RecommendItem(ArrayList<String> images, String created_at,
                         String id, String likes_count, String previous_comic_id,
                         String title, String updated_at, String url,
                         TopicBean topicBean, User user) {
        this.images = images;
        this.created_at = created_at;
        this.id = id;
        this.likes_count = likes_count;
        this.previous_comic_id = previous_comic_id;
        this.title = title;
        this.updated_at = updated_at;
        this.url = url;
        this.topicBean = topicBean;
        this.user = user;
    }

    public RecommendItem() {
    }

    protected RecommendItem(Parcel in) {
        images = in.createStringArrayList();
        created_at = in.readString();
        id = in.readString();
        likes_count = in.readString();
        previous_comic_id = in.readString();
        title = in.readString();
        updated_at = in.readString();
        url = in.readString();
        topicBean = in.readParcelable(TopicBean.class.getClassLoader());
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<RecommendItem> CREATOR = new Creator<RecommendItem>() {
        @Override
        public RecommendItem createFromParcel(Parcel in) {
            return new RecommendItem(in);
        }

        @Override
        public RecommendItem[] newArray(int size) {
            return new RecommendItem[size];
        }
    };

    @Override
    public String toString() {
        return "RecommendItem{" +
                "images=" + images +
                ", created_at='" + created_at + '\'' +
                ", id='" + id + '\'' +
                ", likes_count='" + likes_count + '\'' +
                ", previous_comic_id='" + previous_comic_id + '\'' +
                ", title='" + title + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", url='" + url + '\'' +
                ", topicBean=" + topicBean +
                ", user=" + user +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(images);
        dest.writeString(created_at);
        dest.writeString(id);
        dest.writeString(likes_count);
        dest.writeString(previous_comic_id);
        dest.writeString(title);
        dest.writeString(updated_at);
        dest.writeString(url);
        dest.writeParcelable(topicBean, flags);
        dest.writeParcelable(user, flags);
    }
}
