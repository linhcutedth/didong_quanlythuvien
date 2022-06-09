package com.example.quanlythuvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChiTietPhieuMuonAdapter extends RecyclerView.Adapter<ChiTietPhieuMuonAdapter.ViewHolder> {

    private ArrayList<ChiTietMuonSachModels> ctpmsModelsArrayList;
    private Context context;

    public ChiTietPhieuMuonAdapter(ArrayList<ChiTietMuonSachModels> ctpmsModelsArrayList, Context context) {
        this.ctpmsModelsArrayList = ctpmsModelsArrayList;
        this.context = context;
    }
    public ChiTietPhieuMuonAdapter(ArrayList<ChiTietMuonSachModels> list){
        this.ctpmsModelsArrayList = list;
    }
    @NonNull
    @Override
    public ChiTietPhieuMuonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chitietmuonsach_item, parent, false);
        return new ChiTietPhieuMuonAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietPhieuMuonAdapter.ViewHolder holder, int position) {
        ChiTietMuonSachModels modal = ctpmsModelsArrayList.get(position);
        holder.tensach.setText("Tên đầu sách: " + modal.getTen_sach());
        holder.masach.setText("Mã sách: " + modal.getMa_sach());
        holder.tinhtrang.setText("Tình trạng: " + modal.getTinh_trang());

    }

    @Override
    public int getItemCount() {
        return ctpmsModelsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tensach, masach, tinhtrang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tensach = itemView.findViewById(R.id.tensach);
            masach= itemView.findViewById(R.id.masach);
            tinhtrang = itemView.findViewById(R.id.tinhtrang);
        }
    }
}