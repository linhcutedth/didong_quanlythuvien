package com.example.quanlythuvien.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MenuItem;

import com.example.quanlythuvien.ChiTietMuonSachModels;
import com.example.quanlythuvien.ChiTietPhieuMuonAdapter;
import com.example.quanlythuvien.DauSachModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.PhieuMuonModels;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;

import java.util.ArrayList;


public class Detail_Pms extends Fragment {

    private TextView MA_PMS, MA_DG, NGAYMUON;
    private View view;
    private Button btnUpdate;
    private HomeActivity homeActivity;
    public static final String TAG = Detail_Pms.class.getName();
    SqliteDBHelper db;
    private RecyclerView recyclerView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_detail_pms, container, false);
        btnUpdate = view.findViewById(R.id.update_book_btn);
        MA_PMS = (TextView) view.findViewById(R.id.edit_mapms);
        MA_DG = (TextView) view.findViewById(R.id.edit_madg);
        NGAYMUON = (TextView) view.findViewById(R.id.edit_ngaymuon);

        ArrayList<ChiTietMuonSachModels> list = new ArrayList<ChiTietMuonSachModels>();
        homeActivity = (HomeActivity) getActivity();


        db = new SqliteDBHelper(Detail_Pms.this.getActivity(), null, 1);

        Bundle bundleReceive = getArguments();
        if(bundleReceive != null){
            PhieuMuonModels phieuMuonModels = (PhieuMuonModels) bundleReceive.get("object_pms");
            if(phieuMuonModels != null){

                list = homeActivity.getAllCTMS((int)phieuMuonModels.getMa_PMS());
                String hoten = db.layTenDocGia(phieuMuonModels.getMa_DG());
                MA_PMS.setText("Mã phiếu mượn sách: " + phieuMuonModels.getMa_PMS());
                MA_DG.setText("Họ tên độc giả: "+ hoten);
                NGAYMUON.setText("Ngày mượn: " +phieuMuonModels.getNgayMuon());

                if (list != null) {
                    recyclerView = view.findViewById(R.id.idRV_PhieuTra);
                    recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
                    recyclerView.setAdapter(new ChiTietPhieuMuonAdapter(list));
                }

            }
        }


        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setActionBarTitle("Chi tiết phiếu mượn sách");
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
            Fragment newFragment = new BorrowFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, newFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }
}