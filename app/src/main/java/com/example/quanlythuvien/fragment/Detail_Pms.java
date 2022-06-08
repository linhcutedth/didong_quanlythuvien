package com.example.quanlythuvien.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.MenuItem;
import com.example.quanlythuvien.DauSachModels;
import com.example.quanlythuvien.PhieuMuonModels;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;


public class Detail_Pms extends Fragment {

    private EditText MA_PMS, MA_DG, NGAYMUON;
    private View view;
    private Button btnUpdate;
    public static final String TAG = Detail_Pms.class.getName();
    SqliteDBHelper db;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_detail_pms, container, false);
        btnUpdate = view.findViewById(R.id.update_book_btn);
        MA_PMS = (EditText) view.findViewById(R.id.edit_mapms);
        MA_DG = (EditText) view.findViewById(R.id.edit_madg);
        NGAYMUON = (EditText) view.findViewById(R.id.edit_ngaymuon);

        db = new SqliteDBHelper(Detail_Pms.this.getActivity(), null, 1);

        Bundle bundleReceive = getArguments();
        if(bundleReceive != null){
            PhieuMuonModels phieuMuonModels = (PhieuMuonModels) bundleReceive.get("object_pms");
            if(phieuMuonModels != null){
                MA_PMS.setText(Integer.toString(phieuMuonModels.getMa_PMS()));
                MA_DG.setText(Integer.toString(phieuMuonModels.getMa_DG()));
                NGAYMUON.setText(phieuMuonModels.getNgayMuon());

            }
        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ma_pms = Integer.valueOf(MA_PMS.getText().toString());
                int ma_dg = Integer.valueOf(MA_DG.getText().toString());
                String ngaymuon = NGAYMUON.getText().toString();
                PhieuMuonModels phieuMuonModels = new PhieuMuonModels(ma_pms, ma_dg, ngaymuon);
                Boolean rs = db.update_pms(phieuMuonModels);
                if (rs== true){
                    Fragment newFragment = new BorrowFragment();
                    androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Toast.makeText(Detail_Pms.this.getActivity(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, newFragment).commit();
                } else{
                    Toast.makeText(Detail_Pms.this.getActivity(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
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