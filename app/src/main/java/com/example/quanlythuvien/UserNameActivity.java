package com.example.quanlythuvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuvien.fragment.ReaderFragment;

import java.util.ArrayList;

public class UserNameActivity extends AppCompatActivity {

    EditText username;
    Button bt_send;
    TextView bt_back;
    SqliteDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);

        bt_back = findViewById(R.id.txt_backto);
        bt_send = findViewById(R.id.bt_gui);
        username = findViewById(R.id.edit_username);
        db = new SqliteDBHelper(this, null, 1);
        db.initialise();

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();

                Cursor data = db.getAllUser();
                boolean kq = false;
                if (data == null){
                     kq = false;
                }
              else {
                    while (data.moveToNext()) {
                        String tendangnhap = data.getString(0);

                        if (user.equals(tendangnhap)) {
                            kq = true;
                        }
                    }

                    if (kq) {

                        Toast.makeText(UserNameActivity.this, "ok", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                        //send data
                        intent.putExtra("tendangnhap", user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(UserNameActivity.this, "tên đăng nhập không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}