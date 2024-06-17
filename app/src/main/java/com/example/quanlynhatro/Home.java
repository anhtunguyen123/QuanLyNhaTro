package com.example.quanlynhatro;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import Database.DatabaseHelperLogin;

public class Home extends AppCompatActivity {
    private Button addroom;
    private LoadRoom mAdapter;
    private ListView listView;
    private DatabaseHelperLogin dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });


        addroom = findViewById(R.id.add_room);
        listView = findViewById(R.id.listview);

        dbHelper = new DatabaseHelperLogin(this);

        loadRoomList();
        registerForContextMenu(listView);

        addroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AddRoom.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRoomList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }
    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.logout) {
                    AlertDialog show = new AlertDialog.Builder(Home.this)
                            .setTitle("Đăng xuất")
                            .setMessage("Bạn có chắc chắn muốn đăng xuất hay không?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(Home.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(Home.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Không", null)
                            .show();
                    return true;
                }
                else if (item.getItemId() == R.id.khachthue) {
                    Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Home.this, ListitemKhachThueActivity.class);
                    startActivity(intent);
                    return true;
                }
                else if (item.getItemId() == R.id.phongtro) {
                    Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Home.this, Home.class);
                    startActivity(intent);
                    return true;
                }
                else if (item.getItemId() == R.id.hopdong) {
                    Toast.makeText(Home.this, "", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Home.this, HopDongActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        popup.show();
    }
    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();
        assert info != null;
        int position = info.position;
        Room room = mAdapter.getItem(position);

        if (menuItem.getItemId() == R.id.view_info) {
            if (room != null) {
                Room detailedRoom = dbHelper.getRoomById(room.getId());
                showDialog(detailedRoom);
            }
            return true;
        } else if (menuItem.getItemId() == R.id.rent_room) {
            assert room != null;
            if (dbHelper.checkRoom(room.getId())) {
                Toast.makeText(this, "Phòng này đã có người thuê", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Home.this, ThuePhong.class);
                intent.putExtra("ROOM_ID", room.getId());
                startActivity(intent);
            }
            return true;
        }else if (menuItem.getItemId() == R.id.thongtinkhachthue) {
            if (room != null) {
                Intent intent = new Intent(Home.this, KhachThueActivity.class);
                intent.putExtra("ROOM_ID", room.getId());
                startActivity(intent);
            }
            return true;
        }else if (menuItem.getItemId() == R.id.update_room) {
            if (room != null) {
                Intent intent = new Intent(Home.this, UpdateRoom.class);
                intent.putExtra("ROOM_ID", room.getId());
                startActivity(intent);
            }
            return true;
        }
        else if (menuItem.getItemId() == R.id.delete_room) {
            assert room != null;
            if (dbHelper.checkRoom(room.getId())) {
                Toast.makeText(this, "Phòng này đã có người thuê", Toast.LENGTH_SHORT).show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Xóa phòng")
                        .setMessage("Bạn có chắc chắn muốn xóa phòng này không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dbHelper.deleteRoom(room.getId());
                                loadRoomList();
                                Toast.makeText(Home.this, "Phòng đã được xóa", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
            return true;
        } if (menuItem.getItemId() == R.id.hopdong_room) {
            if (room != null) {
                Intent intent = new Intent(Home.this, HopDong.class);
                intent.putExtra("ROOM_ID", room.getId());
                startActivity(intent);
            }
        }
        return super.onContextItemSelected(menuItem);
    }
//show thông tin phòng trọ
    private void showDialog(Room room) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.inforroom, null);

        TextView textViewRoomName = dialogView.findViewById(R.id.textViewRoomName);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewRoomprice = dialogView.findViewById(R.id.textViewRoomprice);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewRoomquantity = dialogView.findViewById(R.id.textViewRoomquantity);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})TextView textViewRoomStatus = dialogView.findViewById(R.id.textViewRoomstatust);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})TextView textViewRoomAcare = dialogView.findViewById(R.id.textViewRoomacere);

        textViewRoomName.setText(room.getName());
        textViewRoomprice.setText(room.getPrice());
        textViewRoomStatus.setText(room.getStatus());
        textViewRoomquantity.setText(room.getQuantity());
        textViewRoomAcare.setText(room.getArea());


        builder.setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
//Load danh sách
    private void loadRoomList() {
        List<Room> roomList = dbHelper.getAllRooms();
        if (mAdapter == null) {
            mAdapter = new LoadRoom(this, roomList);
            listView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(roomList);
            mAdapter.notifyDataSetChanged();
        }
    }
}
