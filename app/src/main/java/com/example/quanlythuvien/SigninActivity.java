package com.example.quanlythuvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SigninActivity extends AppCompatActivity {

    EditText username, password, passwordConfirm;
    Button bt_signup;
    SqliteDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        bt_signup =findViewById(R.id.bt_signup);
        username = findViewById(R.id.edit_username);
        password = findViewById(R.id.edit_password);
        passwordConfirm = findViewById(R.id.edit_password_confirm);
        db = new SqliteDBHelper(this, null, 1);

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String passconfirm = passwordConfirm.getText().toString();

                if (user.equals("") || password.equals("") || passconfirm.equals("")) {
                    Toast.makeText(SigninActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(passconfirm)) {
                        int id = Integer.parseInt(null);
                        db.QueryData("insert into NGUOIDUNG values (" + "user" + "," + pass +"," + passconfirm + ")");

                        Toast.makeText(SigninActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SigninActivity.this, LoginActivity.class);
                        startActivity(intent);

                    }
                }
            }
        });
    }

}
