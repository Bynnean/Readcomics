package com.bynnean.cartoon.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author：Administrator on 2015/11/16 10:02
 * Email：709272013@qq.com
 */
public class SearchBean implements Parcelable{

//    "icon":"http://i.kuaikanmanhua.com/image/150814/rcst5xekw.jpg-w200",
//            "priority":14,
//            "tag_id":30,
//            "title":"HOT"

    public String icon;
    public String priority;
    public String tag_id;
    public String title;

    public SearchBean(String icon, String priority, String tag_id, String title) {
        this.icon = icon;
        this.priority = priority;
        this.tag_id = tag_id;
        this.title = title;
    }

    public SearchBean() {
    }

    protected SearchBean(Parcel in) {
        icon = in.readString();
        priority = in.readString();
        tag_id = in.readString();
        title = in.readString();
    }

    public static final Creator<SearchBean> CREATOR = new Creator<SearchBean>() {
        @Override
        public SearchBean createFromParcel(Parcel in) {
            return new SearchBean(in);
        }

        @Override
        public SearchBean[] newArray(int size) {
            return new SearchBean[size];
        }
    };

    @Override
    public String toString() {
        return "SearchBean{" +
                "icon='" + icon + '\'' +
                ", priority='" + priority + '\'' +
                ", tag_id='" + tag_id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeString(priority);
        dest.writeString(tag_id);
        dest.writeString(title);
    }
}
