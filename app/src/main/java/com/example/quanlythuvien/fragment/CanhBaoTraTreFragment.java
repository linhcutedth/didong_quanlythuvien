package com.example.quanlythuvien.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.quanlythuvien.CB_ToiHanAdapter;
import com.example.quanlythuvien.DocGiaModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;

import java.util.ArrayList;


public class CanhBaoTraTreFragment extends Fragment {

    SqliteDBHelper db;
    RecyclerView recyclerView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setActionBarTitle("Cảnh báo trả trễ");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem item_search = menu.findItem(R.id.toolbar_search);
        item_search.setVisible(false);
        MenuItem item_notice = menu.findItem(R.id.toolbar_notice);
        item_notice.setVisible(false);
        MenuItem item_person = menu.findItem(R.id.toolbar_person);
        item_person.setVisible(false);
        MenuItem item_back = menu.findItem(R.id.toolbar_back);
        item_back.setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar_back){
            Fragment newFragment = new HomeFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, newFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canh_bao_tra_tre, container, false);
        db = new SqliteDBHelper(CanhBaoTraTreFragment.this.getActivity(), null, 1);
        db.initialise();
        Cursor data = db.CB_TraTre();
        ArrayList<DocGiaModels> list = new ArrayList<DocGiaModels>();
        while(data.moveToNext()){
            int id = data.getInt(0);
            String hoTen = data.getString(1);
            String ngSinh = data.getString(2);
            String loaiDG = data.getString(3);
            String diaChi = data.getString(4);
            String email = data.getString(5);
            String ngLapThe = data.getString(6);
            String tinhTrang = data.getString(7);
            DocGiaModels docgia;
            docgia = new DocGiaModels(id,hoTen,ngSinh,loaiDG,diaChi,email,ngLapThe,tinhTrang);
            list.add(docgia);
        }
            recyclerView = view.findViewById(R.id.idRV_CuonSach);
            recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
            recyclerView.setAdapter(new CB_ToiHanAdapter(list));
            Button btn = view.findViewById(R.id.btn_tratre);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int lenght = list.size();
                    String emailStr = "";
                    if(lenght == 0){
                        Toast.makeText(CanhBaoTraTreFragment.this.getActivity(),"Có ai để gửi đâu", Toast.LENGTH_SHORT).show();
                    }
                    else if(lenght == 1){
                        sendMail(list.get(0).getEmail(),lenght);
                    }
                    else {
                        for(int i = 0; i < lenght; i++){
                            emailStr += list.get(i).getEmail() + ",";
                        }
                        Log.v("email1", emailStr);
                        emailStr = emailStr.substring(0, emailStr.length() - 1);
                        Log.v("email", emailStr);
                        sendMail(emailStr,lenght);
                    }
                }
            });

        return view;
        // Inflate the layout for this fragment

    }
    private void sendMail(String email, int lenght) {
        String message = "Quá hạn rồi!! Đem sách đến trả lẹ lên, đem theo tiền nữa nhé!";
        String subject = "NHẮC NHỞ QUÁ HẠN";
        Intent intent = new Intent(Intent.ACTION_SEND);
        if(lenght == 1){
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        }
        else{
            String[] recipients = email.split(",");
            intent.putExtra(Intent.EXTRA_EMAIL,recipients);
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("text/html");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}