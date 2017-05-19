package com.bynnean.cartoon.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper dbHelper;
    public static final String DBNAME = "manhua.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_FOLLOWDATA = "kuaikan";
    public static final String TABLE_NAME_collect = "collect";
    //Store follow data
    //id  漫画的唯一标识
    //data_title漫画子标题
    //topic_title漫画主标题
    //nickname作者
    //vertical_image_url 漫画图片地址
    private static final String sql_followdata = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_FOLLOWDATA + " ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "id INTEGER UNIQUE NOT NULL,"
            + "data_title VARCHAR(50),"
            + "topic_title VARCHAR(50),"
            + "nickname VARCHAR(50),"
            + "vertical_image_url VARCHAR(50)"
            + ")";
    //Storage collection data
    //id  漫画的唯一标识
    //data_title漫画子标题
    //topic_title漫画主标题
    //nickname作者
    //vertical_image_url跳转地址
    private static final String sql_collect = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_collect + " ("
            + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "id INTEGER UNIQUE NOT NULL,"
            + "data_title VARCHAR(50),"
            + "topic_title VARCHAR(50),"
            + "nickname VARCHAR(50),"
            + "vertical_image_url VARCHAR(50)"
            + ")";

    public DBHelper(Context context) {
        super(context, DBNAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql_followdata);
        db.execSQL(sql_collect);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
