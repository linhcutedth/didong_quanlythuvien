package com.example.quanlythuvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quanlythuvien.fragment.ReaderFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SqliteDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QuanLyThuVien.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    private static SqliteDBHelper mInstance;
    private static SQLiteDatabase mSqliteDb;
    static Context mContext;


    public SqliteDBHelper(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
        mContext = context;
    }




    public void initialise() {
        if (mInstance == null) {
            if (!checkDatabase()) {

                copyDataBase();
            }
            mInstance = new SqliteDBHelper(mContext, null, DATABASE_VERSION);
            mSqliteDb = mInstance.getWritableDatabase();
        }
    }

    public void QueryData(String sql){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    //truy van tra ve ket qua

    public Cursor GetData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public SqliteDBHelper getInstance(){
        return mInstance;
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH_SUFFIX + DATABASE_NAME;
        mSqliteDb = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);
    }

    public SQLiteDatabase getDatabase() {
        return mSqliteDb;
    }

    private static void copyDataBase() {

        try {
            // Open your local db as the input stream
            InputStream myInput = mContext.getAssets().open(DATABASE_NAME);

            // Path to the just created empty db
            String outFileName = getDatabasePath(mContext);

            // if the path doesn't exist first, create it
            File f = new File(mContext.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean checkDatabase() {
        SQLiteDatabase checkDB = null;

        try {
            try {
                String myPath = getDatabasePath(mContext);
                checkDB = SQLiteDatabase.openDatabase(myPath, null,
                        SQLiteDatabase.OPEN_READONLY);
                checkDB.close();
            } catch (Exception e) { }
        } catch (Throwable ex) {
        }
        return checkDB != null ? true : false;
    }

    private static String getDatabasePath(Context mContext) {
        return mContext.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // insert NGUOIDUNG
    public Boolean insert_NguoiDung(String username, String pass){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", username);
        contentValues.put("password", pass);
       // contentValues.put("PASSWORD_CONFIRM", pass_conf);
        contentValues.put("email", "");
        contentValues.put("phone", "");
        contentValues.put("address", "");
        contentValues.put("sex", "");

        long result = myDB.insert("NGUOIDUNG", null, contentValues);

        if(result==-1){
            return false;
        } else {
            return  true;
        }
    }

    //Kiểm tra tên đăng nhập đã tồn tại hay chưa

    public Boolean check_username (String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from Nguoidung where username =?", new String[]{username});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }
    //Kiểm tra tên đăng nhập và mật khẩu có khớp nhau không
    public Boolean check_Username_password(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from Nguoidung where username =? and password =?", new String[]{username, password});
        if (cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }


    public Cursor getAllReader(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor resultSet = database.rawQuery("Select * from DOCGIA",null);
        return resultSet;
    }
}
