package com.example.quanlythuvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DauSachAdapter extends RecyclerView.Adapter<DauSachAdapter.ViewHolder>{

    private ArrayList<DauSachModels> cuonsachModalArrayList;
    private Context context;

    public DauSachAdapter(ArrayList<DauSachModels> cuonsachModalArrayList, Context context) {
        this.cuonsachModalArrayList = cuonsachModalArrayList;
        this.context = context;
    }

    public DauSachAdapter(ArrayList<DauSachModels> cuonsachModalArrayList) {
        this.cuonsachModalArrayList = cuonsachModalArrayList;
    }

    @NonNull
    @Override
    public DauSachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuonsach_item, parent, false);
        return new DauSachAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DauSachAdapter.ViewHolder holder, int position) {
        DauSachModels modal = cuonsachModalArrayList.get(position);
        holder.TENDAUSACH.setText("Tên đầu sách: " + modal.getTENDAUSACH());
        holder.TACGIA.setText("Tác giả: " + modal.getTACGIA());
        holder.SANCO.setText("Sẵn có: " + modal.getSANCO());
    }

    @Override
    public int getItemCount() {
        return cuonsachModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView MA_DAUSACH, TENDAUSACH, TACGIA, NXB, NAMXB, TONGSO, VITRI,SANCO,DANGCHOMUON,THELOAI,HINHANH;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            TENDAUSACH = itemView.findViewById(R.id.txt_tendausach);
            //  NgSinh = itemView.findViewById(R.id.txt_birthdate);
            TACGIA = itemView.findViewById(R.id.txt_tacgia);
            //DiaChi = itemView.findViewById(R.id.txt_address);
            // Email = itemView.findViewById(R.id.txt_email);
            // NgLapthe = itemView.findViewById(R.id.txt_nglt);
            SANCO = itemView.findViewById(R.id.txt_sanco);
        }
    }
}
