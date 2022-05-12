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

import com.example.quanlythuvien.DocGiaAdapter;
import com.example.quanlythuvien.DocGiaModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ReaderFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private BottomNavigationView mBottomNavigationView;
    private ArrayList<DocGiaModels> contList;
    private SqliteDBHelper dbHelper;
    private TextView readTV;
    private HomeActivity homeActivity;
    private DocGiaAdapter docGiaAdapter;
    private FloatingActionButton button_add;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_doc_gia,container,false);
        ArrayList<DocGiaModels> list = new ArrayList<DocGiaModels>();
        homeActivity = (HomeActivity) getActivity();
        list = homeActivity.getAllReader();


        RecyclerView recyclerView = view.findViewById(R.id.idRV_DocGia);
        recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
        recyclerView.setAdapter(new DocGiaAdapter(list));

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


}