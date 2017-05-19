package com.bynnean.cartoon.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author：Administrator on 2015/11/16 13:50
 * Email：709272013@qq.com
 */
public class TopicBean implements Parcelable{


    public String comics_count;
    public String cover_image_url;
    public String created_at;
    public String description;
    public String id;
    public String order;
    public String title;
    public String updated_at;
    public String vertical_image_url;
    public User user;

    public TopicBean(String comics_count, String cover_image_url,
                     String created_at, String description, String id,
                     String order,
                     String title, String updated_at,
                     String vertical_image_url, User user) {
        this.comics_count = comics_count;
        this.cover_image_url = cover_image_url;
        this.created_at = created_at;
        this.description = description;
        this.id = id;
        this.order = order;
        this.title = title;
        this.updated_at = updated_at;
        this.vertical_image_url = vertical_image_url;
        this.user = user;
    }

    public TopicBean() {
    }

    protected TopicBean(Parcel in) {
        comics_count = in.readString();
        cover_image_url = in.readString();
        created_at = in.readString();
        description = in.readString();
        id = in.readString();
        order = in.readString();
        title = in.readString();
        updated_at = in.readString();
        vertical_image_url = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<TopicBean> CREATOR = new Creator<TopicBean>() {
        @Override
        public TopicBean createFromParcel(Parcel in) {
            return new TopicBean(in);
        }

        @Override
        public TopicBean[] newArray(int size) {
            return new TopicBean[size];
        }
    };

    @Override
    public String toString() {
        return "TopicBean{" +
                "comics_count='" + comics_count + '\'' +
                ", cover_image_url='" + cover_image_url + '\'' +
                ", created_at='" + created_at + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", order='" + order + '\'' +
                ", title='" + title + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", vertical_image_url='" + vertical_image_url + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(comics_count);
        dest.writeString(cover_image_url);
        dest.writeString(created_at);
        dest.writeString(description);
        dest.writeString(id);
        dest.writeString(order);
        dest.writeString(title);
        dest.writeString(updated_at);
        dest.writeString(vertical_image_url);
        dest.writeParcelable(user, flags);
    }
}
