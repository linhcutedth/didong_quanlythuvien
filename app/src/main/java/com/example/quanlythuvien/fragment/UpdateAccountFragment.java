package com.example.quanlythuvien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.NguoiDungModel;
import com.example.quanlythuvien.PhieuMuonActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;

import java.io.Console;

public class UpdateAccountFragment extends Fragment {

    EditText Username,Password, Email, Phone, Address, Sex;
    Button update;
    private HomeActivity homeActivity;
    private MenuItem menuItem;
    SqliteDBHelper db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_account,container,false);

        //send data -> get username
        homeActivity = (HomeActivity) getActivity();
        String user_name = homeActivity.getTendangnhap();

        db = new SqliteDBHelper(UpdateAccountFragment.this.getActivity(), null, 1);
        db.initialise();

        System.out.println(user_name);
        NguoiDungModel ng_dung = new NguoiDungModel();
        //get người dùng
        ng_dung = db.getUser(user_name);

        Username = view.findViewById(R.id.edit_username) ;
        Password = view.findViewById(R.id.edit_password) ;
        Email = view.findViewById(R.id.edit_email) ;
        Phone = view.findViewById(R.id.edit_phone) ;
        Address = view.findViewById(R.id.edit_address) ;
        Sex = view.findViewById(R.id.edit_Sex) ;
        update = view.findViewById(R.id.update_account_btn);

        //hiển thị thông tin người dùng
        Username.setText(ng_dung.getUsername());
        Password.setText(ng_dung.getPassword());
        Email.setText(ng_dung.getEmail());
        Phone.setText(ng_dung.getPhone());
        Address.setText(ng_dung.getAddress());
        Sex.setText(ng_dung.getSex());

        //Cập nhật thông tin người dùng

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Uname = Username.getText().toString();
                String Pword = Password.getText().toString();
                String email = Email.getText().toString();
                String phone = Phone.getText().toString();
                String address = Address.getText().toString();
                String sex = Sex.getText().toString();

                NguoiDungModel ng = new NguoiDungModel(Uname,Pword,email,phone,address,sex);
                Boolean rs = db.update_nguoidung(ng);

                if (rs== true){

                    Toast.makeText(UpdateAccountFragment.this.getActivity(),"Cập nhật thành công",Toast.LENGTH_SHORT).show();

                } else{
                    Toast.makeText(UpdateAccountFragment.this.getActivity(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).setActionBarTitle("Thông tin người dùng");
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
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
            Fragment newFragment = new SettingFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, newFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }
}
