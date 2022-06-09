package com.example.quanlythuvien.fragment;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.quanlythuvien.DocGiaModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;

public class Detail_DocGia extends Fragment {
    private EditText hoten, madocgia;
    private EditText ngsinh;
    private EditText loaidg;
    private EditText diachi;
    private EditText email;
    private EditText nglt;
    private EditText tinhtrang;
    private View view;
    private Button btnUpdate;
    public static final String TAG = Detail_DocGia.class.getName();
    SqliteDBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail_docgia, container, false);
        // btnback = view.findViewById(R.id.back_book_btn);
        btnUpdate = view.findViewById(R.id.update_docgia_btn);
        hoten = (EditText) view.findViewById(R.id.edit_hoten);
        ngsinh = (EditText) view.findViewById(R.id.edit_ngaysinh);
        loaidg = (EditText) view.findViewById(R.id.edit_loaidg);
        diachi = (EditText) view.findViewById(R.id.edit_diachi);
        email = (EditText) view.findViewById(R.id.edit_email);
        nglt = (EditText) view.findViewById(R.id.edit_ngaylt);
        tinhtrang = (EditText) view.findViewById(R.id.edit_tinhtrang);
        madocgia = (EditText) view.findViewById(R.id.edit_madg);

        db = new SqliteDBHelper(Detail_DocGia.this.getActivity(), null, 1);

        Bundle bundleReceive = getArguments();
        if(bundleReceive != null){
            DocGiaModels docGiaModels = (DocGiaModels) bundleReceive.get("object_docgia");
            if(docGiaModels != null){
                madocgia.setText(Integer.toString(docGiaModels.getMaDG()));
                hoten.setText(docGiaModels.getHoTen());
                ngsinh.setText(docGiaModels.getNgSinh());
                loaidg.setText(docGiaModels.getLoaiDG());
                diachi.setText(docGiaModels.getDiaChi());
                email.setText(docGiaModels.getEmail());
                nglt.setText(docGiaModels.getNgLapThe());
                tinhtrang.setText(docGiaModels.getTinhTrangThe());
            }
        }

//        btnback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(getFragmentManager() != null){
//                    getFragmentManager().popBackStack();
//                }
//            }
//        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Madocgia = Integer.valueOf(madocgia.getText().toString());
                String Hoten = hoten.getText().toString();
                String Ngsinh = ngsinh.getText().toString();
                String Loaidg = loaidg.getText().toString();
                String Diachi = diachi.getText().toString();
                String Email = email.getText().toString();
                String Nglt = nglt.getText().toString();
                String Tinhtrang = tinhtrang.getText().toString();

                DocGiaModels docGiaModels = new DocGiaModels(Madocgia,Hoten,Ngsinh,Loaidg,Diachi,Email,Nglt,Tinhtrang);
                Boolean rs = db.update_docgia(docGiaModels);
                if (rs== true){
                    Fragment newFragment = new ReaderFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Toast.makeText(Detail_DocGia.this.getActivity(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, newFragment).commit();
                } else{
                    Toast.makeText(Detail_DocGia.this.getActivity(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).setActionBarTitle("Chi tiết độc giả");
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
            Fragment newFragment = new ReaderFragment();
            androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, newFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }

}
