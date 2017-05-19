package com.bynnean.cartoon.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author：Administrator on 2015/11/16 14:15
 * Email：709272013@qq.com
 */
public class User implements Parcelable{
//    "avatar_url":"http://i.kuaikanmanhua.com/image/150706/49kpat7nq.jpg-w180",
//            "id":459327,
//            "nickname":"青庭",
//            "reg_type":"author"

    public String avatar_url;
    public String id;
    public String nickname;
    public String reg_type;

    public User(String avatar_url, String id, String nickname, String reg_type) {
        this.avatar_url = avatar_url;
        this.id = id;
        this.nickname = nickname;
        this.reg_type = reg_type;
    }

    public User() {
    }

    protected User(Parcel in) {
        avatar_url = in.readString();
        id = in.readString();
        nickname = in.readString();
        reg_type = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "avatar_url='" + avatar_url + '\'' +
                ", id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", reg_type='" + reg_type + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatar_url);
        dest.writeString(id);
        dest.writeString(nickname);
        dest.writeString(reg_type);
    }
}
