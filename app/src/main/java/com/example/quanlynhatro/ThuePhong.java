package com.example.quanlynhatro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Database.DatabaseHelperLogin;

public class ThuePhong extends AppCompatActivity {

    private EditText edtRoomName, edtRoomPrice, edtNamekhachhang, edtRentPrice, edtRenterPhone, edtCMND;
    private RadioGroup edtgendernam;
    private RadioButton edtgendernu;
    private DatabaseHelperLogin databaseHelperLogin;
    private int roomId;
    private Button btnCreateRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thuephong);

        edtRoomName = findViewById(R.id.edtRoomName);
        edtRoomPrice = findViewById(R.id.edtRentPrice);
        edtNamekhachhang = findViewById(R.id.edtNamekhachhang);
        edtRentPrice = findViewById(R.id.edtRentPrice);
        edtRenterPhone = findViewById(R.id.edtRenterPhone);
        edtCMND = findViewById(R.id.edtCMND);
        edtgendernam = findViewById(R.id.edtgendernam); // RadioGroup
        edtgendernu = findViewById(R.id.edtgendernu);   // RadioButton
        btnCreateRoom = findViewById(R.id.btnCreateRoom);
        databaseHelperLogin = new DatabaseHelperLogin(this);

        // Lấy roomId từ Intent
        roomId = getIntent().getIntExtra("ROOM_ID", -1);
        if (roomId != -1) {
            Room room = databaseHelperLogin.getRoomById(roomId);
            if (room != null) {
                edtRoomName.setText(room.getName());
                edtRoomPrice.setText(room.getPrice());
            }
        }

        btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameKhachHang = edtNamekhachhang.getText().toString().trim();
                String rentPrice = edtRentPrice.getText().toString().trim();
                String renterPhone = edtRenterPhone.getText().toString().trim();
                String cmnd = edtCMND.getText().toString().trim();

                // Lấy ID của RadioButton được chọn trong RadioGroup
                int selectedRadioButtonId = edtgendernam.getCheckedRadioButtonId();
                String gender = "";
                if (selectedRadioButtonId != -1) {
                    if (selectedRadioButtonId == edtgendernu.getId()) {
                        gender = "Nữ";
                    } else {
                        gender = "Nam";
                    }
                }

                if (nameKhachHang.isEmpty() || rentPrice.isEmpty() || renterPhone.isEmpty() || cmnd.isEmpty() || gender.isEmpty()) {
                    Toast.makeText(ThuePhong.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                long result = databaseHelperLogin.addKhachThue(nameKhachHang, gender, renterPhone, cmnd, roomId);
                if (result != -1) {
                    databaseHelperLogin.updateRoomStatus(roomId, "hết");
                    Toast.makeText(ThuePhong.this, "Thuê phòng thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ThuePhong.this, "Thuê phòng thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}