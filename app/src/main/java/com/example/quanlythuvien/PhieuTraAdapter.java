package com.example.quanlythuvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PhieuTraAdapter extends RecyclerView.Adapter<PhieuTraAdapter.ViewHolder> {

    private ArrayList<PhieuTraModels> phieuTraModelsArrayList;
    private Context context;
    private PhieuTraAdapter.IClickItemlistener iClickItemlistener;

    public interface IClickItemlistener{
        void onClickItemBook(PhieuTraModels phieuTraModels);
    }

    public PhieuTraAdapter(ArrayList<PhieuTraModels> phieuTraModelsArrayList, Context context) {
        this.phieuTraModelsArrayList = phieuTraModelsArrayList;
        this.context = context;
    }
    public PhieuTraAdapter(ArrayList<PhieuTraModels> list,IClickItemlistener iClickItemlistener){
        this.phieuTraModelsArrayList = list;
        this.iClickItemlistener = iClickItemlistener;
    }
    @NonNull
    @Override
    public PhieuTraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phieutra_item, parent, false);
        return new PhieuTraAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuTraAdapter.ViewHolder holder, int position) {
        PhieuTraModels modal = phieuTraModelsArrayList.get(position);
        holder.ma_pts.setText("Mã phiếu trả sách: " + modal.getMa_PTS());
        holder.ma_dg.setText("Mã độc giả: " + modal.getMa_DG());
        holder.ngaytra.setText("Ngày trả: " + modal.getNgayTra());
        holder.tienphat.setText("Tiền phạt kỳ này: " + modal.getTienPhatKyNay());
        holder.ma_pts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemlistener.onClickItemBook(modal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phieuTraModelsArrayList.size();
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