package com.example.quanlythuvien.fragment;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuvien.DocGiaAdapter;
import com.example.quanlythuvien.DocGiaModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ReaderFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener {

    private DrawerLayout mDrawerLayout;
    private BottomNavigationView mBottomNavigationView;
    private ArrayList<DocGiaModels> contList;
    private SqliteDBHelper dbHelper;
    private TextView readTV;
    private HomeActivity homeActivity;
    private DocGiaAdapter docGiaAdapter;
    private FloatingActionButton button_add;
    private MenuItem menuItem;
    private SearchView searchView;
    private Menu menu;
    RecyclerView recyclerView;
    SqliteDBHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_doc_gia,container,false);
        ArrayList<DocGiaModels> list = new ArrayList<DocGiaModels>();
        homeActivity = (HomeActivity) getActivity();
        db = new SqliteDBHelper(ReaderFragment.this.getActivity(), null, 1);
        list = homeActivity.getAllReader();


        recyclerView = view.findViewById(R.id.idRV_DocGia);
        recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
        recyclerView.setAdapter(new DocGiaAdapter(list, new DocGiaAdapter.IClickItemlistener() {
            @Override
            public void onClickItemDocGia(DocGiaModels docGiaModels) {
                homeActivity.DetailDocGia(docGiaModels);
            }

            @Override
            public void onClickDeleteDocGia(String maDocGia) {
                openModal(Gravity.CENTER,maDocGia);
            }

        }));

        button_add = view.findViewById(R.id.add_button);

        //Thêm độc giả
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.add_button:
                        Fragment newFragment = new AddReaderFragment();
                        androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_frame, newFragment).commit();

                        break;
                }
            }
        });

        return view;
    }

    // search
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).setActionBarTitle("Độc giả");
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
        ArrayList<DocGiaModels> list = new ArrayList<DocGiaModels>();
        homeActivity = (HomeActivity) getActivity();
        list = homeActivity.searchReader(query);
        if(list.size() !=0){
            recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
            recyclerView.setAdapter(new DocGiaAdapter(list, new DocGiaAdapter.IClickItemlistener() {
                @Override
                public void onClickItemDocGia(DocGiaModels docGiaModels) {
                    homeActivity.DetailDocGia(docGiaModels);
                }


                @Override
                public void onClickDeleteDocGia(String maDocGia){
                    openModal(Gravity.CENTER,maDocGia);
                }
            }));
        }
        else {
            recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
            recyclerView.setAdapter(new DocGiaAdapter(list, new DocGiaAdapter.IClickItemlistener() {
                @Override
                public void onClickItemDocGia(DocGiaModels docGiaModels) {
                    homeActivity.DetailDocGia(docGiaModels);
                }

                @Override
                public void onClickDeleteDocGia(String maDocGia) {

                }

            }));
            Toast.makeText(ReaderFragment.this.getActivity(),"Không tìm thấy độc giả",Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View view) {

    }

    public void openModal(int gravity, String madocgia){
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

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean rs = db.delete_docgia(madocgia);
                if (rs == true){
                    Fragment newFragment = new ReaderFragment();
                    androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Toast.makeText(ReaderFragment.this.getActivity(),"Xóa thành công",Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, newFragment).commit();
                } else{
                    Toast.makeText(ReaderFragment.this.getActivity(), "Xóa không thành công", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}