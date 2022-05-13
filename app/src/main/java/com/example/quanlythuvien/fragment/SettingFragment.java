package com.example.quanlythuvien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.LoginActivity;
import com.example.quanlythuvien.R;

public class SettingFragment extends Fragment {
    Button button_logout;
    HomeActivity homeActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting,container,false);
        //ánh xạ
        button_logout = view.findViewById(R.id.bt_dangxuat);
        homeActivity = (HomeActivity) getActivity();
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingFragment.this.getActivity(), "Logout successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingFragment.this.getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
