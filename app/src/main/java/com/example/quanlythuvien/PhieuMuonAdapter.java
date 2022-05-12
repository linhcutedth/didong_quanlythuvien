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

    public PhieuMuonAdapter(ArrayList<PhieuMuonModels> phieumuonModelArrayList, Context context) {
        this.phieumuonModelArrayList = phieumuonModelArrayList;
        this.context = context;
    }
    public PhieuMuonAdapter(ArrayList<PhieuMuonModels> list){
        this.phieumuonModelArrayList = list;
    }
    @NonNull
    @Override
    public PhieuMuonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phieumuon_member, parent, false);
        return new PhieuMuonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonAdapter.ViewHolder holder, int position) {
        PhieuMuonModels modal = phieumuonModelArrayList.get(position);
        holder.ma_pms.setText("maPMS: " + modal.getMa_PMS());
        holder.ma_dg.setText("maDG: " + modal.getMa_DG());
        holder.ngaymuon.setText("ngay muon: " + modal.getNgayMuon());
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