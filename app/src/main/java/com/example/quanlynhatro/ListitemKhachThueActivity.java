package com.example.quanlynhatro;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Database.DatabaseHelperLogin;

public class ListitemKhachThueActivity extends AppCompatActivity {
    private ListView listView;
    private DatabaseHelperLogin databaseHelper;
    private List<KhachThue> KhachThueList;
    private ListitemsKhachThueAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allinfornguoithue);

        listView = findViewById(R.id.listviewKhachthue);
        databaseHelper = new DatabaseHelperLogin(this);

        loadKhachThueList();

        adapter = new ListitemsKhachThueAdapter(this, KhachThueList);
        listView.setAdapter(adapter);
    }

    private void loadKhachThueList() {
        KhachThueList = databaseHelper.getAllKhachThue();

        // Update the adapter with the new data
        if (adapter != null) {
            adapter.clear();
            adapter.addAll(KhachThueList);
            adapter.notifyDataSetChanged();
        }
    }
}
