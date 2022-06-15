package com.example.quanlythuvien.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.quanlythuvien.DocGiaAdapter;
import com.example.quanlythuvien.DocGiaModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.LoginActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SignupActivity;
import com.example.quanlythuvien.SqliteDBHelper;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddReaderFragment extends Fragment {

    AddReaderFragment(){

    }
    Spinner spinner;
    private EditText hoten;
    private EditText ngsinh;
    private String loaidg;
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
        AwesomeValidation mAwesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        Calendar now = Calendar.getInstance();
        String strDateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String ngayhn = sdf.format(now.getTime());

        hoten = view.findViewById(R.id.txt_hoten);
        ngsinh = view.findViewById(R.id.txt_ngsinh);
//        loaidg = view.findViewById(R.id.txt_loaidg);
        spinner = view.findViewById(R.id.spinner);
        diachi = view.findViewById(R.id.txt_diachi);
        email = view.findViewById(R.id.txt_email);
        nglt = view.findViewById(R.id.txt_nglt);
        tinhtrang = view.findViewById(R.id.txt_tinhtrangthe);
        them = view.findViewById(R.id.add_button);
        ngsinh.setInputType(InputType.TYPE_CLASS_DATETIME
                | InputType.TYPE_DATETIME_VARIATION_DATE);

        nglt.setInputType(InputType.TYPE_CLASS_DATETIME
                | InputType.TYPE_DATETIME_VARIATION_DATE);

        mAwesomeValidation.addValidation(hoten, RegexTemplate.NOT_EMPTY, "Không bỏ trống");
        mAwesomeValidation.addValidation(ngsinh, RegexTemplate.NOT_EMPTY, "Không bỏ trống");
        mAwesomeValidation.addValidation(email, RegexTemplate.NOT_EMPTY, "Không bỏ trống");
        mAwesomeValidation.addValidation(tinhtrang, RegexTemplate.NOT_EMPTY, "Không bỏ trống");
        nglt.setText(ngayhn);
        homeActivity = (HomeActivity) getActivity();
        dbHelper = new SqliteDBHelper(AddReaderFragment.this.getActivity(), null, 1);

        ngsinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay();
            }
        });

        //Sự kiện thêm
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAwesomeValidation.validate()) {
                    int ma = 0;
                    String ten = hoten.getText().toString();
                    System.out.print(ten);
                    String ngaysinh = ngsinh.getText().toString();
                    String loaidgia = spinner.getSelectedItem().toString();
                    String dia_chi = diachi.getText().toString();
                    String Email = email.getText().toString();
                    String nglapthe = nglt.getText().toString();
                    String tinhtrangthe = tinhtrang.getText().toString();
                    docGiaModels = new DocGiaModels(ma, ten, ngaysinh, loaidgia, dia_chi, Email, nglapthe, tinhtrangthe);




                    if (isDateAfter(ngaysinh, nglapthe) == true) {
                        Boolean rs = dbHelper.insert_docgia(docGiaModels);
                        if (rs == true) {

                            Fragment newFragment = new ReaderFragment();
                            androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            Toast.makeText(AddReaderFragment.this.getActivity(), "oke do", Toast.LENGTH_SHORT).show();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction()
                                    .replace(R.id.content_frame, newFragment).commit();

                        }
                    } else {
                        Toast.makeText(AddReaderFragment.this.getActivity(), "Ngày sinh phải nhỏ hơn ngày lập thẻ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("Sinh viên");
        arrayList.add("Giảng viên");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddReaderFragment.this.getActivity(),android.R.layout.simple_spinner_item, arrayList);
        dataAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item


                // Showing selected spinner item
//                Toast.makeText(parent.getContext(), "Bạn chọn: " + label,
//                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
    private void ChonNgay(){
        Calendar calendar =  Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddReaderFragment.this.getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                ngsinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam,thang,ngay);
        datePickerDialog.show();
    }

    boolean isDateAfter(String ngsinh,String nglt)
    {
        try
        {
            String myFormatString = "dd/MM/yyyy" ; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date nglthe = df.parse(nglt);
            Date ngs = df.parse(ngsinh);

            if (nglthe.after(ngs))
                return true;
            else
                return false;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}