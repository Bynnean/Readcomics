package com.bynnean.cartoon;

import android.app.Application;

import com.bynnean.cartoon.util.PayUtils;

import org.xutils.x;


public class ComicApllication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志
        initCache();
    }

    private void initCache(){
        PayUtils.init(getApplicationContext());
    }
}
