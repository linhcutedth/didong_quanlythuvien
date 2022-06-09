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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            int tongso = dausach.getTONGSO();
            long count = 0;
            for (int i = 0; i < tongso; i++){
                ContentValues contentValuesCS = new ContentValues();
                contentValuesCS.put("ma_dausach", result);
                contentValuesCS.put("tinhtrang", "sẵn có");
               long row = myDB.insert("CUONSACH", null, contentValuesCS);
               if(row != -1){
                   count = count + 1;
               }
            }
            if(count == tongso){
                return true;
            }else{
                return false;
            }
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
                long temp = myDB.delete("CUONSACH", "ma_dausach" + "=?", new String[]{maDauSach});
                if(temp != -1){
                    long row = myDB.delete("DAUSACH", "ma_dausach" + "=?", new String[]{maDauSach});
                    if(row == -1){
                        return false;
                    }
                    else {
                        return true;
                    }
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
    //lấy danh sách phiếu trả sách
    public Cursor getAllPts(){
        SQLiteDatabase database = getReadableDatabase();
        Cursor resultSet = database.rawQuery("Select * from PHIEUTRASACH", null);
        return resultSet;
    }

    public List<String> getDocGia_NV(){
        List<String> list = new ArrayList<String>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from DOCGIA",null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0) + "-" + cursor.getString(1));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        database.close();
        // returning lables
        return list;
    }

    public List<String> getCuonSach_TS_list(){
        List<String> list = new ArrayList<String>();

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery("Select MA_SACH, TENDAUSACH from CUONSACH,DAUSACH where CUONSACH.MA_DAUSACH = DAUSACH.MA_DAUSACH and tinhtrang = ?",new String[]{"đang cho mượn"});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0)+":"+cursor.getString(1));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        database.close();
        // returning lables
        return list;
    }

    //search Phiếu trả sách
    public Cursor searchPts(String name){
        SQLiteDatabase database = getReadableDatabase();
        Cursor resultSet = database.rawQuery("Select * from PHIEUTRASACH where MA_PTS LIKE ?",new String[] {"%"+ name+ "%" });
        return resultSet;
    }

    public String[] getCuonSach_TS_array(){
        List<String> list = getCuonSach_TS_list();
        int length = list.size();
        String[] cuonsachArray = new String[length];
        for(int i = 0; i < length; i++){
            cuonsachArray[i] = list.get(i);
        }
        return cuonsachArray;
    }

    //Thêm phiếu trả sách
    public Boolean insert_phieutrasach(PhieuTraModels pts, String macuonsach){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String[] word = macuonsach.split(",");
        List<Integer> masach = new ArrayList<Integer>();
        for(int i = 0; i < word.length; i++){
            String[] word1 = word[i].split(":");
            masach.add(Integer.valueOf(word1[0].trim()));
        }
        contentValues.put("ma_dg", pts.getMa_DG());
        contentValues.put("ngaytra", pts.getNgayTra());
        contentValues.put("tienphatkynay", pts.getTienPhatKyNay());

        long result = myDB.insert("PHIEUTRASACH", null, contentValues);

        if(result==-1){
            return false;
        } else {
            int count = 0;
            for(int i = 0; i < masach.size();i++){
                Log.v("macuonsach",String.valueOf(masach.get(i)));
                ContentValues contentValuesCTPT = new ContentValues();
                contentValuesCTPT.put("ma_pts", result);
                contentValuesCTPT.put("ma_sach",masach.get(i));
                contentValuesCTPT.put("songaytratre", 0);
                contentValuesCTPT.put("tienphat", 0);
                long row = myDB.insert("CTPTS", null, contentValuesCTPT);
                if(row != -1){
                    count = count + 1;
                xuly((int)result, pts.getMa_DG(), masach.get(i),pts.getNgayTra());
                update_phieumuonsach(masach.get(i));
                capnhatcuonsach(masach.get(i));
                capnhatdausach(masach.get(i));
                }
            }
            if(count == masach.size()){
                return true;
            }
            return false;
        }
    }
    // xử lý tiền phạt
    public void xuly(int mapts, int madg, int masach, String ngaytrastr){
        String ngaymuonstr = layngaymuon(masach,madg);
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        long getDaysDiff;
        try {
            Date ngaymuon = formatter1.parse(ngaymuonstr);
            Date ngaytra = formatter1.parse(ngaytrastr);
            long getDiff = ngaytra.getTime() - ngaymuon.getTime();
            getDaysDiff = getDiff / (24 * 60 * 60 * 1000);
            if(getDaysDiff > 4){
                update_chitiet_pts(masach,mapts,getDaysDiff);
                update_phieutrasach(mapts,getDaysDiff);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    public String layngaymuon(int masach, int madg){
        String ngaymuon ="";
        SQLiteDatabase database = getReadableDatabase();
        Cursor result = database.rawQuery("select NGAYMUON from PHIEUMUONSACH, CTMS where PHIEUMUONSACH.MA_PMS = CTMS.MA_PMS and MA_DG = ? and MA_SACH = ? and TINHTRANG = ?", new String[]{String.valueOf(madg),String.valueOf(masach),"chưa trả"});
        if(result.getCount() > 0) {
            result.moveToFirst();
            ngaymuon = result.getString(0);
        }
        return ngaymuon;
    }
    public void update_chitiet_pts(int masach, int mapts,long songay){
        SQLiteDatabase myDB = this.getWritableDatabase();
        int songaytratre = (int)songay;
        ContentValues contentValues = new ContentValues();
        contentValues.put("songaytratre",songaytratre);
        contentValues.put("tienphat", (songaytratre-4)*1000);

        myDB.update("CTPTS", contentValues, "ma_sach=? and ma_pts=?", new String[]{String.valueOf(masach),String.valueOf(mapts)});

    }
    public void update_phieutrasach(int mapts,long songay){
        Cursor cursor = searchPts(String.valueOf(mapts));
        int tienphatkynay = 0;
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            tienphatkynay = cursor.getInt(3);
        }
        SQLiteDatabase myDB = this.getWritableDatabase();
        int songaytratre = (int)songay;
        ContentValues contentValues = new ContentValues();
        contentValues.put("tienphatkynay", tienphatkynay + (songaytratre-4)*1000);

        myDB.update("PHIEUTRASACH", contentValues, "MA_PTS=?", new String[]{String.valueOf(mapts)});

    }

    public void update_phieumuonsach(int masach){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tinhtrang", "đã trả");

        myDB.update("CTMS", contentValues, "ma_sach=?", new String[]{String.valueOf(masach)});
    }
    public void capnhatcuonsach(int masach){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tinhtrang", "sẵn có");

        myDB.update("CUONSACH", contentValues, "ma_sach=?", new String[]{String.valueOf(masach)});
    }
    public void capnhatdausach(int masach){
        int madausach = laymadausach(masach);
        Cursor cursor = laydausachtuma(madausach);
        int sanco = 0;
        int dangchomuon = 0;
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            sanco = cursor.getInt(7);
            dangchomuon = cursor.getInt(8);
        }
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sanco", sanco + 1);
        contentValues.put("dangchomuon", dangchomuon - 1);

        myDB.update("DAUSACH", contentValues, "ma_dausach=?", new String[]{String.valueOf(madausach)});
    }
    public int laymadausach(int masach){
        SQLiteDatabase database = getReadableDatabase();
        int madausach = 0;
        Cursor resultSet = database.rawQuery("Select * from CUONSACH where ma_sach = ?",new String[] {String.valueOf(masach)});
        if(resultSet.getCount() > 0) {
            resultSet.moveToFirst();
            madausach = resultSet.getInt(1);
        }
        return madausach;
    }
    public Cursor laydausachtuma(int madausach){
        SQLiteDatabase database = getReadableDatabase();
        Cursor resultSet = database.rawQuery("Select * from DAUSACH where ma_dausach = ?",new String[] {String.valueOf(madausach)});

        return resultSet;
    }
    public String layTenDocGia(int maDG){
        SQLiteDatabase database = getReadableDatabase();
        String tenDocGia = "";
        Cursor resultSet = database.rawQuery("Select * from DOCGIA where ma_dg = ?",new String[] {String.valueOf(maDG)});
        if(resultSet.getCount() > 0) {
            resultSet.moveToFirst();
            tenDocGia = resultSet.getString(1);
        }
        return tenDocGia;
    }
    public Cursor layctts(int mapts){
        SQLiteDatabase database = getReadableDatabase();
        Cursor resultSet = database.rawQuery("Select MA_PTS, CUONSACH.MA_SACH, TENDAUSACH,  SONGAYTRATRE, TIENPHAT from CTPTS,CUONSACH,DAUSACH where CTPTS.MA_SACH = CUONSACH.MA_SACH and CUONSACH.MA_DAUSACH = DAUSACH.MA_DAUSACH and ma_pts = ?",new String[] {String.valueOf(mapts)});

        return resultSet;
    }
    //insert phiếu thu
    public Boolean insert_phieuthu(PhieuThuModels phieuthu){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ma_pts", phieuthu.getMa_PTS());
        contentValues.put("tienno", phieuthu.getTienNo());
        contentValues.put("tienthu", phieuthu.getTienThu());

        long result = myDB.insert("PHIEUTHUTIENPHAT", null, contentValues);
        if(result==-1){
            return false;
        } else {
            return  true;
        }
    }
    public Cursor layphieuthu(int mapts){
        SQLiteDatabase database = getReadableDatabase();
        Cursor resultSet = database.rawQuery("Select * from PHIEUTHUTIENPHAT where ma_pts = ?",new String[] {String.valueOf(mapts)});

        return resultSet;
    }
    public int laytienno(String name){
        SQLiteDatabase database = getReadableDatabase();
        Cursor resultSet = database.rawQuery("Select * from PHIEUTRASACH where MA_PTS = ?",new String[] {name});
        int tienno = 0;
        if(resultSet.getCount() > 0) {
            resultSet.moveToFirst();
            tienno = resultSet.getInt(3);
        }
        return tienno;
    }
    public Boolean kiemtraphieuthu(String name){
        SQLiteDatabase database = getReadableDatabase();
        Cursor resultSet = database.rawQuery("Select * from PHIEUTHUTIENPHAT where MA_PTS = ?",new String[] {name});
        if(resultSet.getCount() > 0) {
            return true;
        }
        return false;
    }
}
