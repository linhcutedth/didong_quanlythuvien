package com.example.quanlythuvien.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import androidx.fragment.app.FragmentManager;

import com.example.quanlythuvien.DauSachAdapter;
import com.example.quanlythuvien.DauSachModels;
import com.example.quanlythuvien.DocGiaAdapter;
import com.example.quanlythuvien.DocGiaModels;
import com.example.quanlythuvien.HomeActivity;
import com.example.quanlythuvien.PhieuMuonAdapter;
import com.example.quanlythuvien.PhieuMuonModels;
import com.example.quanlythuvien.R;
import com.example.quanlythuvien.SqliteDBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class BorrowFragment extends Fragment implements OnClickListener,  SearchView.OnQueryTextListener {

    private DrawerLayout mDrawerLayout;
    private BottomNavigationView mBottomNavigationView;
    private ArrayList<PhieuMuonModels> contList;
    private SqliteDBHelper dbHelper;
    private TextView readTV;
    private HomeActivity homeActivity;
    private MenuItem menuItem;
    private SearchView searchView;
    private PhieuMuonAdapter phieuMuonAdapter;
    private FloatingActionButton button;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_phieumuon, container, false);


        ArrayList<PhieuMuonModels> list = new ArrayList<PhieuMuonModels>();
        homeActivity = (HomeActivity) getActivity();
        list = homeActivity.getAllPms();
        if (list != null){
            recyclerView = view.findViewById(R.id.pms);
            recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
            recyclerView.setAdapter(new PhieuMuonAdapter(list, new PhieuMuonAdapter.IClickItemlistener() {
                @Override
                public void onClickItemBook(PhieuMuonModels phieuMuonModels) {
                        homeActivity.DetailPms(phieuMuonModels);
                }
            }));
        }else
        {
            Toast.makeText(BorrowFragment.this.getActivity(),"ko duoc roi", Toast.LENGTH_SHORT).show();
        }





        button =  view.findViewById(R.id.add_button);
        button.setOnClickListener(this);


        return view;


    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_button:
                Fragment newFragment = new BorrowBook_Insert();
                androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, newFragment).commit();

                break;
        }
    }

    // search
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        ((HomeActivity) getActivity()).setActionBarTitle("Phi???u m?????n s??ch");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menuItem = menu.findItem(R.id.toolbar_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconified(true);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void mysearch(String query) {
        ArrayList<PhieuMuonModels> list = new ArrayList<PhieuMuonModels>();
        homeActivity = (HomeActivity) getActivity();
        list = homeActivity.searchpms(query);
        if(list.size() !=0){
            recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
            recyclerView.setAdapter(new PhieuMuonAdapter(list, new PhieuMuonAdapter.IClickItemlistener() {
                @Override
                public void onClickItemBook(PhieuMuonModels phieuMuonModels) {
                    homeActivity.DetailPms(phieuMuonModels);
                }

            }));

        }
        else {
            recyclerView.setLayoutManager(new LinearLayoutManager((this.getContext())));
            recyclerView.setAdapter(new PhieuMuonAdapter(list, new PhieuMuonAdapter.IClickItemlistener() {
                @Override
                public void onClickItemBook(PhieuMuonModels phieuMuonModels) {}

            }));

            Toast.makeText(BorrowFragment.this.getActivity(),"Kh??ng t??m th???y s??ch",Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        mysearch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        mysearch(query);
        return false;
    }
}

