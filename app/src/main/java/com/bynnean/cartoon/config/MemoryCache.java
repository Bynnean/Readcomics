package com.bynnean.cartoon.config;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2015/11/3.
 */
public class MemoryCache implements ImageLoader.ImageCache {

    private LruCache<String,Bitmap> mCache;

    public MemoryCache() {
        //初始化缓存对象并设置大小
        /**
         * maxsize的值代表容器的大小：
         *       如果重写了LurCache的sizeof方法的话，maxsize就代表容器所占用的字节数
         *       如果没有重写sizeof方法的话，maxsize就代表容器所能容纳的对象个数
         */
        mCache=new LruCache<>(1000);
    }
    @Override
    public Bitmap getBitmap(String url) {
        //从缓存里面拿出一张对象
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        //给缓存容器里添加一个对象
        mCache.put(url,bitmap);
    }
}
