package com.example.quanlythuvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
    public Boolean insert_NguoiDung(String username, String pass, String email, String phone, String address, String sex){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", username);
        contentValues.put("password", pass);
        // contentValues.put("PASSWORD_CONFIRM", pass_conf);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("address", address);
        contentValues.put("sex", sex);

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


    //insert độc giả
    public Boolean insert_docgia(DocGiaModels docgia){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("hoten", docgia.getHoTen());
        contentValues.put("ngaysinh", docgia.getNgSinh());
        contentValues.put("loaidg", docgia.getLoaiDG());
        contentValues.put("diachi", docgia.getDiaChi());
        contentValues.put("email", docgia.getEmail());
        contentValues.put("nglapthe", docgia.getNgLapThe());
        contentValues.put("tinhtrangthe", docgia.getTinhTrangThe());

        long result = myDB.insert("DOCGIA", null, contentValues);

        if(result==-1){
            return false;
        } else {
            return  true;
        }
    }

    public Cursor getAllPms(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor resultSet = database.rawQuery("Select * from PHIEUMUONSACH", null);
        return resultSet;
    }

    public Boolean insert_phieumuonsach(PhieuMuonModels pms){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ma_pms", pms.getMa_PMS());
        contentValues.put("ma_dg", pms.getMa_DG());
        contentValues.put("ngaymuon", pms.getNgayMuon());


        long result = myDB.insert("PHIEUMUONSACH", null, contentValues);

        if(result==-1){
            return false;
        } else {
            return  true;
        }
    }

    public Cursor getAllBook(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor resultSet = database.rawQuery("Select * from DAUSACH",null);
        return resultSet;
    }

    //insert đầu sách
    public Boolean insert_dausach(DauSachModels dausach){
        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ma_dausach",dausach.getMA_DAUSACH());
        contentValues.put("tendausach", dausach.getTENDAUSACH());
        contentValues.put("tacgia", dausach.getTACGIA());
        contentValues.put("nxb", dausach.getNXB());
        contentValues.put("namxb", dausach.getNAMXB());
        contentValues.put("tongso", dausach.getTONGSO());
        contentValues.put("vitri", dausach.getVITRI());
        contentValues.put("theloai", dausach.getTHELOAI());
        contentValues.put("sanco", dausach.getSANCO());
        contentValues.put("dangchomuon",dausach.getDANGCHOMUON());
        contentValues.put("hinhanh", dausach.getHINHANH());

        long result = myDB.insert("DAUSACH", null, contentValues);

        if(result==-1){
            return false;
        } else {
            return  true;
        }
    }

    //search Book
    public Cursor searchBook(String name){
        SQLiteDatabase database = getReadableDatabase();
        Cursor resultSet = database.rawQuery("Select * from DAUSACH where tendausach LIKE ?",new String[] {"%"+ name+ "%" });
        return resultSet;
    }
    //search Reader
    public Cursor searchReader(String name){
        SQLiteDatabase database = getReadableDatabase();
        Cursor resultSet = database.rawQuery("Select * from DOCGIA where hoten LIKE ?",new String[] {"%"+ name+ "%" });
        return resultSet;
    }

    //Cập nhật đầu sách
    public Boolean update_dausach(DauSachModels dausach){
        SQLiteDatabase myDB = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ma_dausach",dausach.getMA_DAUSACH());
        contentValues.put("tendausach", dausach.getTENDAUSACH());
        contentValues.put("tacgia", dausach.getTACGIA());
        contentValues.put("nxb", dausach.getNXB());
        contentValues.put("namxb", dausach.getNAMXB());
        contentValues.put("tongso", dausach.getTONGSO());
        contentValues.put("vitri", dausach.getVITRI());
        contentValues.put("theloai", dausach.getTHELOAI());
        contentValues.put("sanco", dausach.getSANCO());
        contentValues.put("dangchomuon",dausach.getDANGCHOMUON());
        contentValues.put("hinhanh", dausach.getHINHANH());

        long result = myDB.update("DAUSACH", contentValues, "ma_dausach=?", new String[]{String.valueOf(dausach.getMA_DAUSACH())});

        if(result==-1){
            return false;
        } else {

            return  true;
        }
    }
    //xóa đầu sách
    public Boolean delete_dausach(String maDauSach){
        SQLiteDatabase database = getReadableDatabase();


        Cursor result = database.rawQuery("select * from DAUSACH where ma_dausach = ?", new String[]{maDauSach});

        if(result.getCount() > 0){
            result.moveToFirst();
            SQLiteDatabase myDB = this.getWritableDatabase();
            int tongso = result.getInt(5);
            int sanco = result.getInt(7);
            if(tongso == sanco){
                long row = myDB.delete("DAUSACH", "ma_dausach" + "=?", new String[]{maDauSach});
                if(row == -1){
                    return false;
                }
                else {
                    return true;
                }
            }
            return false;
        }else {
            return false;
        }
    }

}
