package com.example.quanlythuvien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View.OnClickListener;
import com.example.quanlythuvien.DauSachAdapter;
import com.example.quanlythuvien.DauSachModels;
import com.example.quanlythuvien.DocGiaModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BookFragment extends Fragment implements OnClickListener {
    private DrawerLayout mDrawerLayout;
    private BottomNavigationView mBottomNavigationView;
    private ArrayList<DocGiaModels> contList;
    private SqliteDBHelper dbHelper;
    private TextView readTV;
    private HomeActivity homeActivity;
    private DauSachModels dauSachModels;
    private FloatingActionButton button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_book,container,false);

        View view = inflater.inflate(R.layout.fragment_book, container, false);
        ArrayList<DauSachModels> list = new ArrayList<DauSachModels>();
        homeActivity = (HomeActivity) getActivity();
        list = homeActivity.getAllBook();

        RecyclerView recyclerView = view.findViewById(R.id.idRV_CuonSach);
        recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
        recyclerView.setAdapter(new DauSachAdapter(list));

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
}
