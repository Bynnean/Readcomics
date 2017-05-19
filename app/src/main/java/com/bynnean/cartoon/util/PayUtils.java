package com.bynnean.cartoon.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alipay.sdk.pay.PayEntity;

import java.util.ArrayList;

/**
 * ${desc}
 *
 * @author bin2.he@gmail.com
 * @date 16-10-9-上午11:17
 */
public class PayUtils {

    public static SharedPreferences mPreferences = null;

    public static  void init(Context context){
        mPreferences = context.getSharedPreferences("pay",  Context.MODE_PRIVATE);
    }


    public static  boolean isNeedPayOrder(String cartoonName){
        if(TextUtils.isEmpty(cartoonName)) return  true;
        else {
           String payInfo =  mPreferences.getString("payOrder","");
            if(TextUtils.isEmpty(payInfo)) return  true;
            else {
              PayEntity entity =  (PayEntity) JsonUtils.fromJson(payInfo, PayEntity.class);
              if(entity.getPayWay() == 1){
                  ArrayList<String> nameLists = entity.getBookSel();
                  if(nameLists.contains(cartoonName)) return  false;
                  else return  true;
              }else if(entity.getPayWay() == 3){
                  return  false;
              } else if(entity.getPayWay() == 2){
                  ArrayList<String> nameLists = entity.getBookSel();
                  if(null == nameLists || nameLists.size() < 5){
                      if(!nameLists.contains(cartoonName)){

                          return  true;
                      } else
                      return false;
                  } else if(nameLists.size() >=5){
                    if(nameLists.contains(cartoonName)) return  false;
                      else return  true;
                  } else return  true;
              } else return  true;
            }
        }
    }

    public static  void updateOrderInfo(int index,String orderId){
        if(TextUtils.isEmpty(orderId)) return;
        String payInfo =  mPreferences.getString("payOrder","");
        PayEntity entity = null;
        if(TextUtils.isEmpty(payInfo)) entity = new PayEntity();
        else entity =  (PayEntity) JsonUtils.fromJson(payInfo, PayEntity.class);
        entity.setPayWay(index);
        ArrayList<String> list = entity.getBookSel();
        list.add(orderId);
        entity.setBookSel(list);
        mPreferences.edit().putString("payOrder",JsonUtils.toJson(entity)).commit();
    }


    public static  void updateLoginState(boolean isLogin){
        mPreferences.edit().putBoolean("isLogin",isLogin).commit();
    }

    public static  boolean getLoginState(){
      return   mPreferences.getBoolean("isLogin",false);
    }
}
