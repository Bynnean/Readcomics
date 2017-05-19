package com.bynnean.cartoon.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/11/17.
 */
public class Banner implements Parcelable{
    /*"pic":"http://i.kuaikanmanhua.com/image/151112/2pmokoufz.jpg-w640",
            "title":"关于我最喜欢的他",
            "type":1,
            "value":"http://www.kuaikanmanhua.com/act/bookcart_new.html"*/
    private String pic;
    private String title;
    private String value;

    public Banner() {
    }

    public Banner(String pic, String title, String value) {
        this.pic = pic;
        this.title = title;
        this.value = value;
    }

    protected Banner(Parcel in) {
        pic = in.readString();
        title = in.readString();
        value = in.readString();
    }

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel in) {
            return new Banner(in);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "pic='" + pic + '\'' +
                ", title='" + title + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pic);
        dest.writeString(title);
        dest.writeString(value);
    }
}
