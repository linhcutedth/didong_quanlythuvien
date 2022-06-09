package com.example.quanlythuvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CTPTSAdapter extends RecyclerView.Adapter<CTPTSAdapter.ViewHolder> {

    private ArrayList<CTPTSModels> ctptsModelsArrayList;
    private Context context;

    public CTPTSAdapter(ArrayList<CTPTSModels> ctptsModelsArrayList, Context context) {
        this.ctptsModelsArrayList = ctptsModelsArrayList;
        this.context = context;
    }
    public CTPTSAdapter(ArrayList<CTPTSModels> list){
        this.ctptsModelsArrayList = list;
    }
    @NonNull
    @Override
    public CTPTSAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ctphieutra_item, parent, false);
        return new CTPTSAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CTPTSAdapter.ViewHolder holder, int position) {
        CTPTSModels modal = ctptsModelsArrayList.get(position);
        holder.ma_dg.setText("Mã sách: " + modal.getMaSach());
        holder.ma_pts.setText("Tên đầu sách: " + modal.getTenSach());
        holder.ngaytra.setText("Số ngày trả trễ: " + String.valueOf(modal.getSoNgayTraTre()));
        holder.tienphat.setText("Tiền phạt: " + modal.getTienPhat());
    }

    @Override
    public int getItemCount() {
        return ctptsModelsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ma_pts, ma_dg, ngaytra, tienphat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ma_pts = itemView.findViewById(R.id.txt_mapts);
            ma_dg= itemView.findViewById(R.id.txt_DGpts);
            ngaytra = itemView.findViewById(R.id.txt_ngaytra);
            tienphat = itemView.findViewById(R.id.txt_tienphat);
        }
    }
}