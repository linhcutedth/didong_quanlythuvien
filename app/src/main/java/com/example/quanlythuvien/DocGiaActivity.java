package com.example.quanlythuvien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuvien.fragment.BookFragment;
import com.example.quanlythuvien.fragment.ReaderFragment;
import com.google.android.filament.View;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class DocGiaActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private BottomNavigationView mBottomNavigationView;
    private ArrayList<DocGiaModels> contList;
    private SqliteDBHelper dbHelper;
    private TextView readTV;

    private ArrayList<NguoiDungModel> dungModelArrayList;
    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_BOOK = 1;
    private static final int FRAGMENT_ACCOUNT = 2;
    private static final int DocGiaActivity = 3;

    private  int mCurrentFragment = DocGiaActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_gia);

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.bottom_home){
                    Intent intent = new Intent(DocGiaActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else if(id == R.id.bottom_book){
                    openBookFragment();
                }else if (id == R.id.bottom_reader){
                    Intent intent = new Intent(DocGiaActivity.this, DocGiaActivity.class);
                    startActivity(intent);
                }
                setTitleToolBar();
                return true;
            }
        });
        setTitleToolBar();
    }
    private void openBookFragment(){
        if(mCurrentFragment != FRAGMENT_BOOK){
            replaceFragment(new BookFragment());
            mCurrentFragment = FRAGMENT_BOOK;
        }
    }
    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
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
        }
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

}