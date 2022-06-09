package com.example.quanlythuvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PhieuThuAdapter extends RecyclerView.Adapter<PhieuThuAdapter.ViewHolder> {

    private ArrayList<PhieuThuModels> phieuThuModelsArrayList;
    private Context context;

    public PhieuThuAdapter(ArrayList<PhieuThuModels> phieuThuModelsArrayList) {
        this.phieuThuModelsArrayList = phieuThuModelsArrayList;
    }

    @NonNull
    @Override
    public PhieuThuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ctphieutra_item, parent, false);
        return new PhieuThuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuThuAdapter.ViewHolder holder, int position) {
        PhieuThuModels modal = phieuThuModelsArrayList.get(position);
        holder.maphieuthu.setText("Mã phiếu thu: " + modal.getMaPhieuThu());
        holder.ma_pts.setText("Mã phiếu trả sách: " + modal.getMa_PTS());
        holder.tienno.setText("Tiền nợ: " + modal.getTienNo());
        holder.tienthu.setText("Tiền thu: " + modal.getTienThu());
    }

    @Override
    public int getItemCount() {
        return phieuThuModelsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ma_pts, maphieuthu, tienthu, tienno;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            maphieuthu = itemView.findViewById(R.id.txt_mapts);
            ma_pts= itemView.findViewById(R.id.txt_DGpts);
            tienthu = itemView.findViewById(R.id.txt_ngaytra);
            tienno = itemView.findViewById(R.id.txt_tienphat);
        }
    }
}