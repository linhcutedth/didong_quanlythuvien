package com.example.quanlythuvien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.FragmentManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.quanlythuvien.DauSachModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.LoginActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SignupActivity;
import com.example.quanlythuvien.SqliteDBHelper;
import com.google.common.collect.Range;

import java.util.Calendar;

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
        AwesomeValidation mAwesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        button_add = view.findViewById(R.id.add_button);
        TENDAUSACH = (EditText) view.findViewById(R.id.txt_tensach);
        TACGIA = (EditText) view.findViewById(R.id.txt_tacgia);
        NXB = (EditText) view.findViewById(R.id.txt_nxb);
        NAMXB = (EditText) view.findViewById(R.id.txt_namxb);
        TONGSO = (EditText) view.findViewById(R.id.txt_tongso);
        VITRI = (EditText) view.findViewById(R.id.txt_vitri);
        THELOAI = (EditText) view.findViewById(R.id.txt_theloai);
        mAwesomeValidation.addValidation(TENDAUSACH, RegexTemplate.NOT_EMPTY, "Không bỏ trống");
        mAwesomeValidation.addValidation(TONGSO, RegexTemplate.NOT_EMPTY,"Không bỏ trống" );
        mAwesomeValidation.addValidation(NAMXB, Range.closed(2014, Calendar.getInstance().get(Calendar.YEAR)),"Nhập từ năm 2014 đến nay" );
        mAwesomeValidation.addValidation(NXB, RegexTemplate.NOT_EMPTY, "Không bỏ trống");
        mAwesomeValidation.addValidation(THELOAI, RegexTemplate.NOT_EMPTY, "Không bỏ trống");
        db = new SqliteDBHelper(Book_Insert.this.getActivity(), null, 1);
            button_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mAwesomeValidation.validate()){
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
                    } else{
                        Toast.makeText(Book_Insert.this.getActivity(), "Nhập sai rồi", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem item_search = menu.findItem(R.id.toolbar_search);
        item_search.setVisible(false);
        MenuItem item_notice = menu.findItem(R.id.toolbar_notice);
        item_notice.setVisible(false);
        MenuItem item_person = menu.findItem(R.id.toolbar_person);
        item_person.setVisible(false);
        MenuItem item_back = menu.findItem(R.id.toolbar_back);
        item_back.setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_back){
            Fragment newFragment = new BookFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, newFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }
}