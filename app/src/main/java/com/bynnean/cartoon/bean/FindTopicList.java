package com.bynnean.cartoon.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by 李树华 on 2015/11/16.
 */
public class FindTopicList implements Parcelable{
    private String action;
    private String topics_title;
    private ArrayList<FindTopic> list;

    public FindTopicList() {
    }

    public FindTopicList(String action, String topics_title, ArrayList<FindTopic> list) {
        this.action = action;
        this.topics_title = topics_title;
        this.list = list;
    }

    public FindTopicList(String topics_title, ArrayList<FindTopic> list) {
        this.topics_title = topics_title;
        this.list = list;
    }

    protected FindTopicList(Parcel in) {
        action = in.readString();
        topics_title = in.readString();
        list = in.createTypedArrayList(FindTopic.CREATOR);
    }

    public static final Creator<FindTopicList> CREATOR = new Creator<FindTopicList>() {
        @Override
        public FindTopicList createFromParcel(Parcel in) {
            return new FindTopicList(in);
        }

        @Override
        public FindTopicList[] newArray(int size) {
            return new FindTopicList[size];
        }
    };

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTopics_title() {
        return topics_title;
    }

    public void setTopics_title(String topics_title) {
        this.topics_title = topics_title;
    }

    public ArrayList<FindTopic> getList() {
        return list;
    }

    public void setList(ArrayList<FindTopic> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "FindTopicList{" +
                "topics_title='" + topics_title + '\'' +
                ", list=" + list +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(action);
        dest.writeString(topics_title);
        dest.writeTypedList(list);
    }
}
