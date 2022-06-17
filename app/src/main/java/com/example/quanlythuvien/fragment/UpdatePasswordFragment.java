package com.example.quanlythuvien.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.quanlythuvien.ChangePasswordActivity;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.LoginActivity;
import com.example.quanlythuvien.PhieuMuonModels;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;

import java.io.Console;
import java.util.ArrayList;

public class UpdatePasswordFragment extends Fragment {

    EditText old_pass, new_pass, new_passConfirm;
    Button bt_update;
    SqliteDBHelper db;
    CheckBox show_pass;
    //send data
    String tendangnhap;
    HomeActivity homeActivity;
    AwesomeValidation mAwesomeValidation;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_password, container, false);

        show_pass = view.findViewById(R.id.showpass);
        old_pass = view.findViewById(R.id.edit_password_old);
        new_pass = view.findViewById(R.id.edit_password);
        new_passConfirm = view.findViewById(R.id.edit_password_confirm);
        old_pass.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        new_pass.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        new_passConfirm.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        bt_update = view.findViewById(R.id.bt_capnhat);
        homeActivity = (HomeActivity) getActivity();

        db = new SqliteDBHelper(UpdatePasswordFragment.this.getActivity(), null, 1);
        //validation
        mAwesomeValidation  = new AwesomeValidation(ValidationStyle.BASIC);

        //old_password
        mAwesomeValidation.addValidation(UpdatePasswordFragment.this.getActivity(), R.id.edit_password_old, ".{6,}", R.string.invalid_password);

        //password
        mAwesomeValidation.addValidation(UpdatePasswordFragment.this.getActivity(), R.id.edit_password, ".{6,}", R.string.invalid_password);

        //confirm password
        mAwesomeValidation.addValidation(UpdatePasswordFragment.this.getActivity(), R.id.edit_password_confirm, R.id.edit_password, R.string.invalid_confirm_password);

        tendangnhap = homeActivity.getTendangnhap();

        show_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!show_pass.isChecked()){
                    old_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    new_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    new_passConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else{
                    old_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    new_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    new_passConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
            }
        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mAwesomeValidation.validate()) {
                    //success
                    //get old pass
                    Cursor data = db.getPassword(tendangnhap);
                    String old_password = null;
                    while(data.moveToNext()){
                        old_password = data.getString(0);
                    }

                    //check old pass
                    if (old_password.equals(old_pass.getText().toString())) {

                        String password = new_pass.getText().toString();

                        Boolean rs = db.update_nguoidung(tendangnhap, password);
                        if (rs == true) {
                            Toast.makeText(UpdatePasswordFragment.this.getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(UpdatePasswordFragment.this.getActivity(), "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UpdatePasswordFragment.this.getActivity(), "Mật khẩu củ không chính xác", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        return view;
    }

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).setActionBarTitle("Cập nhật mật khẩu");
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
