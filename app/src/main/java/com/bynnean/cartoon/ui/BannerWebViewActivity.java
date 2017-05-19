package com.bynnean.cartoon.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bynnean.cartoon.R;

/**
 * Created by 李树华 on 2015/11/18.
 */
public class BannerWebViewActivity extends AppCompatActivity{
    private WebView mWebview;
    private ImageView mShare;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_webview);
        mShare=(ImageView)findViewById(R.id.share_webview);

        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShare.setSelected(true);
                //调用系统分享 通用版
                Intent intentsh = new Intent(Intent.ACTION_SEND);
                intentsh.setType("image/*");
                intentsh.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intentsh.putExtra(Intent.EXTRA_TEXT, "好像，这个是可行的哦");
                intentsh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intentsh, "分享到---"));
            }
        });

        mWebview= (WebView) findViewById(R.id.webview);
        String url=getIntent().getStringExtra("url");
        mWebview.loadUrl(url);

    }

    //取消
    public void onCancel(View view) {
        finish();
    }

    //分享
    public void onShare(View view) {

    }
}
