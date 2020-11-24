package com.example.launchertest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler {
    SQLiteOpenHelper mHelper = null;
    SQLiteDatabase mDB = null;

    public DBHandler(Context context) {
        mHelper = new DBHelper(context);
    }

    public static DBHandler open(Context context) {
        return new DBHandler(context);
    }

    public Cursor select() {
        mDB = mHelper.getReadableDatabase();
        String sql_query = "SELECT * FROM LauncherDB";
        Cursor c = mDB.rawQuery(sql_query, null);
        //c.moveToFirst();
        return c;
    }

    public void insert(String title){
        mDB = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",title);
        mDB.insert("LauncherDB",null,values);
    }

    public void delete(String title){
        mDB = mHelper.getWritableDatabase();
        mDB.delete("LauncherDB", "title=?", new String[]{title});
    }
}
