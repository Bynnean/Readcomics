package com.bynnean.cartoon.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 李树华 on 2015/11/16.
 */
public class FindTopic implements Parcelable{
    private String id;
    private String title;//标题
    private String nickname;//作者
    private String imagePath;//封面

    public FindTopic() {
    }

    public FindTopic(String id, String title, String nickname, String imagePath) {
        this.id = id;
        this.title = title;
        this.nickname=nickname;
        this.imagePath = imagePath;
    }

    protected FindTopic(Parcel in) {
        id = in.readString();
        title = in.readString();
        nickname = in.readString();
        imagePath = in.readString();
    }

    public static final Creator<FindTopic> CREATOR = new Creator<FindTopic>() {
        @Override
        public FindTopic createFromParcel(Parcel in) {
            return new FindTopic(in);
        }

        @Override
        public FindTopic[] newArray(int size) {
            return new FindTopic[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "FindTopic{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", nickname='" + nickname + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(nickname);
        dest.writeString(imagePath);
    }
}
