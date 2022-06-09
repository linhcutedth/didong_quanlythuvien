package com.example.quanlythuvien.fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ThongKeSachFragment extends Fragment {

    SqliteDBHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_ke_sach, container, false);
        db = new SqliteDBHelper(ThongKeSachFragment.this.getActivity(), null, 1);
        BarChart barChart = view.findViewById(R.id.barchart);
        ArrayList<BarEntry> thongkesach = new ArrayList<BarEntry>();
        Cursor cursor = db.ThongKeSach();
        if(cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                int nam = cursor.getInt(0);
                int demSach = cursor.getInt(1);
                thongkesach.add(new BarEntry(cursor.getInt(0),cursor.getInt(1)));
            }
        }
        BarDataSet barDataSet = new BarDataSet(thongkesach,"Số sách");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Thống kê sách theo năm");
        barChart.animateY(2000);

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((HomeActivity) getActivity()).setActionBarTitle("Thống kê đầu sách - năm xuất bản");
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
            Fragment newFragment = new HomeFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, newFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }
}