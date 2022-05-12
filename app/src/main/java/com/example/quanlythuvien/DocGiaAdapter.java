package com.example.quanlythuvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DocGiaAdapter extends RecyclerView.Adapter<DocGiaAdapter.ViewHolder> {

    private ArrayList<DocGiaModels> courseModalArrayList;
    private Context context;

    //Constructor
    public DocGiaAdapter(ArrayList<DocGiaModels> courseModalArrayList, Context context) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DocGiaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.docgia_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocGiaAdapter.ViewHolder holder, int position) {
        DocGiaModels modal = courseModalArrayList.get(position);
        holder.HoTen.setText(modal.getHoTen());
        holder.NgSinh.setText(modal.getNgSinh());
        holder.LoaiDG.setText(modal.getLoaiDG());
        holder.DiaChi.setText(modal.getDiaChi());
        holder.Email.setText(modal.getEmail());
        holder.NgLapthe.setText(modal.getNgLapThe());
        holder.TinhTrang.setText(modal.getTinhTrangThe());
    }

    @Override
    public int getItemCount() {
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView HoTen, NgSinh, LoaiDG, DiaChi, Email, NgLapthe, TinhTrang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            HoTen = itemView.findViewById(R.id.txt_fname);
            NgSinh = itemView.findViewById(R.id.txt_birthdate);
            LoaiDG = itemView.findViewById(R.id.txt_typeReader);
            DiaChi = itemView.findViewById(R.id.txt_address);
            Email = itemView.findViewById(R.id.txt_email);
            NgLapthe = itemView.findViewById(R.id.txt_nglt);
            TinhTrang = itemView.findViewById(R.id.txt_tinhtrang);
        }
    }
}
