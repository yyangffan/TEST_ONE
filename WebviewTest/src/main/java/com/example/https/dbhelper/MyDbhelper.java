package com.example.https.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yangfan on 2017/5/13.
 */

public class MyDbhelper extends SQLiteOpenHelper {
    private String name;
    public MyDbhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.name=name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table"+ name+"(_id integer primary key autoincrement,url text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
