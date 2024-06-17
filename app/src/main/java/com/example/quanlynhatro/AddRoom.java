package com.example.quanlynhatro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Database.DatabaseHelperLogin;

public class AddRoom extends AppCompatActivity {
    private EditText edtName, edtPrice, edtQuantity, edtArea, edtStatus;
    private Button btnCreateRoom;
    private DatabaseHelperLogin dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addroom);

        dbHelper = new DatabaseHelperLogin(this);

        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtQuantity = findViewById(R.id.edtquantity);
        edtArea = findViewById(R.id.edtArea);
        edtStatus = findViewById(R.id.edtStatus);

        btnCreateRoom = findViewById(R.id.btnCreateRoom);
        btnCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToDatabase();
            }
        });
    }

    private void addToDatabase() {
        String name = edtName.getText().toString();
        String price = edtPrice.getText().toString();
        String quantity = edtQuantity.getText().toString();
        String area = edtArea.getText().toString();
        String status = edtStatus.getText().toString();

        if (name.isEmpty() || price.isEmpty() || quantity.isEmpty() || area.isEmpty() || status.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.checkRoomName(name)) {
            Toast.makeText(this, "Phòng đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        long newRowId = dbHelper.addRoom(name, price, quantity, area, status); // Sử dụng phương thức addRoom

        if (newRowId != -1) {
            Toast.makeText(this, "Thêm phòng thành công", Toast.LENGTH_SHORT).show();
            edtName.setText("");
            edtPrice.setText("");
            edtQuantity.setText("");
            edtArea.setText("");
            edtStatus.setText("");
        } else {
            Toast.makeText(this, "Thêm phòng thất bại", Toast.LENGTH_SHORT).show();
        }
    }


}
