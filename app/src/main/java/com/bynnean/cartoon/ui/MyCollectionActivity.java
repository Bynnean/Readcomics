package com.bynnean.cartoon.ui;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.bynnean.cartoon.R;
import com.bynnean.cartoon.adapter.CollectAdapter;
import com.bynnean.cartoon.bean.Collect;
import com.bynnean.cartoon.db.DBHelper;
import com.bynnean.cartoon.db.DBManager;

import java.util.ArrayList;


public class MyCollectionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private DBManager manager;
    private CollectAdapter adapter;
    private ListView listview;
    private ArrayList<Collect> datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);

        manager = new DBManager(this);
        datalist = new ArrayList<Collect>();

        listview = (ListView) findViewById(R.id.listview_collection);

        Cursor cursor = manager.query(DBHelper.TABLE_NAME_collect);

        datalist = manager.seclectData();

        adapter = new CollectAdapter(this, datalist);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(this);


        ImageButton collwback = (ImageButton) findViewById(R.id.collection_back);
        collwback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCollectionActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent();
        intent.putExtra("itemId",datalist.get(position).getId());
        intent.setClass(MyCollectionActivity.this,RecommendItemActivity.class);
        startActivity(intent);
    }
}
