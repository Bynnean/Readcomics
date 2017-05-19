package com.bynnean.cartoon.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bynnean.cartoon.bean.Collect;

import java.util.ArrayList;

public class DBManager {
    DBHelper helper;

    public DBManager(Context context) {
        helper = new DBHelper(context);
    }

    public boolean insertSQLite(Context context, String tableName,
                                ContentValues values) {
        SQLiteDatabase database = helper
                .getWritableDatabase();
        long raw = database.insert(tableName, null, values);
        database.close();
        if (raw > 0) {
            return true;
        } else {
            return false;
        }
    }


    public  ArrayList<Collect> seclectData(){
        ArrayList<Collect> list=new ArrayList();

        Cursor cursor = null;
        String sql="select * from collect";
        SQLiteDatabase database = helper.getReadableDatabase();
       cursor=database.rawQuery(sql,null);
        while (cursor.moveToNext()){
            String id=cursor.getString(cursor.getColumnIndex("id"));
            String data_title=cursor.getString(cursor.getColumnIndex("data_title"));
            String topic_title=cursor.getString(cursor.getColumnIndex("topic_title"));
            String nickname=cursor.getString(cursor.getColumnIndex("nickname"));
            String vertical_image_url=cursor.getString(cursor.getColumnIndex("vertical_image_url"));
            Collect collect=new Collect(id,topic_title,nickname,data_title,vertical_image_url);
            list.add(collect);
        }
        return list;
    }



    public Cursor query(String fileName) {
        Cursor cursor = null;
        SQLiteDatabase database = helper.getReadableDatabase();
        cursor = database.query(fileName, null, null, null, null, null, null);
        return cursor;
    }

    public boolean delete(String fileName, String id) {
        SQLiteDatabase database = null;
        long new_id = 0;
        try {
            database = helper.getWritableDatabase();
            database.delete(fileName, " _id  = ? ",
                    new String[]{id});
            return new_id > 0 ? true : false;
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return false;
    }
}
