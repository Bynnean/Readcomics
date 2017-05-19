package com.bynnean.cartoon.bean;

/**
 * Created by Administrator on 2015/11/18.
 */
//"id", "topic_title", "nickname","data_title", "vertical_image_url"
public class Collect {
    private String id;
    private String topic_title;
    private String nickname;
    private String data_title;
    private String vertical_image_url;

    public Collect(String id, String topic_title,
                   String nickname, String data_title,
                   String vertical_image_url) {
        this.id = id;
        this.topic_title = topic_title;
        this.nickname = nickname;
        this.data_title = data_title;
        this.vertical_image_url = vertical_image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic_title() {
        return topic_title;
    }

    public void setTopic_title(String topic_title) {
        this.topic_title = topic_title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getData_title() {
        return data_title;
    }

    public void setData_title(String data_title) {
        this.data_title = data_title;
    }

    public String getVertical_image_url() {
        return vertical_image_url;
    }

    public void setVertical_image_url(String vertical_image_url) {
        this.vertical_image_url = vertical_image_url;
    }

    @Override
    public String toString() {
        return "Collect{" +
                "id='" + id + '\'' +
                ", topic_title='" + topic_title + '\'' +
                ", nickname='" + nickname + '\'' +
                ", data_title='" + data_title + '\'' +
                ", vertical_image_url='" + vertical_image_url + '\'' +
                '}';
    }
}
