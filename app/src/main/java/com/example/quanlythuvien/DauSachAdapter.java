package com.example.quanlythuvien;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlythuvien.fragment.BookFragment;

import java.util.ArrayList;

public class DauSachAdapter extends RecyclerView.Adapter<DauSachAdapter.ViewHolder>{

    private ArrayList<DauSachModels> cuonsachModalArrayList;
    private Context context;
    private IClickItemlistener iClickItemlistener;
    private Fragment fragment;

    public interface IClickItemlistener{
        void onClickItemBook(DauSachModels dauSachModels);
        void onClickDeleteBook(String maDauSach);
    }

    public DauSachAdapter(ArrayList<DauSachModels> cuonsachModalArrayList, Context context) {
        this.cuonsachModalArrayList = cuonsachModalArrayList;
        this.context = context;
    }
    public DauSachAdapter(ArrayList<DauSachModels> cuonsachModalArrayList, IClickItemlistener listener) {
        this.cuonsachModalArrayList = cuonsachModalArrayList;
        this.iClickItemlistener = listener;
    }

    @NonNull
    @Override
    public DauSachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuonsach_item, parent, false);
        return new DauSachAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DauSachAdapter.ViewHolder holder, int position) {
        final DauSachModels modal = cuonsachModalArrayList.get(position);
        holder.MA_DAUSACH.setText(Integer.toString(modal.getMA_DAUSACH()));
        holder.TENDAUSACH.setText(modal.getTENDAUSACH());
        holder.TACGIA.setText("Tác giả: " + modal.getTACGIA());
        holder.SANCO.setText("Sẵn có: " + modal.getSANCO());
        holder.TENDAUSACH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemlistener.onClickItemBook(modal);
            }
        });
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
            TACGIA = itemView.findViewById(R.id.txt_tacgia);
            SANCO = itemView.findViewById(R.id.txt_sanco);
            MA_DAUSACH = itemView.findViewById(R.id.txt_masach);

            itemView.findViewById(R.id.trash_cuonsach).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemlistener.onClickDeleteBook(MA_DAUSACH.getText().toString());
                }
            });
        }

    }


}
