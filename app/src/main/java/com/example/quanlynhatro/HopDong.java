package com.example.quanlynhatro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import Database.DatabaseHelperLogin;

public class HopDong extends AppCompatActivity {

    EditText edtName, edtRentname, edtPrice, edtNgayBatDau, edtNgayKetThuc;
    DatabaseHelperLogin dbHelper;
    private Button addhopdong;
    int roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hopdong);

        dbHelper = new DatabaseHelperLogin(this);

        roomId = getIntent().getIntExtra("ROOM_ID", 0);

        edtName = findViewById(R.id.edtName);
        edtRentname = findViewById(R.id.edtRentname);
        edtPrice = findViewById(R.id.edtPrice);
        edtNgayBatDau = findViewById(R.id.edtngaybatdau);
        edtNgayKetThuc = findViewById(R.id.edtngayketthuc);
        addhopdong = findViewById(R.id.btnCreateHopDong);

        loadRoomInfo(roomId);

        edtNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(edtNgayBatDau);
            }
        });

        edtNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(edtNgayKetThuc);
            }
        });

        addhopdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = edtName.getText().toString();
                String rentName = edtRentname.getText().toString();
                String price = edtPrice.getText().toString();
                String startDate = edtNgayBatDau.getText().toString();
                String endDate = edtNgayKetThuc.getText().toString();

                if (roomName.isEmpty() || rentName.isEmpty() || price.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                    Toast.makeText(HopDong.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    boolean hasContract = dbHelper.hasContractForRoom(roomId);

                    if (hasContract) {
                        Toast.makeText(HopDong.this, "Phòng này đã có hợp đồng", Toast.LENGTH_SHORT).show();
                    } else {
                        long id = dbHelper.addHopDong(roomName, rentName, price, startDate, endDate);
                        if (id > 0) {
                            Toast.makeText(HopDong.this, "Tạo hợp đồng thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HopDong.this, Home.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(HopDong.this, "Có lỗi xảy ra, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadRoomInfo(int roomId) {
        String roomName = dbHelper.getRoomNameById(roomId);
        String rentName = dbHelper.getRentNameByRoomId(roomId);
        edtName.setText(roomName);
        edtRentname.setText(rentName);
    }

    private void showDatePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(HopDong.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                editText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }
}
