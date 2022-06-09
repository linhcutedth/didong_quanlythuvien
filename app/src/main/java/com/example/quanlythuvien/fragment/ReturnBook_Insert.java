package com.example.quanlythuvien.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.quanlythuvien.DauSachModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.LoginActivity;
import com.example.quanlythuvien.PhieuMuonModels;
import com.example.quanlythuvien.PhieuTraModels;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SignupActivity;
import com.example.quanlythuvien.SqliteDBHelper;
import com.google.common.collect.Range;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class ReturnBook_Insert extends Fragment {
    Spinner spinner;
    public ReturnBook_Insert() {
    }
    TextView tv_sachtra;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    String ma_cuonsach = "";

    EditText NGAYTRA;
    Button button_add;
    HomeActivity homeActivity;
    SqliteDBHelper db;
    String ma_dg;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_return_book__insert, container, false);
        Calendar now = Calendar.getInstance();
        String strDateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String ngay = sdf.format(now.getTime());
        NGAYTRA = view.findViewById(R.id.txt_ngaytra);
        NGAYTRA.setText(ngay);
        button_add = view.findViewById(R.id.add_button);
        spinner = view.findViewById(R.id.spinner);
        tv_sachtra = view.findViewById(R.id.sachtra);
        homeActivity = (HomeActivity) getActivity();
        db = new SqliteDBHelper(ReturnBook_Insert.this.getActivity(), null, 1);
        List<String> labels = db.getDocGia_NV();
        String[] cuonsachArray = db.getCuonSach_TS_array();

        selectedLanguage = new boolean[cuonsachArray.length];

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ReturnBook_Insert.this.getActivity(),android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NgayTra = NGAYTRA.getText().toString();

                int Ma_dg = Integer.valueOf(ma_dg.split("-")[0]);
                PhieuTraModels phieuTraModels = new PhieuTraModels(1, Ma_dg, NgayTra,0);

                Boolean rs = db.insert_phieutrasach(phieuTraModels,ma_cuonsach);
                if (rs == true) {
                    Fragment newFragment = new ReturnBookFragment();
                    Toast.makeText(ReturnBook_Insert.this.getActivity(), "Thêm phiếu trả thành công", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, newFragment).commit();

                } else {
                    Toast.makeText(ReturnBook_Insert.this.getActivity(), "Thêm phiếu trả thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                ma_dg = parent.getItemAtPosition(position).toString();

                // Showing selected spinner item
//                Toast.makeText(parent.getContext(), "Bạn chọn: " + label,
//                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //click chọn sách
        tv_sachtra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ReturnBook_Insert.this.getActivity());

                // set title
                builder.setTitle("Chọn cuốn sách");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(cuonsachArray, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(cuonsachArray[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        tv_sachtra.setText(stringBuilder.toString());
                        ma_cuonsach = stringBuilder.toString();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            tv_sachtra.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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
            Fragment newFragment = new ReturnBookFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, newFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }

}