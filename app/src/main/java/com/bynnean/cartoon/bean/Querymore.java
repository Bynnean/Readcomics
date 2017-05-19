package com.bynnean.cartoon.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/11/17.
 */
public class Querymore implements Parcelable{
   /* "comics_count":9,
            "comments_count":131918,
            "cover_image_url":"http://i.kuaikanmanhua.com/image/151009/44vxhcjdx.jpg-w640",
            "created_at":1442563580,
            "description":"一个长相普通的职场女青年，无意中下载了一个整容APP...初尝了变美的禁果，从此踏上了一条不归路——【独家新作/周六更新 责编：Nico】",
            "id":544,
            "is_favourite":false,
            "likes_count":2962642,
            "order":0,
            "title":"整容游戏",
            "updated_at":1442563580,
            "user":{
        "avatar_url":"http://i.kuaikanmanhua.com/image/150918/jdla02iby.jpg-w180",
                "id":2967943,
                "nickname":"金丘",
                "reg_type":"author"
    },
            "vertical_image_url":"http://i.kuaikanmanhua.com/image/150925/24lanbu6m.jpg-w320"*/

    private String cover_image_url;
    private String comments_count;
    private String description;
    private String id;
    private String title;
    private String nickname;
    private String likes_count;

    public Querymore() {
    }

    public Querymore(String cover_image_url,
                     String comments_count,
                     String description,
                     String id,
                     String title,
                     String nickname,
                     String likes_count) {
        this.cover_image_url = cover_image_url;
        this.comments_count = comments_count;
        this.description = description;
        this.id = id;
        this.title = title;
        this.nickname = nickname;
        this.likes_count=likes_count;
    }

    protected Querymore(Parcel in) {
        cover_image_url = in.readString();
        comments_count = in.readString();
        description = in.readString();
        id = in.readString();
        title = in.readString();
        nickname = in.readString();
        likes_count=in.readString();
    }

    public static final Creator<Querymore> CREATOR = new Creator<Querymore>() {
        @Override
        public Querymore createFromParcel(Parcel in) {
            return new Querymore(in);
        }

        @Override
        public Querymore[] newArray(int size) {
            return new Querymore[size];
        }
    };

    public String getCover_image_url() {
        return cover_image_url;
    }

    public void setCover_image_url(String cover_image_url) {
        this.cover_image_url = cover_image_url;
    }

    public String getComments_count() {
        return comments_count;
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(String likes_count) {
        this.likes_count = likes_count;
    }

    @Override
    public String toString() {
        return "Querymore{" +
                "cover_image_url='" + cover_image_url + '\'' +
                ", comments_count='" + comments_count + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", nickname='" + nickname + '\'' +
                ", likes_count='" + likes_count + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cover_image_url);
        dest.writeString(comments_count);
        dest.writeString(description);
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(nickname);
        dest.writeString(likes_count);
    }
}

