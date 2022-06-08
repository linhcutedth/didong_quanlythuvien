package com.example.quanlythuvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {

    private ArrayList<PhieuMuonModels> phieumuonModelArrayList;
    private Context context;
    private IClickItemlistener iClickItemlistener;

    public interface IClickItemlistener{
        void onClickItemBook(PhieuMuonModels phieuMuonModels);
    }

    public PhieuMuonAdapter(ArrayList<PhieuMuonModels> phieumuonModelArrayList, Context context) {
        this.phieumuonModelArrayList = phieumuonModelArrayList;
        this.context = context;
    }
    public PhieuMuonAdapter(ArrayList<PhieuMuonModels> list, IClickItemlistener listener ){
        this.phieumuonModelArrayList = list;
        this.iClickItemlistener = listener;
    }
    @NonNull
    @Override
    public PhieuMuonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phieumuon_member, parent, false);
        return new PhieuMuonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonAdapter.ViewHolder holder, int position) {
        final PhieuMuonModels modal = phieumuonModelArrayList.get(position);
        holder.ma_pms.setText("Mã phiếu mượn sách: " + modal.getMa_PMS());
        holder.ma_dg.setText("Mã độc giả: " + modal.getMa_DG());
        holder.ngaymuon.setText("Ngày mượn: " + modal.getNgayMuon());
        holder.ma_pms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemlistener.onClickItemBook(modal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phieumuonModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ma_pms, ma_dg, ngaymuon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ma_pms = itemView.findViewById(R.id.txt_fname);
            ma_dg= itemView.findViewById(R.id.txt_typeReader);
            ngaymuon = itemView.findViewById(R.id.txt_tinhtrang);
        }
    }
}