package com.example.quanlythuvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.quanlythuvien.fragment.AccountFragment;
import com.example.quanlythuvien.fragment.BookFragment;
import com.example.quanlythuvien.fragment.Book_Insert;
import com.example.quanlythuvien.fragment.BorrowFragment;
import com.example.quanlythuvien.fragment.Detail_Book;
import com.example.quanlythuvien.fragment.Detail_ReturnBook;
import com.example.quanlythuvien.fragment.ReaderFragment;
import com.example.quanlythuvien.fragment.HomeFragment;
import com.example.quanlythuvien.fragment.ReturnBookFragment;
import com.example.quanlythuvien.fragment.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_BOOK = 1;
    private static final int FRAGMENT_ACCOUNT = 2;
    private static final int FRAGMENT_READER = 3;
    private static final int FRAGMENT_SETTING = 4;
    private static final int FRAGMENT_BORROW_BOOK = 5;
    private static final int FRAGMENT_RETURN_BOOK = 6;



    private  int mCurrentFragment = FRAGMENT_HOME;
    private DrawerLayout mDrawerLayout;
    private BottomNavigationView mBottomNavigationView;
    SqliteDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* db = new SqliteDBHelper(this, null, 1);
        db.initialise();*/

        mBottomNavigationView = findViewById(R.id.nav_bottom);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new HomeFragment());

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            //bottom_nav
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.bottom_home){
                    openHomeFragment();
                }else if(id == R.id.bottom_book){
                    openBookFragment();
                }else if (id == R.id.bottom_reader){
                    openReaderFragment();
                }else if (id == R.id.bottom_setting){
                    openSettingFragment();
                }
                setTitleToolBar();
                return true;
            }
        });
        setTitleToolBar();
    }

    //menu_bottom
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home){
            openHomeFragment();
            mBottomNavigationView.getMenu().findItem(R.id.bottom_home).setChecked(true);
        }else if (id == R.id.nav_book){
            openBookFragment();
            mBottomNavigationView.getMenu().findItem(R.id.bottom_book).setChecked(true);
        }else if (id == R.id.nav_account){
            if(mCurrentFragment != FRAGMENT_ACCOUNT){
                replaceFragment(new AccountFragment());
                mCurrentFragment = FRAGMENT_ACCOUNT;
            }
        }else if (id == R.id.nav_reader){
            openReaderFragment();
            mBottomNavigationView.getMenu().findItem(R.id.bottom_reader).setChecked(true);
        }else if(id == R.id.nav_borrow_book){
            if(mCurrentFragment != FRAGMENT_BORROW_BOOK){
                replaceFragment(new BorrowFragment());
                mCurrentFragment = FRAGMENT_BORROW_BOOK;
            }
        }else if(id == R.id.nav_return_book){
            if(mCurrentFragment != FRAGMENT_RETURN_BOOK){
                replaceFragment(new ReturnBookFragment());
                mCurrentFragment = FRAGMENT_RETURN_BOOK;
            }
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        setTitleToolBar();
        return true;
    }
    private void openHomeFragment(){
        if(mCurrentFragment != FRAGMENT_HOME){
            replaceFragment(new HomeFragment());
            mCurrentFragment = FRAGMENT_HOME;
        }
    }
    private void openBookFragment(){
        if(mCurrentFragment != FRAGMENT_BOOK){
            replaceFragment(new BookFragment());
            mCurrentFragment = FRAGMENT_BOOK;
        }
    }
    private void openReaderFragment(){
        if(mCurrentFragment != FRAGMENT_READER){
            replaceFragment(new ReaderFragment());
            mCurrentFragment = FRAGMENT_READER;
        }
    }

    private void openSettingFragment(){
        if(mCurrentFragment != FRAGMENT_SETTING){
            replaceFragment(new SettingFragment());
            mCurrentFragment = FRAGMENT_SETTING;
        }
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_on_toolbar,menu);
        MenuItem item_back = menu.findItem(R.id.toolbar_back);
        item_back.setVisible(false);
        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        };
        menu.findItem(R.id.toolbar_search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView = (SearchView) menu.findItem(R.id.toolbar_search).getActionView();
        searchView.setQueryHint("Tìm kiếm.....");
        return true;
    }

    private void setTitleToolBar(){
        String title ="";
        switch (mCurrentFragment){
            case FRAGMENT_HOME:
                title = getString(R.string.nav_home);
                break;
            case FRAGMENT_BOOK:
                title = getString(R.string.nav_book);
                break;
            case FRAGMENT_ACCOUNT:
                title = getString(R.string.nav_account);
                break;
            case FRAGMENT_READER:
                title = getString(R.string.nav_reader);
                break;
            case FRAGMENT_SETTING:
                title = getString(R.string.nav_setting);
                break;
            case FRAGMENT_BORROW_BOOK:
                title = "Phiếu mượn sách";
                break;
            case FRAGMENT_RETURN_BOOK:
                title = "Phiếu trả sách";
                break;
        }
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }

    }

    //lấy tất cả độc giả
    public ArrayList<DocGiaModels> getAllReader(){
        db = new SqliteDBHelper(this, null, 1);
        db.initialise();
        Cursor data = db.getAllReader();
        ArrayList<DocGiaModels> list = new ArrayList<DocGiaModels>();
        while(data.moveToNext()){
            int id = data.getInt(0);
            String hoTen = data.getString(1);
            String ngSinh = data.getString(2);
            String loaiDG = data.getString(3);
            String diaChi = data.getString(4);
            String email = data.getString(5);
            String ngLapThe = data.getString(6);
            String tinhTrang = data.getString(7);
            // Toast.makeText(this, hoTen, Toast.LENGTH_SHORT).show();
            System.out.print(hoTen);
            DocGiaModels docgia;
            docgia = new DocGiaModels(id,hoTen,ngSinh,loaiDG,diaChi,email,ngLapThe,tinhTrang);
            list.add(docgia);
        }
        return list;
    }

    //lấy tất cả phiếu mượn sách
    public ArrayList<PhieuMuonModels> getAllPms(){
        db = new SqliteDBHelper(this, null, 1);
        db.initialise();
        Cursor data = db.getAllPms();
        ArrayList<PhieuMuonModels> list = new ArrayList<PhieuMuonModels>();
        while(data.moveToNext()){
            int ma_pms = data.getInt(0);
            int ma_dg = data.getInt(1);
            String tinhTrang  = data.getString(2);
            PhieuMuonModels pms;
            pms = new PhieuMuonModels(ma_pms, ma_dg,tinhTrang);
            list.add(pms);
        }
        return list;
    }

    //lấy tất cả sách
    public ArrayList<DauSachModels> getAllBook(){
        db = new SqliteDBHelper(this, null, 1);
        db.initialise();
        Cursor data = db.getAllBook();
        ArrayList<DauSachModels> list = new ArrayList<DauSachModels>();
        while(data.moveToNext()){
            int MA_DAUSACH = data.getInt(0);
            String TENDAUSACH = data.getString(1);
            String TACGIA = data.getString(2);
            String NXB = data.getString(3);
            int NAMXB = data.getInt(4);
            int TONGSO = data.getInt(5);
            String VITRI = data.getString(6);
            int SANCO = data.getInt(7);
            int DANGCHOMUON = data.getInt(8);
            String THELOAI = data.getString(9);
            String HINHANH = data.getString(10);
            DauSachModels cuonsach;
            cuonsach = new DauSachModels(MA_DAUSACH,TENDAUSACH,TACGIA,NXB,NAMXB,TONGSO,VITRI,SANCO,DANGCHOMUON,THELOAI,HINHANH);
            list.add(cuonsach);

        }
        return list;
    }

    //TÌM KIẾM SÁCH
    public ArrayList<DauSachModels> searchBook(String query){
        db = new SqliteDBHelper(this, null, 1);
        db.initialise();
        Cursor data = db.searchBook(query);
        ArrayList<DauSachModels> list = new ArrayList<DauSachModels>();
        while(data.moveToNext()){
            int MA_DAUSACH = data.getInt(0);
            String TENDAUSACH = data.getString(1);
            String TACGIA = data.getString(2);
            String NXB = data.getString(3);
            int NAMXB = data.getInt(4);
            int TONGSO = data.getInt(5);
            String VITRI = data.getString(6);
            int SANCO = data.getInt(7);
            int DANGCHOMUON = data.getInt(8);
            String THELOAI = data.getString(9);
            String HINHANH = data.getString(10);
            System.out.print(TENDAUSACH);
            DauSachModels cuonsach;
            cuonsach = new DauSachModels(MA_DAUSACH,TENDAUSACH,TACGIA,NXB,NAMXB,TONGSO,VITRI,SANCO,DANGCHOMUON,THELOAI,HINHANH);
            list.add(cuonsach);
        }
        return list;
    }

    //TÌM KIẾM ĐỘC GIẢ
    public ArrayList<DocGiaModels> searchReader(String query){
        db = new SqliteDBHelper(this, null, 1);
        db.initialise();
        Cursor data = db.searchReader(query);
        ArrayList<DocGiaModels> list = new ArrayList<DocGiaModels>();
        while(data.moveToNext()){
            int id = data.getInt(0);
            String hoTen = data.getString(1);
            String ngSinh = data.getString(2);
            String loaiDG = data.getString(3);
            String diaChi = data.getString(4);
            String email = data.getString(5);
            String ngLapThe = data.getString(6);
            String tinhTrang = data.getString(7);
            // Toast.makeText(this, hoTen, Toast.LENGTH_SHORT).show();
            System.out.print(hoTen);
            DocGiaModels docgia;
            docgia = new DocGiaModels(id,hoTen,ngSinh,loaiDG,diaChi,email,ngLapThe,tinhTrang);
            list.add(docgia);
        }
        return list;
    }
    //Xem chi tiết đầu sách
    public void DetailBook(DauSachModels dauSachModels){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Detail_Book detail_book = new Detail_Book();

        Bundle bundle = new Bundle();
        bundle.putSerializable("object_dausach", dauSachModels);

        detail_book.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,detail_book);
        fragmentTransaction.addToBackStack(Detail_Book.TAG);
        fragmentTransaction.commit();
    }

    //lấy tất cả phiếu trả sách
    public ArrayList<PhieuTraModels> getAllPts(){
        db = new SqliteDBHelper(this, null, 1);
        db.initialise();
        Cursor data = db.getAllPts();
        ArrayList<PhieuTraModels> list = new ArrayList<PhieuTraModels>();
        while(data.moveToNext()){
            int ma_pts = data.getInt(0);
            int ma_dg = data.getInt(1);
            String ngayTra  = data.getString(2);
            int tienPhat = data.getInt(3);
            PhieuTraModels pts;
            pts = new PhieuTraModels(ma_pts,ma_dg,ngayTra,tienPhat);
            list.add(pts);
        }
        return list;
    }
    //SEARCH PHIẾU TRẢ SÁCH
    public ArrayList<PhieuTraModels> searchPts(String query){
        db = new SqliteDBHelper(this, null, 1);
        db.initialise();
        Cursor data = db.searchPts(query);
        ArrayList<PhieuTraModels> list = new ArrayList<PhieuTraModels>();
        while(data.moveToNext()){
            int ma_pts = data.getInt(0);
            int ma_dg = data.getInt(1);
            String ngayTra  = data.getString(2);
            int tienPhat = data.getInt(3);
            PhieuTraModels pts;
            pts = new PhieuTraModels(ma_pts,ma_dg,ngayTra,tienPhat);
            list.add(pts);
        }
        return list;
    }
    public void DetailReturn_Book(PhieuTraModels phieuTraModels){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Detail_ReturnBook detail_returnBook = new Detail_ReturnBook();

        Bundle bundle = new Bundle();
        bundle.putSerializable("object_phieutra", phieuTraModels);

        detail_returnBook.setArguments(bundle);

        fragmentTransaction.replace(R.id.content_frame,detail_returnBook);
        fragmentTransaction.addToBackStack(Detail_ReturnBook.TAG);
        fragmentTransaction.commit();
    }
    //lấy tất cả chi tiết phiếu trả sách
    public ArrayList<CTPTSModels> getAllCTPTS(int mapts){
        db = new SqliteDBHelper(this, null, 1);
        db.initialise();
        Cursor data = db.layctts(mapts);
        ArrayList<CTPTSModels> list = new ArrayList<CTPTSModels>();
        while(data.moveToNext()){
            int ma_pts = data.getInt(0);
            int ma_sach = data.getInt(1);
            String tendausach  = data.getString(2);
            int songaytratre = data.getInt(3);
            int tienPhat = data.getInt(4);

            CTPTSModels ctpts;
            ctpts = new CTPTSModels(ma_pts,ma_sach,tendausach,songaytratre,tienPhat);
            list.add(ctpts);
        }
        return list;
    }

}
