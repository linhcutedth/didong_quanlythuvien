package com.example.quanlythuvien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.LoginActivity;
import com.example.quanlythuvien.R;

public class SettingFragment extends Fragment {
    TextView info, changepass;

    Button button_logout;
    HomeActivity homeActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting,container,false);
        //ánh xạ
        button_logout = view.findViewById(R.id.bt_dangxuat);
        homeActivity = (HomeActivity) getActivity();

        homeActivity = (HomeActivity) getActivity();
        TextView welcome = view.findViewById(R.id.textView);
        welcome.setText(homeActivity.getTendangnhap());

        info = view.findViewById(R.id.tv_profile);
        changepass = view.findViewById(R.id.tv_changepassword);

        //đăng xuất
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingFragment.this.getActivity(), "Logout successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingFragment.this.getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        // cập nhật thông tin

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new UpdateAccountFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, newFragment).commit();
            }
        });

        // đổi mật khẩu
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new UpdatePasswordFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, newFragment).commit();
            }
        });

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).setActionBarTitle("Cài đặt");
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem item_search = menu.findItem(R.id.toolbar_search);
        item_search.setVisible(false);
        MenuItem item_back = menu.findItem(R.id.toolbar_back);
        item_back.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
