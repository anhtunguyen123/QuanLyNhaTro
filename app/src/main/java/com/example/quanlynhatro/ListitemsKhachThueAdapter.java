package com.example.quanlynhatro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import Database.DatabaseHelperLogin;

public class ListitemsKhachThueAdapter extends ArrayAdapter<KhachThue> {

    private Context mContext;
    private List<KhachThue> mKhachThueList;
    private DatabaseHelperLogin databaseHelperLogin;

    public ListitemsKhachThueAdapter(Context context, List<KhachThue> khachThueList) {
        super(context, 0, khachThueList);
        mContext = context;
        mKhachThueList = khachThueList;
        databaseHelperLogin = new DatabaseHelperLogin(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.itemlistkhachthue, parent, false);
        }

        KhachThue currentKhachThue = mKhachThueList.get(position);

        TextView tvName = listItem.findViewById(R.id.tvNamekhachthue);
        tvName.setText(currentKhachThue.getName());

        TextView tvGender = listItem.findViewById(R.id.tvGender);
        tvGender.setText(currentKhachThue.getGender());

        TextView tvPhone = listItem.findViewById(R.id.tvPhone);
        tvPhone.setText(currentKhachThue.getPhone());

        TextView tvRoomName = listItem.findViewById(R.id.tvNameRoom);
        String roomName = databaseHelperLogin.getRoomNameById(currentKhachThue.getRoomId());
        tvRoomName.setText(roomName);

        TextView tvCMND = listItem.findViewById(R.id.tvCMND);
        tvCMND.setText(currentKhachThue.getCmnd());

        return listItem;
    }
}