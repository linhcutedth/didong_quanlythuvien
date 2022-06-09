package com.example.quanlythuvien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.regex.Pattern;


public class SignupActivity extends AppCompatActivity {

    EditText username, password, passwordConfirm, email, phone, address, sex;
    Button bt_signup;
    SqliteDBHelper db;

    AwesomeValidation mAwesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        bt_signup =findViewById(R.id.bt_signup);
        username = findViewById(R.id.edit_username);
        password = findViewById(R.id.edit_password);
        password.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordConfirm = findViewById(R.id.edit_password_confirm);
        passwordConfirm.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        sex = findViewById(R.id.sex);

        db = new SqliteDBHelper(this, null, 1);

        mAwesomeValidation  = new AwesomeValidation(ValidationStyle.BASIC);

        //validation name
        mAwesomeValidation.addValidation(this, R.id.edit_username, RegexTemplate.NOT_EMPTY, R.string.ivalid_name);

        //email
        mAwesomeValidation.addValidation(this,R.id.email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        //phone
        // mAwesomeValidation.addValidation(this, R.id.phone, "[0] {1} [0-9] {9}$", R.string.invalid_phone);

        //password
        mAwesomeValidation.addValidation(this, R.id.edit_password, ".{6,}", R.string.invalid_password);

        //confirm password
        mAwesomeValidation.addValidation(this, R.id.edit_password_confirm, R.id.edit_password, R.string.invalid_confirm_password);

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check validation
                if (mAwesomeValidation.validate()){
                    //suscess
                    String user = username.getText().toString();
                    String pass = password.getText().toString();
                    String passconfirm = passwordConfirm.getText().toString();
                    String email_ = email.getText().toString();
                    String phone_ = phone.getText().toString();
                    String address_ = address.getText().toString();
                    String sex_ = sex.getText().toString();

                    if (db.check_username(user)){
                        Toast.makeText(SignupActivity.this, "Username is exist", Toast.LENGTH_SHORT).show();
                    }else{
                        if (pass.equals(passconfirm)) {

                            Boolean rs = db.insert_NguoiDung(user,pass,email_, phone_, address_, sex_);
                            if (rs== true){
                                Toast.makeText(SignupActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(SignupActivity.this, "Not successfully", Toast.LENGTH_SHORT).show();
                            }


                        }else{
                            Toast.makeText(SignupActivity.this, "Password confirm incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });
    }

}
