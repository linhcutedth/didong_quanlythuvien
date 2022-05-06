package com.example.quanlythuvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button bt_login, bt_signin;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bt_login = findViewById(R.id.bt_login);
        bt_signin =findViewById(R.id.bt_signin);
        username = findViewById(R.id.edit_username);
        password = findViewById(R.id.edit_password);
        db = new DBHelper(this);

        //db.QueryData("INSERT INTO Nguoidung VALUES (null, 'linh', 'linh', 'linh')");
        //db.QueryData("INSERT INTO Nguoidung VALUES (null, 'hai', 'hai', 'hai')");

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this,"Please enter all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean check_userpass = db.check_Username_password(user, pass);
                    if (check_userpass == true){
                        Toast.makeText(LoginActivity.this,"Login successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this,"Invalid Credentials", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

        bt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SigninActivity.class);
                startActivity(intent);
            }
        });
    }
}