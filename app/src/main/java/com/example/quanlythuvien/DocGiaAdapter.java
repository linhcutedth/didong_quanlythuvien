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

    private ArrayList<DocGiaModels> docgiaModalArrayList;
    private Context context;
    private DocGiaAdapter.IClickItemlistener iClickItemlistener;

    public interface IClickItemlistener{
        void onClickItemDocGia(DocGiaModels docGiaModels);
        void onClickDeleteDocGia(String maDocGia);
    }
    //Constructor
    public DocGiaAdapter(ArrayList<DocGiaModels> docgiaModalArrayList, Context context) {
        this.docgiaModalArrayList = docgiaModalArrayList;
        this.context = context;
    }

    public DocGiaAdapter(ArrayList<DocGiaModels> list, IClickItemlistener iClickItemlistener){

        this.docgiaModalArrayList = list;
        this.iClickItemlistener = iClickItemlistener;
    }

    @NonNull
    @Override
    public DocGiaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.docgia_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocGiaAdapter.ViewHolder holder, int position) {
        DocGiaModels modal = docgiaModalArrayList.get(position);
        holder.Ma.setText(Integer.toString(modal.getMaDG()));
        holder.HoTen.setText("Họ tên: " + modal.getHoTen());
        holder.NgSinh.setText(modal.getNgSinh());
        holder.LoaiDG.setText("Loại độc giả: " + modal.getLoaiDG());
        holder.DiaChi.setText(modal.getDiaChi());
        holder.Email.setText(modal.getEmail());
        holder.NgLapthe.setText(modal.getNgLapThe());
        holder.TinhTrang.setText("Tình trạng thẻ: " + modal.getTinhTrangThe());
        holder.HoTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemlistener.onClickItemDocGia(modal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return docgiaModalArrayList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Ma, HoTen, NgSinh, LoaiDG, DiaChi, Email, NgLapthe, TinhTrang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Ma = itemView.findViewById(R.id.txt_id);
            HoTen = itemView.findViewById(R.id.txt_fname);
            NgSinh = itemView.findViewById(R.id.txt_birthdate);
            LoaiDG = itemView.findViewById(R.id.txt_typeReader);
            DiaChi = itemView.findViewById(R.id.txt_address);
            Email = itemView.findViewById(R.id.txt_email);
            NgLapthe = itemView.findViewById(R.id.txt_nglt);
            TinhTrang = itemView.findViewById(R.id.txt_tinhtrang);


            itemView.findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemlistener.onClickDeleteDocGia(Ma.getText().toString());
                }
            });
        }
    }
}
