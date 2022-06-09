package com.example.quanlythuvien.fragment;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
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
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.quanlythuvien.DauSachAdapter;
import com.example.quanlythuvien.DauSachModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.PhieuMuonAdapter;
import com.example.quanlythuvien.PhieuMuonModels;
import com.example.quanlythuvien.PhieuThuModels;
import com.example.quanlythuvien.PhieuTraAdapter;
import com.example.quanlythuvien.PhieuTraModels;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.Calendar;

public class ReturnBookFragment extends Fragment implements OnClickListener, SearchView.OnQueryTextListener {
    private HomeActivity homeActivity;
    private FloatingActionButton button;
    private RecyclerView recyclerView;
    private MenuItem menuItem;
    private SearchView searchView;
    private Menu menu;
    SqliteDBHelper db;
    AwesomeValidation mAwesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_phieutra, container, false);


        ArrayList<PhieuTraModels> list = new ArrayList<PhieuTraModels>();
        homeActivity = (HomeActivity) getActivity();
        list = homeActivity.getAllPts();
        db = new SqliteDBHelper(ReturnBookFragment.this.getActivity(), null, 1);
        if (list != null){
            recyclerView = view.findViewById(R.id.idRV_PhieuTra);
            recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
            recyclerView.setAdapter(new PhieuTraAdapter(list, new PhieuTraAdapter.IClickItemlistener() {
                @Override
                public void onClickItemBook(PhieuTraModels phieuTraModels) {
                    homeActivity.DetailReturn_Book(phieuTraModels);
                }
                @Override
                public void onClickPhieuThu(String mapts){
                    Boolean check = db.kiemtraphieuthu(mapts);
                    int tienno = db.laytienno(mapts);
                    if(!check && tienno != 0){
                        openModal(Gravity.CENTER,mapts,tienno);
                    }
                    else{
                        Toast.makeText(ReturnBookFragment.this.getActivity(), "Không có tiền nợ cho phiếu trả này", Toast.LENGTH_SHORT).show();
                    }
                }
            }));
        }else
        {
            Toast.makeText(ReturnBookFragment.this.getActivity(),"ko duoc roi", Toast.LENGTH_SHORT).show();
        }

        button =  view.findViewById(R.id.add_button);
        button.setOnClickListener(this);

        return view;
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_button:
                Fragment newFragment = new ReturnBook_Insert();
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
        ArrayList<PhieuTraModels> list = new ArrayList<PhieuTraModels>();
        homeActivity = (HomeActivity) getActivity();
        list = homeActivity.searchPts(query);
        if(list.size() !=0){
            recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
            recyclerView.setAdapter(new PhieuTraAdapter(list, new PhieuTraAdapter.IClickItemlistener() {
                @Override
                public void onClickItemBook(PhieuTraModels phieuTraModels) {
                    homeActivity.DetailReturn_Book(phieuTraModels);
                }
                @Override
                public void onClickPhieuThu(String mapts){
                    Boolean check = db.kiemtraphieuthu(mapts);
                    int tienno = db.laytienno(mapts);
                    if(!check && tienno != 0){
                        openModal(Gravity.CENTER,mapts,tienno);
                    }
                    else{
                        Toast.makeText(ReturnBookFragment.this.getActivity(), "Không có tiền nợ cho phiếu trả này", Toast.LENGTH_SHORT).show();
                    }
                }
            }));
        }
        else {
            recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
            recyclerView.setAdapter(new PhieuTraAdapter(list, new PhieuTraAdapter.IClickItemlistener() {
                @Override
                public void onClickItemBook(PhieuTraModels phieuTraModels) {

                }
                @Override
                public void onClickPhieuThu(String mapts){

                }
            }));
            Toast.makeText(ReturnBookFragment.this.getActivity(),"Không tìm thấy phiếu trả",Toast.LENGTH_SHORT).show();
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

    public void openModal(int gravity, String mapts, int tienno) {
        Log.v("có","có");
        final Dialog dialog = new Dialog(this.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_phieuthu);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
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
                    EditText TIENTHU = dialog.findViewById(R.id.tienthu);
                    int tienthu = Integer.valueOf(TIENTHU.getText().toString());
                    if (tienthu == tienno) {
                        PhieuThuModels phieuThuModels = new PhieuThuModels(100, Integer.valueOf(mapts), tienno, tienthu);
                        Boolean rs = db.insert_phieuthu(phieuThuModels);
                        if (rs == true) {
                            Toast.makeText(ReturnBookFragment.this.getActivity(), "Thêm phiếu thu thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ReturnBookFragment.this.getActivity(), "Thêm phiếu thu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ReturnBookFragment.this.getActivity(), "Tiền thu phải bằng tiền phạt kỳ này", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }