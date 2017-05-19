package com.bhsoft.android.pay;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import android.util.Base64;

/**
 * ${desc}
 *
 * @author bin2.he@gmail.com
 * @date 17-2-14-下午4:54
 */
public class MiGuPay {


    // Apple  ID
    static String appleId[] = {"600000008365" , "600000008366", "600000008367"};
    static String channelId = "700004919";
    // Apple  KEY
    static String appKey[] =  {"ZILO850OAETYSZIM" ,"RJX9FLB8M2D4LRXW","ZGC9U7MOKW74SHIL"};

    static String baseConnect = "&";

    //  result url
    static String url;
    static String payCode[] = {"300008365001","300008366001","300008367001"};
    static String baseUrl = "http://wap.dm.10086.cn/apay/monthlyOrderHandle.jsp?";


    public static String  getPayUrl(int payState){
        String timeStamp = getTimeStamp();
        String randomString = getRandom();
        // 请求流水号
        url = baseUrl + "RequestID="+appleId[payState] + randomString + baseConnect;

        // 应用号
        url = url + "AppID=" + appleId[payState] + baseConnect;

        url = url + "PayCode="+ payCode[payState] + baseConnect;

        url = url + "TimeStamp=" + timeStamp + baseConnect ;

        url = url + "ChannelID=" + channelId + baseConnect;

        url = url + "Signature=" + getSignature(appleId[payState] + randomString , appleId[payState] , appKey[payState] , payCode[payState]  , timeStamp , channelId);
        return url;
    }



    private static String getTimeStamp(){
        String timeStamp = ""+ Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0,10));;
        return timeStamp;
    }

    // 签名
    public static String getSignature(String RequestID , String appleID , String AppKey , String PayCode ,String  TimeStamp , String  ChannelID){

        String signerFigner = RequestID + baseConnect + appleID + baseConnect + AppKey + baseConnect + PayCode + baseConnect + TimeStamp + baseConnect + ChannelID ;
        // 先进行md5算法
        String md5Value  = getMd5Value(signerFigner);

        md5Value = md5Value.toUpperCase();

        String base64value = Base64.encodeToString(md5Value.getBytes(),Base64.DEFAULT);

        try {
            base64value = URLEncoder.encode(base64value, "UTF-8");
            base64value = URLEncoder.encode(base64value, "UTF-8");
        } catch (Exception e) {
            // TODO: handle exception
        }


        return base64value;
    }

    static String getRandom(){
        Random random = new Random();
        return "" + random.nextInt(1 * 10000000);
    }



    // md5 算法
    public static String getMd5Value(String sSecret) {
        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(sSecret.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();// 加密
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
