package com.example.quanlynhatro;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class KhachThueAdapter extends ArrayAdapter<KhachThue> {

    private Context context;
    private ArrayList<KhachThue> khachThueList;
    private String roomName;

    public KhachThueAdapter(Context context, ArrayList<KhachThue> khachThueList, String roomName) {
        super(context, 0, khachThueList);
        this.context = context;
        this.khachThueList = khachThueList;
        this.roomName = roomName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.itemkhachthue, parent, false);
        }

        KhachThue khachThue = getItem(position);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvGender = view.findViewById(R.id.tvGender);
        TextView tvPhone = view.findViewById(R.id.tvPhone);
        TextView tvNameRoom = view.findViewById(R.id.tvNameRoom);
        TextView tvCMND = view.findViewById(R.id.tvCMND);

        tvName.setText("Tên:           " + khachThue.getName());
        tvGender.setText("Giới tính:   " + khachThue.getGender());
        tvPhone.setText("Số điện thoại:" + khachThue.getPhone());
        tvNameRoom.setText("Số Phòng:  " + roomName);
        tvCMND.setText("CMND/CCCD:     " + khachThue.getCmnd());

        return view;
    }
}
