package com.example.quanlythuvien.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

import com.example.quanlythuvien.DauSachModels;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;


public class Detail_Book extends Fragment {
    private EditText MADAUSACH, TENDAUSACH, TACGIA, NXB,NAMXB,TONGSO,DANGCHOMUON, SANCO, VITRI,THELOAI;
    private View view;
    private Button btnUpdate;
    public static final String TAG = Detail_Book.class.getName();
    SqliteDBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail__book, container, false);
//        btnback = view.findViewById(R.id.back_book_btn);
        btnUpdate = view.findViewById(R.id.update_book_btn);
        TENDAUSACH = (EditText) view.findViewById(R.id.edit_tensach);
        TACGIA = (EditText) view.findViewById(R.id.edit_tacgia);
        NXB = (EditText) view.findViewById(R.id.edit_nxb);
        NAMXB = (EditText) view.findViewById(R.id.edit_namxb);
        TONGSO = (EditText) view.findViewById(R.id.edit_tongso);
        VITRI = (EditText) view.findViewById(R.id.edit_vitri);
        THELOAI = (EditText) view.findViewById(R.id.edit_theloai);
        MADAUSACH = (EditText) view.findViewById(R.id.edit_masach);
        DANGCHOMUON = (EditText) view.findViewById(R.id.edit_dangchomuon);
        SANCO = (EditText) view.findViewById(R.id.edit_sanco);
        db = new SqliteDBHelper(Detail_Book.this.getActivity(), null, 1);

        Bundle bundleReceive = getArguments();
        if(bundleReceive != null){
            DauSachModels dauSachModels = (DauSachModels) bundleReceive.get("object_dausach");
            if(dauSachModels != null){
                MADAUSACH.setText(Integer.toString(dauSachModels.getMA_DAUSACH()));
                TENDAUSACH.setText(dauSachModels.getTENDAUSACH());
                TACGIA.setText(dauSachModels.getTACGIA());
                NXB.setText(dauSachModels.getNXB());
                NAMXB.setText(Integer.toString(dauSachModels.getNAMXB()));
                TONGSO.setText(Integer.toString(dauSachModels.getTONGSO()));
                VITRI.setText(dauSachModels.getVITRI());
                THELOAI.setText(dauSachModels.getTHELOAI());
                DANGCHOMUON.setText(Integer.toString(dauSachModels.getDANGCHOMUON()));
                SANCO.setText(Integer.toString(dauSachModels.getSANCO()));
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
                int masach = Integer.valueOf(MADAUSACH.getText().toString());
                String tendausach = TENDAUSACH.getText().toString();
                String tacgia = TACGIA.getText().toString();
                String nxb = NXB.getText().toString();
                int namxb = Integer.valueOf(NAMXB.getText().toString());
                int tongso = Integer.valueOf(TONGSO.getText().toString());
                int sanco = Integer.valueOf(TONGSO.getText().toString());
                int dangchomuon = Integer.valueOf(DANGCHOMUON.getText().toString());
                String vitri = VITRI.getText().toString();
                String theloai = THELOAI.getText().toString();
                DauSachModels dauSachModels = new DauSachModels(masach,tendausach,tacgia,nxb,namxb,tongso,vitri,sanco,dangchomuon,theloai,"");
                Boolean rs = db.update_dausach(dauSachModels);
                if (rs== true){
                    Fragment newFragment = new BookFragment();
                    androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Toast.makeText(Detail_Book.this.getActivity(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, newFragment).commit();
                } else{
                    Toast.makeText(Detail_Book.this.getActivity(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
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
           if(getFragmentManager() != null){
                getFragmentManager().popBackStack();
           }
        }
        return super.onOptionsItemSelected(item);
    }
}