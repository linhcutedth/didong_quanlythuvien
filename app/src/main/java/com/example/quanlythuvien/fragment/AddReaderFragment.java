package com.example.quanlythuvien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quanlythuvien.DocGiaAdapter;
import com.example.quanlythuvien.DocGiaModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.LoginActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SignupActivity;
import com.example.quanlythuvien.SqliteDBHelper;

import java.io.Console;

public class AddReaderFragment extends Fragment {

    AddReaderFragment(){

    }
    private EditText hoten;
    private EditText ngsinh;
    private EditText loaidg;
    private EditText diachi;
    private EditText email;
    private EditText nglt;
    private EditText tinhtrang;
    private DocGiaModels docGiaModels;
    private Button them;
    private SqliteDBHelper dbHelper;
    private HomeActivity homeActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_reader,container,false);

        hoten = view.findViewById(R.id.txt_hoten);
        ngsinh = view.findViewById(R.id.txt_ngsinh);
        loaidg = view.findViewById(R.id.txt_loaidg);
        diachi = view.findViewById(R.id.txt_diachi);
        email = view.findViewById(R.id.txt_email);
        nglt = view.findViewById(R.id.txt_nglt);
        tinhtrang = view.findViewById(R.id.txt_tinhtrangthe);
        them = view.findViewById(R.id.add_button);
        ngsinh.setInputType(InputType.TYPE_CLASS_DATETIME
                | InputType.TYPE_DATETIME_VARIATION_DATE);
        nglt.setInputType(InputType.TYPE_CLASS_DATETIME
                | InputType.TYPE_DATETIME_VARIATION_DATE);
        homeActivity = (HomeActivity) getActivity();
        dbHelper = new SqliteDBHelper(AddReaderFragment.this.getActivity(), null, 1);

        //Sự kiện thêm

        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ma = 0;
                String ten = hoten.getText().toString();
                System.out.print(ten);
                String ngaysinh = ngsinh.getText().toString();
                String loaidgia = loaidg.getText().toString();
                String dia_chi = diachi.getText().toString();
                String Email = email.getText().toString();
                String nglapthe = nglt.getText().toString();
                String tinhtrangthe = tinhtrang.getText().toString();
                docGiaModels = new DocGiaModels(ma,ten,ngaysinh,loaidgia,dia_chi,Email,nglapthe,tinhtrangthe);

                Boolean rs = dbHelper.insert_docgia(docGiaModels);
                if (rs == true){

                            Fragment newFragment = new ReaderFragment();
                            androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    Toast.makeText(AddReaderFragment.this.getActivity(),"oke do",Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, newFragment).commit();

                } else{
                   Toast.makeText(AddReaderFragment.this.getActivity(),"sai roi",Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).setActionBarTitle("Thêm độc giả");
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
            Fragment newFragment = new ReaderFragment();
            androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, newFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }

}