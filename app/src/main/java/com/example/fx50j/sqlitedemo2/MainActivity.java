package com.example.fx50j.sqlitedemo2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseAdpter dbHelper;
    private Button btu_create;
    private Button btu_add;
    private Button btu_refresh;
    private Button btu_delete;
    private Button btu_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseAdpter(this,"BookStore.db",null,2);
        btu_create = (Button) findViewById(R.id.btu_create);
        btu_add = (Button) findViewById(R.id.btu_adddata);
        btu_refresh = (Button) findViewById(R.id.btu_refreshdata);
        btu_delete = (Button) findViewById(R.id.btu_deletedata);
        btu_check = (Button) findViewById(R.id.btu_checkdata);

        //点击创建并更新数据库
        btu_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        //点击添加数据
        btu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name","书名1");
                values.put("author","作者1");
                values.put("pages",99);
                values.put("price",12);
                db.insert("Book", null, values);
                values.clear();
                values.put("name", "书名2");
                values.put("author", "作者2");
                values.put("pages", 50);
                values.put("price",13);
                db.insert("Book",null,values);
            }
        });

        //点击更新数据
        btu_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",15);
                db.update("Book",values,"name = ?",new String[]{"书名1"});
            }
        });

        //点击删除数据
        btu_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Book",null,null);
            }
        });

        //点击查询数据
        btu_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do{
                        //遍历Cursor对象，取出数据并打印
                        String name  = cursor.getString(cursor.getColumnIndex("name"));
                        String author  = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("data",name);
                        Log.d("data",author);
                        Log.d("data", String.valueOf(pages));
                        Log.d("data", String.valueOf(price));
                        Log.d("data"," ");
                    }while (cursor.moveToNext());
                }
            }
        });
    }
}
