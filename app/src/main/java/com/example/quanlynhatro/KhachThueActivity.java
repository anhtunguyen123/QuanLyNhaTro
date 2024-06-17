package com.example.quanlynhatro;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import Database.DatabaseHelperLogin;

public class KhachThueActivity extends AppCompatActivity {

    private ListView listView;
    private KhachThueAdapter adapter;
    private DatabaseHelperLogin dbHelper;
    private ArrayList<KhachThue> khachThueList;
    private int roomId;
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtinkhackthue);

        listView = findViewById(R.id.listviewKhachthue);
        registerForContextMenu(listView);

        dbHelper = new DatabaseHelperLogin(this);
        roomId = getIntent().getIntExtra("ROOM_ID", -1);
        if (roomId == -1) {
            Toast.makeText(this, "Không tìm thấy phòng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        room = dbHelper.getRoomById(roomId);
        if (room == null) {
            Toast.makeText(this, "Không tìm thấy phòng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String roomName = room.getName();

        khachThueList = dbHelper.getAllKhachThueForRoom(roomId);
        if (khachThueList == null) {
            khachThueList = new ArrayList<>();
        }

        adapter = new KhachThueAdapter(this, khachThueList, roomName);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.showContextMenuForChild(view);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_rent_info, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        KhachThue khachThue = khachThueList.get(position);

        if (item.getItemId() == R.id.edit_rent_info) {
            handleEditAction(khachThue);
            return true;
        } else if (item.getItemId() == R.id.delete_rent_info) {
            handleDeleteAction(khachThue, position);
            return true;
        } else {
            return super.onContextItemSelected(item);
        }
    }

    private void handleEditAction(KhachThue khachThue) {
        Intent intent = new Intent(KhachThueActivity.this, FixKhachHang.class);
        intent.putExtra("khachThue", khachThue);
        intent.putExtra("roomName", room.getName());
        intent.putExtra("roomPrice", room.getPrice());
        startActivityForResult(intent, 1);
    }

    private void handleDeleteAction(KhachThue khachThue, int position) {
        boolean deleted = dbHelper.deleteKhachThue(khachThue.getId());
        if (deleted) {
            khachThueList.remove(position);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Đã xóa thông tin khách thuê", Toast.LENGTH_SHORT).show();
            dbHelper.updateRoomStatus(roomId, "còn");
        } else {
            Toast.makeText(this, "Không thể xóa thông tin khách thuê", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            KhachThue updatedKhachThue = (KhachThue) data.getSerializableExtra("updatedKhachThue");
            if (updatedKhachThue != null) {
                for (int i = 0; i < khachThueList.size(); i++) {
                    if (khachThueList.get(i).getId() == updatedKhachThue.getId()) {
                        khachThueList.set(i, updatedKhachThue);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Đã cập nhật thông tin khách thuê", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Không thể cập nhật thông tin khách thuê", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
