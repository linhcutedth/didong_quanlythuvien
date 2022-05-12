package com.example.quanlythuvien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import com.example.quanlythuvien.DocGiaAdapter;
import com.example.quanlythuvien.DocGiaModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.PhieuMuonAdapter;
import com.example.quanlythuvien.PhieuMuonModels;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class BorrowFragment extends Fragment implements OnClickListener {

    private DrawerLayout mDrawerLayout;
    private BottomNavigationView mBottomNavigationView;
    private ArrayList<PhieuMuonModels> contList;
    private SqliteDBHelper dbHelper;
    private TextView readTV;
    private HomeActivity homeActivity;
    private PhieuMuonAdapter phieuMuonAdapter;
    private FloatingActionButton button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_phieumuon, container, false);


        ArrayList<PhieuMuonModels> list = new ArrayList<PhieuMuonModels>();
        homeActivity = (HomeActivity) getActivity();
        list = homeActivity.getAllPms();
        if (list != null){
            RecyclerView recyclerView = view.findViewById(R.id.pms);
            recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
            recyclerView.setAdapter(new PhieuMuonAdapter(list));
        }else
        {
            Toast.makeText(BorrowFragment.this.getActivity(),"ko duoc roi", Toast.LENGTH_SHORT).show();
        }





        button =  view.findViewById(R.id.add_button);
        button.setOnClickListener(this);


        return view;


    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_button:
                Fragment newFragment = new BorrowBook_Insert();
                androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, newFragment).commit();

                break;
        }
    }
}