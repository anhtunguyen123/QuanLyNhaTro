package com.example.quanlynhatro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Database.DatabaseHelperLogin;

public class FixKhachHang extends AppCompatActivity {

    private EditText edtName, edtRentPrice, edtRenterPhone, edtCMND, edtRoomName;
    private RadioGroup edtGender;
    private RadioButton radioButtonNam, radioButtonNu;
    private Button btnThayDoi;
    private KhachThue khachThue;
    private String roomName;
    private String roomPrice;
    private DatabaseHelperLogin dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixitemlistkhachhang);

        dbHelper = new DatabaseHelperLogin(this);

        edtName = findViewById(R.id.edtNamekhachhang);
        edtRentPrice = findViewById(R.id.edtRentPrice);
        edtRenterPhone = findViewById(R.id.edtRenterPhone);
        edtCMND = findViewById(R.id.edtCMND);
        edtRoomName = findViewById(R.id.edtRoomName);

        edtGender = findViewById(R.id.edtgendernam);
        radioButtonNam = findViewById(R.id.radioButtonNam);
        radioButtonNu = findViewById(R.id.radioButtonNu);

        Intent intent = getIntent();
        khachThue = (KhachThue) intent.getSerializableExtra("khachThue");
        roomName = intent.getStringExtra("roomName");
        roomPrice = intent.getStringExtra("roomPrice");

        if (khachThue != null) {
            edtRoomName.setText(roomName);
            edtName.setText(khachThue.getName());
            edtRentPrice.setText(roomPrice);
            edtRenterPhone.setText(khachThue.getPhone());
            edtCMND.setText(khachThue.getCmnd());

            if ("Nam".equals(khachThue.getGender())) {
                radioButtonNam.setChecked(true);
            } else if ("Nữ".equals(khachThue.getGender())) {
                radioButtonNu.setChecked(true);
            }
        }

        btnThayDoi = findViewById(R.id.uppdatekhachthue);
        btnThayDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKhachThue();
            }
        });
    }

    private void updateKhachThue() {
        khachThue.setName(edtName.getText().toString());
        khachThue.setPhone(edtRenterPhone.getText().toString());
        khachThue.setCmnd(edtCMND.getText().toString());

        String updatedGender = "";
        if (edtGender.getCheckedRadioButtonId() == R.id.radioButtonNam) {
            updatedGender = "Nam";
        } else if (edtGender.getCheckedRadioButtonId() == R.id.radioButtonNu) {
            updatedGender = "Nữ";
        }
        khachThue.setGender(updatedGender);

        boolean result = dbHelper.updateKhachThue(khachThue.getId(), khachThue.getName(), khachThue.getGender(), khachThue.getPhone(), khachThue.getCmnd());
        if (result) {
            Toast.makeText(FixKhachHang.this, "Đã cập nhật thông tin khách thuê", Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("updatedKhachThue", khachThue);
            setResult(RESULT_OK, resultIntent);
        } else {
            Toast.makeText(FixKhachHang.this, "Không thể cập nhật thông tin khách thuê", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
