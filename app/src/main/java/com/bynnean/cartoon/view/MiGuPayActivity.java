package com.bynnean.cartoon.view;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.bynnean.cartoon.R;

/**
 * 加载咪咕支付页面
 *
 * @author bin2.he@gmail.com
 * @date 17-2-14-下午5:19
 */
public class MiGuPayActivity extends Activity{

    public static  String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.migu_pay_layout);
        WebView webView = (WebView) findViewById(R.id.layout_pay);
        webView.loadUrl(url);
    }
}
