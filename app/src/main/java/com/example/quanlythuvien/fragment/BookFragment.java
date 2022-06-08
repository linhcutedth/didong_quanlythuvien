package com.example.quanlythuvien.fragment;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.quanlythuvien.DauSachAdapter;
import com.example.quanlythuvien.DauSachModels;
import com.example.quanlythuvien.DocGiaModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BookFragment extends Fragment implements OnClickListener, SearchView.OnQueryTextListener {
    private DrawerLayout mDrawerLayout;
    private BottomNavigationView mBottomNavigationView;
    private ArrayList<DocGiaModels> contList;
    private SqliteDBHelper dbHelper;
    private TextView readTV;
    private HomeActivity homeActivity;
    private DauSachModels dauSachModels;
    private FloatingActionButton button;
    private MenuItem menuItem;
    private SearchView searchView;
    private Menu menu;
    private RecyclerView recyclerView;
    SqliteDBHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_book,container,false);

        View view = inflater.inflate(R.layout.fragment_book, container, false);
        ArrayList<DauSachModels> list = new ArrayList<DauSachModels>();
        homeActivity = (HomeActivity) getActivity();
        list = homeActivity.getAllBook();
        db = new SqliteDBHelper(BookFragment.this.getActivity(), null, 1);
        recyclerView = view.findViewById(R.id.idRV_CuonSach);
        recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
        recyclerView.setAdapter(new DauSachAdapter(list, new DauSachAdapter.IClickItemlistener() {
            @Override
            public void onClickItemBook(DauSachModels dauSachModels) {
                homeActivity.DetailBook(dauSachModels);
            }
            @Override
            public void onClickDeleteBook(String maDauSach){
                openModal(Gravity.CENTER,maDauSach);
            }
        }));

        button =  view.findViewById(R.id.add_button);
        button.setOnClickListener(this);

        return view;
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_button:
                Fragment newFragment = new Book_Insert();
                androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, newFragment).commit();

                break;
        }
    }

    // search
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menuItem = menu.findItem(R.id.toolbar_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconified(true);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void mysearch(String query) {
        ArrayList<DauSachModels> list = new ArrayList<DauSachModels>();
        homeActivity = (HomeActivity) getActivity();
        list = homeActivity.searchBook(query);
        if(list.size() !=0){
            recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
            recyclerView.setAdapter(new DauSachAdapter(list, new DauSachAdapter.IClickItemlistener() {
                @Override
                public void onClickItemBook(DauSachModels dauSachModels) {
                    homeActivity.DetailBook(dauSachModels);
                }
                @Override
                public void onClickDeleteBook(String maDauSach){
                    openModal(Gravity.CENTER,maDauSach);
                }
            }));
        }
        else {
            recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
            recyclerView.setAdapter(new DauSachAdapter(list, new DauSachAdapter.IClickItemlistener() {
                @Override
                public void onClickItemBook(DauSachModels dauSachModels) {}
                @Override
                public void onClickDeleteBook(String maDauSach){}
            }));
            Toast.makeText(BookFragment.this.getActivity(),"Không tìm thấy sách",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        mysearch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        mysearch(query);
        return false;
    }

    public void openModal(int gravity, String maDauSach){
        final Dialog dialog = new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.CENTER == gravity){
            dialog.setCancelable(true);
        }else {
            dialog.setCancelable(false);
        }
        Button btn_no = dialog.findViewById(R.id.btn_cancel);
        Button btn_yes = dialog.findViewById(R.id.btn_accept);

        btn_no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean rs = db.delete_dausach(maDauSach);
                if (rs == true){
                    Fragment newFragment = new BookFragment();
                    androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Toast.makeText(BookFragment.this.getActivity(),"Xóa thành công",Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, newFragment).commit();
                } else{
                    Toast.makeText(BookFragment.this.getActivity(), "Xóa không thành công", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
