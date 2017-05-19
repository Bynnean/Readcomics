package com.bynnean.cartoon.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/11/17.
 */
public class ComicsContent implements Parcelable {
  /*  "cover_image_url":"http://i.kuaikanmanhua.com/image/151114/490oq06lv.jpg-w640",
            "created_at":1447454377,
            "id":7262,
            "likes_count":283747,
            "title":"第8话 原来你跟着我的目的竟然是....！",
            "topic_id":544,
            "updated_at":1447453805,
            "url":"http://www.kuaikanmanhua.com/comics/7262"*/
    private String cover_image_url;
    private String created_at;
    private String id;
    private String likes_count;
    private String title;
    private String url;
    private String updated_at;

    public ComicsContent() {
    }

    public ComicsContent(String cover_image_url,
                         String created_at,
                         String id,
                         String likes_count,
                         String title,
                         String url,
                         String updated_at) {
        this.cover_image_url = cover_image_url;
        this.created_at = created_at;
        this.id = id;
        this.likes_count = likes_count;
        this.title = title;
        this.url = url;
        this.updated_at = updated_at;
    }

    protected ComicsContent(Parcel in) {
        cover_image_url = in.readString();
        created_at = in.readString();
        id = in.readString();
        likes_count = in.readString();
        title = in.readString();
        url = in.readString();
        updated_at = in.readString();
    }

    public static final Creator<ComicsContent> CREATOR = new Creator<ComicsContent>() {
        @Override
        public ComicsContent createFromParcel(Parcel in) {
            return new ComicsContent(in);
        }

        @Override
        public ComicsContent[] newArray(int size) {
            return new ComicsContent[size];
        }
    };

    public String getCover_image_url() {
        return cover_image_url;
    }

    public void setCover_image_url(String cover_image_url) {
        this.cover_image_url = cover_image_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(String likes_count) {
        this.likes_count = likes_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "ComicsContent{" +
                "cover_image_url='" + cover_image_url + '\'' +
                ", created_at='" + created_at + '\'' +
                ", id='" + id + '\'' +
                ", likes_count='" + likes_count + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cover_image_url);
        dest.writeString(created_at);
        dest.writeString(id);
        dest.writeString(likes_count);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(updated_at);
    }
}
