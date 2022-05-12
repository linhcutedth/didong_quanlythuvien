package com.example.quanlythuvien.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuvien.DocGiaActivity;
import com.example.quanlythuvien.DocGiaAdapter;
import com.example.quanlythuvien.DocGiaModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ReaderFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private BottomNavigationView mBottomNavigationView;
    private ArrayList<DocGiaModels> contList;
    private SqliteDBHelper dbHelper;
    private TextView readTV;
    private HomeActivity homeActivity;
    private DocGiaAdapter docGiaAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_doc_gia,container,false);
        ArrayList<DocGiaModels> list = new ArrayList<DocGiaModels>();
        homeActivity = (HomeActivity) getActivity();
        list = homeActivity.getAllReader();

       /* DocGiaModels dg = new DocGiaModels(1,"Linh","31/05/2001","sv", "khu a", "ailinh@gm","12/05/2022","con han");
        DocGiaModels dg1 = new DocGiaModels(1,"Linh","31/05/2001","sv", "khu a", "ailinh@gm","12/05/2022","con han");
       list.add(dg);
       list.add(dg1);*/
        RecyclerView recyclerView = view.findViewById(R.id.idRV_DocGia);
        recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
        recyclerView.setAdapter(new DocGiaAdapter(list));

        return view;
    }


}