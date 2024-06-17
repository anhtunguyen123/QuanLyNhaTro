package com.example.quanlynhatro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Database.DatabaseHelperLogin;

public class UpdateRoom extends AppCompatActivity {

    private EditText edittextViewRoomName, edittextViewRoomprice, edittextViewRoomstatust, edittextViewRoomquantity, edittextViewRoomacere;
    private Button updateRoomButton;
    private DatabaseHelperLogin dbHelper;
    private int roomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateroom);

        edittextViewRoomName = findViewById(R.id.edittextViewRoomName);
        edittextViewRoomprice = findViewById(R.id.edittextViewRoomprice);
        edittextViewRoomstatust = findViewById(R.id.edittextViewRoomstatust);
        edittextViewRoomquantity = findViewById(R.id.edittextViewRoomquantity);
        edittextViewRoomacere = findViewById(R.id.edittextViewRoomacere);
        updateRoomButton = findViewById(R.id.update_room);

        dbHelper = new DatabaseHelperLogin(this);

        roomId = getIntent().getIntExtra("ROOM_ID", -1);
        if (roomId != -1) {
            loadRoomDetails(roomId);
        }

        updateRoomButton.setOnClickListener(v -> updateRoomDetails());
    }

    private void loadRoomDetails(int roomId) {
        Room room = dbHelper.getRoomById(roomId);
        if (room != null) {
            edittextViewRoomName.setText(room.getName());
            edittextViewRoomprice.setText(room.getPrice());
            edittextViewRoomstatust.setText(room.getStatus());
            edittextViewRoomquantity.setText(room.getQuantity());
            edittextViewRoomacere.setText(room.getArea());
        }
    }

    private void updateRoomDetails() {
        String name = edittextViewRoomName.getText().toString();
        String price = edittextViewRoomprice.getText().toString();
        String status = edittextViewRoomstatust.getText().toString();
        String quantity = edittextViewRoomquantity.getText().toString();
        String area = edittextViewRoomacere.getText().toString();

        dbHelper.updateRoom(roomId, name, price, quantity, area, status);

        Toast.makeText(this, "Cập nhật phòng thành công", Toast.LENGTH_SHORT).show();
        finish();
    }
}