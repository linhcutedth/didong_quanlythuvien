package com.example.quanlythuvien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlythuvien.DauSachModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.LoginActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SigninActivity;
import com.example.quanlythuvien.SqliteDBHelper;

public class Book_Insert extends Fragment {
    public Book_Insert() {
    }

    EditText TENDAUSACH, TACGIA, NXB,NAMXB,TONGSO,VITRI,THELOAI;
    Button button_add;
    DauSachModels dauSachModels ;
    SqliteDBHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.insert_book, container, false);
        button_add = view.findViewById(R.id.add_button);
        TENDAUSACH = (EditText) view.findViewById(R.id.txt_tensach);
        TACGIA = (EditText) view.findViewById(R.id.txt_tacgia);
        NXB = (EditText) view.findViewById(R.id.txt_nxb);
        NAMXB = (EditText) view.findViewById(R.id.txt_namxb);
        TONGSO = (EditText) view.findViewById(R.id.txt_tongso);
        VITRI = (EditText) view.findViewById(R.id.txt_vitri);
        THELOAI = (EditText) view.findViewById(R.id.txt_theloai);
        db = new SqliteDBHelper(Book_Insert.this.getActivity(), null, 1);
            button_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tendausach = TENDAUSACH.getText().toString();
                    String tacgia = TACGIA.getText().toString();
                    String nxb = NXB.getText().toString();
                    int namxb = Integer.valueOf(NAMXB.getText().toString());
                    int tongso = Integer.valueOf(TONGSO.getText().toString());
                    int sanco = Integer.valueOf(TONGSO.getText().toString());
                    int dangchomuon = 0;
                    String vitri = VITRI.getText().toString();
                    String theloai = THELOAI.getText().toString();
                    dauSachModels = new DauSachModels(100,tendausach,tacgia,nxb,namxb,tongso,vitri,sanco,dangchomuon,theloai,"");
                    Boolean rs = db.insert_dausach(dauSachModels);
                    if (rs== true){
                        Fragment newFragment = new BookFragment();
                        androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        Toast.makeText(Book_Insert.this.getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_frame, newFragment).commit();
                    } else{
                        Toast.makeText(Book_Insert.this.getActivity(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        return view;
    }
}