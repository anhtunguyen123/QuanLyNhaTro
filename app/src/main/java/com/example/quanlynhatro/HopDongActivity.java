package com.example.quanlynhatro;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Database.DatabaseHelperLogin;

public class HopDongActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseHelperLogin databaseHelper;
    private List<HopDongCLass> hopDongList;
    private HopDongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allhopdong);

        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelperLogin(this);

        loadHopDongList();

        adapter = new HopDongAdapter(this, hopDongList);
        listView.setAdapter(adapter);
    }

    private void loadHopDongList() {
        hopDongList = databaseHelper.getAllHopDong();

        if (adapter != null) {
            adapter.clear();
            adapter.addAll(hopDongList);
            adapter.notifyDataSetChanged();
        }
    }
}
