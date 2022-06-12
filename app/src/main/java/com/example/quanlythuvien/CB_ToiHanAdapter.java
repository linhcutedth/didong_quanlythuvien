package com.example.quanlythuvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CB_ToiHanAdapter extends RecyclerView.Adapter<CB_ToiHanAdapter.ViewHolder> {

    private ArrayList<DocGiaModels> docgiaModalArrayList;
    private Context context;

    //Constructor
    public CB_ToiHanAdapter(ArrayList<DocGiaModels> docgiaModalArrayList, Context context) {
        this.docgiaModalArrayList = docgiaModalArrayList;
        this.context = context;
    }

    public CB_ToiHanAdapter(ArrayList<DocGiaModels> list){

        this.docgiaModalArrayList = list;
    }

    @NonNull
    @Override
    public CB_ToiHanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phieumuon_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CB_ToiHanAdapter.ViewHolder holder, int position) {
        DocGiaModels modal = docgiaModalArrayList.get(position);

        holder.HoTen.setText("Họ tên: " + modal.getHoTen());
        holder.Email.setText("Email: " + modal.getEmail());
        holder.DiaChi.setText("Địa chỉ: " + modal.getDiaChi());

    }

    @Override
    public int getItemCount() {
        return docgiaModalArrayList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView HoTen, DiaChi, Email;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            HoTen = itemView.findViewById(R.id.txt_fname);
            DiaChi = itemView.findViewById(R.id.txt_typeReader);
            Email = itemView.findViewById(R.id.txt_tinhtrang);


        }
    }
}
