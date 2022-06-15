package com.example.quanlythuvien.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.PhieuMuonModels;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class BorrowBook_Insert extends Fragment {
    EditText ma_pms,ngaymuon;
    Button add_button;
    SqliteDBHelper dbHelper;
    Spinner spinner;
    SqliteDBHelper db;
    String ma_dg;
    String ma_cuonsach = "";

    TextView tv_sachmuon;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    private HomeActivity homeActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.insert_borrowbook,container,false);


        Calendar now = Calendar.getInstance();
        String strDateFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String ngay = sdf.format(now.getTime());


        ma_pms = view.findViewById(R.id.txt_mapms);

        ngaymuon  = view.findViewById(R.id.txt_ngaymuon);
        ngaymuon.setText(ngay);





//        ngaymuon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ChonNgay();
//            }
//        });
        db = new SqliteDBHelper(BorrowBook_Insert.this.getActivity(), null, 1);
        spinner = view.findViewById(R.id.spinner);
        add_button = view.findViewById(R.id.add_button);
        homeActivity = (HomeActivity) getActivity();
        dbHelper = new SqliteDBHelper(BorrowBook_Insert.this.getActivity(), null, 1);
        tv_sachmuon = view.findViewById(R.id.sachmuon);
        String[] cuonsachArray = db.getCuonSach_MS_array();
        selectedLanguage = new boolean[cuonsachArray.length];
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int Ma_pms = Integer.valueOf(ma_pms.getText().toString());
//                int Ma_dg = Integer.valueOf(ma_dg.getText().toString());
                String Ngaymuon = ngaymuon.getText().toString();
                int Ma_dg = Integer.valueOf(ma_dg.split("-")[0]);
                PhieuMuonModels phieuMuonModels = new PhieuMuonModels(10, Ma_dg, Ngaymuon);

                if (cuonsachArray.length > 4)
                {
                    Toast.makeText(BorrowBook_Insert.this.getActivity(), "Không được mượn hơn 4 quyển", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean rs = dbHelper.insert_phieumuonsach(phieuMuonModels, ma_cuonsach);
                    if (rs == true) {

                        Fragment newFragment = new BorrowFragment();
                        androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        Toast.makeText(BorrowBook_Insert.this.getActivity(), "oke do", Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.content_frame, newFragment).commit();

                    } else {
                        Toast.makeText(BorrowBook_Insert.this.getActivity(), "sai roi", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        List<String> labels = db.getDocGia_NV();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(BorrowBook_Insert.this.getActivity(),android.R.layout.simple_spinner_item, labels);
        spinner.setAdapter(dataAdapter);

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


        //click chon sach


        tv_sachmuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(BorrowBook_Insert.this.getActivity());

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
                        tv_sachmuon.setText(stringBuilder.toString());
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
                            tv_sachmuon.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });



        return view;
    }
//    private void ChonNgay(){
//        Calendar calendar =  Calendar.getInstance();
//        int ngay = calendar.get(Calendar.DATE);
//        int thang = calendar.get(Calendar.MONTH);
//        int nam = calendar.get(Calendar.YEAR);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(BorrowBook_Insert.this.getActivity(), new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                calendar.set(i, i1, i2);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//                ngaymuon.setText(simpleDateFormat.format(calendar.getTime()));
//            }
//        }, nam,thang,ngay);
//        datePickerDialog.show();
//    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setActionBarTitle("Thêm phiếu mượn sách");
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
            Fragment newFragment = new BorrowFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, newFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }


}