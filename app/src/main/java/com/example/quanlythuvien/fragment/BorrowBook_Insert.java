package com.example.quanlythuvien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.PhieuMuonModels;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;

public class BorrowBook_Insert extends Fragment {
    EditText ma_pms,ma_dg,ngaymuon;
    Button add_button;
    SqliteDBHelper dbHelper;
    private HomeActivity homeActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.insert_borrowbook,container,false);


        ma_pms = view.findViewById(R.id.txt_mapms);
        ma_dg = view.findViewById(R.id.txt_madg);
        ngaymuon  = view.findViewById(R.id.txt_ngaymuon);
        add_button = view.findViewById(R.id.add_button);
        homeActivity = (HomeActivity) getActivity();
        dbHelper = new SqliteDBHelper(BorrowBook_Insert.this.getActivity(), null, 1);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int Ma_pms = Integer.valueOf(ma_pms.getText().toString());
                int Ma_dg = Integer.valueOf(ma_dg.getText().toString());
                String Ngaymuon = ngaymuon.getText().toString();

                PhieuMuonModels phieuMuonModels = new PhieuMuonModels(Ma_pms, Ma_dg, Ngaymuon);

                Boolean rs = dbHelper.insert_phieumuonsach(phieuMuonModels);
                if (rs == true){

                    Fragment newFragment = new BorrowFragment();
                    androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Toast.makeText(BorrowBook_Insert.this.getActivity(),"oke do",Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, newFragment).commit();

                } else{
                    Toast.makeText(BorrowBook_Insert.this.getActivity(),"sai roi",Toast.LENGTH_SHORT).show();
                }


            }
        });


        return view;
    }
}