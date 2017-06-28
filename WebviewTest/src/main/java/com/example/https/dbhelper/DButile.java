package com.example.https.dbhelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangfan on 2017/5/13.
 */

public class DButile {
    private Activity mContext;
    private MyDbhelper mMyDbhelper;
    private SQLiteDatabase db;
    private List<String> mList;

    public DButile(Activity context) {
        mContext = context;
        mList = new ArrayList<>();
        mMyDbhelper = new MyDbhelper(context, "user", null, 1);
    }

    public void creatTable(String name) {
        db = mMyDbhelper.getWritableDatabase();
        db.execSQL("create table " + name + "(_id integer primary key autoincrement,url text)");
        db.close();
    }

    /**
     * 去掉集合中重复的数据
     */
    public void removeRepeat() {

    }

    /**
     * 将集合添加到数据库中
     *
     * @param list
     */
    public void addMoreUrl(List<String> list,String name) {
        db = mMyDbhelper.getWritableDatabase();
        for (String str : list) {
            if (!judgeUrl(str,name)) {
                ContentValues contentValue = new ContentValues();
                contentValue.put("url", str);
                if (!db.isOpen()) {
                    db = mMyDbhelper.getWritableDatabase();
                }
                db.insert(name, null, contentValue);
//                Toast.makeText(mContext, "集合数据已添加到数据库中", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(mContext, "数据已存在于库中", Toast.LENGTH_SHORT).show();
            }
        }
        db.close();

    }

    /**
     * 添加数据到user表中
     *
     * @param url 添加的链接
     */
    public boolean addUrl(String url,String name) {
        db = mMyDbhelper.getWritableDatabase();
        if (!judgeUrl(url,name)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("url", url);
            db.insert(name, null, contentValues);
            Toast.makeText(mContext, "添加成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "该链接已存在", Toast.LENGTH_SHORT).show();
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    /**
     * 用来返回查询结果以List集合方式返回
     *
     * @return
     */
    public List queryResult(String name) {
        db = mMyDbhelper.getReadableDatabase();
        Cursor user = db.query(name, null, null, null, null, null, "_id desc");
        while (user.moveToNext()) {
            String url = user.getString(user.getColumnIndex("url"));
            mList.add(url);
        }
        db.close();
        user.close();
        return mList;
    }

    /**
     * 删除数据库中指定的url
     *
     * @param url
     */
    public void deleteUrl(String url,String name) {
        db = mMyDbhelper.getWritableDatabase();
        db.delete(name, "url = ?", new String[]{url});
        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
        db.close();
    }

    /**
     * 用来判断该链接是否已存在
     *
     * @param str
     * @return
     */
    public boolean judgeUrl(String str,String name) {
        db = mMyDbhelper.getReadableDatabase();
        Cursor user = db.query(name, null, null, null, null, null, null);
        while (user.moveToNext()) {
            String url = user.getString(user.getColumnIndex("url"));
            if (str.equals(url)) {
                db.close();
                user.close();
                return true;
            }
        }
        user.close();
        return false;
    }
}



