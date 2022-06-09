package com.example.quanlythuvien.fragment;

import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuvien.CTPTSAdapter;
import com.example.quanlythuvien.CTPTSModels;
import com.example.quanlythuvien.DauSachModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.PhieuThuAdapter;
import com.example.quanlythuvien.PhieuThuModels;
import com.example.quanlythuvien.PhieuTraAdapter;
import com.example.quanlythuvien.PhieuTraModels;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;

import java.util.ArrayList;


public class Detail_ReturnBook extends Fragment {
    private TextView MA_PTS, MA_DG,NGAYTRA,TIENPHATKYNAY;
    private HomeActivity homeActivity;
    private View view;
    public static final String TAG = Detail_ReturnBook.class.getName();
    SqliteDBHelper db;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detail__returnbook, container, false);
        MA_PTS = view.findViewById(R.id.txt_mapts);
        MA_DG = view.findViewById(R.id.txt_DGpts);
        NGAYTRA = view.findViewById(R.id.txt_ngaytra);
        TIENPHATKYNAY = view.findViewById(R.id.txt_tienphat);
        ArrayList<CTPTSModels> list = new ArrayList<CTPTSModels>();
        ArrayList<PhieuThuModels> listPhieuthu = new ArrayList<PhieuThuModels>();
        homeActivity = (HomeActivity) getActivity();

        db = new SqliteDBHelper(Detail_ReturnBook.this.getActivity(), null, 1);
        Bundle bundleReceive = getArguments();
        if(bundleReceive != null) {
            PhieuTraModels phieuTraModels = (PhieuTraModels) bundleReceive.get("object_phieutra");
            if (phieuTraModels != null) {
                list = homeActivity.getAllCTPTS((int)phieuTraModels.getMa_PTS());
                listPhieuthu = homeActivity.getAllPhieuThu((int)phieuTraModels.getMa_PTS());
                String hoten = db.layTenDocGia(phieuTraModels.getMa_DG());
                MA_PTS.setText("Mã phiếu trả sách: " + String.valueOf(phieuTraModels.getMa_PTS()));
                MA_DG.setText("Tên độc giả: " + hoten);
                NGAYTRA.setText("Ngày trả: " + phieuTraModels.getNgayTra());
                TIENPHATKYNAY.setText("Tiền phạt kỳ này: " + String.valueOf(phieuTraModels.getTienPhatKyNay()));

                if (list != null) {
                    recyclerView = view.findViewById(R.id.idRV_PhieuTra);
                    recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
                    recyclerView.setAdapter(new CTPTSAdapter(list));
                }
                if(listPhieuthu.size() != 0){
                    TextView tx_ctpthu = view.findViewById(R.id.text_ctpthu);
                    tx_ctpthu.setText("Chi tiết phiếu thu");
                    recyclerView1 = view.findViewById(R.id.idRV_PhieuThu);
                    recyclerView1.setLayoutManager(new LinearLayoutManager((this.getContext())));
                    recyclerView1.setAdapter(new PhieuThuAdapter(listPhieuthu));
                }
            }
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).setActionBarTitle("Chi tiết phiếu trả sách");
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