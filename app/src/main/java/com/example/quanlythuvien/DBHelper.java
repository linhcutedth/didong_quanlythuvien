package com.example.quanlythuvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper( Context context) {
        super(context, "QLTV.DB", null, 1);
    }

    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create table Nguoidung(id INTEGER primary key AUTOINCREMENT, username TEXT, password TEXT, passwordconfirm TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop table if exists Nguoidung");

    }

    public Boolean insertData(int id, String username, String password, String pass_confirm){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id","");
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("passwordconfirm", pass_confirm);
        long result  = myDB.insert("Nguoidung", null, contentValues);
        if (result==1) return false;
        else return true;
    }

    public Boolean check_Username_password(String username, String password){
         SQLiteDatabase myDB = this.getWritableDatabase();
         Cursor cursor = myDB.rawQuery("select * from Nguoidung where username =? and password =?", new String[]{username, password});
         if (cursor.getCount()>0){
             return true;
         }else return false;
    }

}
