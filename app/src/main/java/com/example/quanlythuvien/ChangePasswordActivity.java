package com.example.quanlythuvien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.quanlythuvien.fragment.BookFragment;
import com.example.quanlythuvien.fragment.Detail_Book;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText  pass, passwordConfirm;
    Button bt_update;
    TextView bt_back;
    SqliteDBHelper db;
    //send data
    String tendangnhap;
    AwesomeValidation mAwesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        bt_back = findViewById(R.id.txt_backto);
        pass = findViewById(R.id.edit_password);
        pass.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordConfirm = findViewById(R.id.edit_password_confirm);
        passwordConfirm.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        bt_update = findViewById(R.id.bt_capnhat);

        // nhận data
        tendangnhap = getIntent().getStringExtra("tendangnhap");

        db = new SqliteDBHelper(this, null, 1);

        mAwesomeValidation  = new AwesomeValidation(ValidationStyle.BASIC);
        //password
        mAwesomeValidation.addValidation(this, R.id.edit_password, ".{6,}", R.string.invalid_password);

        //confirm password
        mAwesomeValidation.addValidation(this, R.id.edit_password_confirm, R.id.edit_password, R.string.invalid_confirm_password);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserNameActivity.class);
                startActivity(intent);
            }
        });
        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAwesomeValidation.validate()) {
                    String password = pass.getText().toString();

                    Boolean rs = db.update_nguoidung(tendangnhap, password);
                    if (rs == true) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(ChangePasswordActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
}