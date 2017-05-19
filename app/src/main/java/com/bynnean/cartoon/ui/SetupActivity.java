package com.bynnean.cartoon.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bynnean.cartoon.R;

public class SetupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        //返回按钮
        ImageButton setupback = (ImageButton) findViewById(R.id.setup_back);
        setupback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(SetupActivity.this, IndividualFragment.class);
//                startActivity(intent);
                SetupActivity.this.finish();
            }
        });


        //赞个好评
        LinearLayout good = (LinearLayout) findViewById(R.id.setupGood);
        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SetupActivity.this,"Thankyou for your favourable comment！",Toast.LENGTH_SHORT).show();
            }
        });

//        //意见反馈
//        LinearLayout sug = (LinearLayout) findViewById(R.id.setupSuggestion);
//        sug.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(SetupActivity.this,"wait for a moument!",Toast.LENGTH_SHORT).show();
//            }
//        });

        //推荐给好友   分享
        LinearLayout send = (LinearLayout) findViewById(R.id.setupSend);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用系统分享 通用版
                Intent intentsh = new Intent(Intent.ACTION_SEND);
                intentsh.setType("image/*");
                intentsh.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intentsh.putExtra(Intent.EXTRA_TEXT, "好像，这个是可行的哦");

                intentsh.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intentsh, "分享到---"));
            }
        });

        //清理缓存
        LinearLayout clean = (LinearLayout) findViewById(R.id.setupClean);
        clean .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SetupActivity.this,"清理完成!",Toast.LENGTH_SHORT).show();
            }
        });

        //关于本软件
        LinearLayout about = (LinearLayout) findViewById(R.id.setupAbout);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intentab = new Intent(SetupActivity.this,CopyrightActivity.class);
                startActivity(intentab);
            }
        });

        //软件更新
        LinearLayout setup = (LinearLayout) findViewById(R.id.setupUp);
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SetupActivity.this,"已经是最新版本了哦~",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
