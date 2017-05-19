package com.bynnean.cartoon.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.config.API;
import com.bynnean.cartoon.db.DBHelper;
import com.bynnean.cartoon.db.DBManager;

public class MyFollowActivity extends AppCompatActivity {

    private DBManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follow);
        ImageButton follback = (ImageButton) findViewById(R.id.follow_back);
        follback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MyFollowActivity.this, IndividualFragment.class);
//                startActivity(intent);
                MyFollowActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.del:
                manager = new DBManager(this);
                long id = info.id;
                boolean flag = manager.delete(DBHelper.TABLE_NAME_FOLLOWDATA, String.valueOf(id));
                if (!flag) {
                    Toast.makeText(this, "删除中。。。", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(this, MyFollowActivity.class);
                    finish();
                    startActivity(intent);
                    Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "删除失败？！", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
        return super.onContextItemSelected(item);

    }
    //
    public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
        Intent intent = new Intent(this,
                MyFollowActivity.class);

        ImageView vertical_image_url = (ImageView) view.findViewById(R.id.follow_image);
        TextView topic_title = (TextView) view.findViewById(R.id.follow_name);
        TextView  nickname= (TextView) view.findViewById(R.id.follow_author);
        TextView  pageid= (TextView) view.findViewById(R.id.follow_id);

        TextView data_title = (TextView) view.findViewById(R.id.follow_abstract);
        String id = pageid.toString();
        String image_url = vertical_image_url.toString();
        String topictitle = topic_title.getText().toString();
        String author = nickname.getText().toString();

        String path = API.RECOMMEND_ITEM_IMAGE + "&id=" + id;
        String datatitle = data_title.getText().toString();
        intent.putExtra("vertical_image_url", image_url);
        intent.putExtra("topic_title", topictitle);
        intent.putExtra("nickname", author);
        intent.putExtra("path", path);
        intent.putExtra("data_title", datatitle);

        startActivity(intent);
    }

}
