package com.example.quanlynhatro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;

public class LoadRoom extends ArrayAdapter<Room> {

    private Context mContext;
    private List<Room> mRoomList;

    public LoadRoom(Context context, List<Room> roomList) {
        super(context, 0, roomList);
        mContext = context;
        mRoomList = roomList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.listitemsroom, parent, false);
        }

        Room currentRoom = mRoomList.get(position);

        TextView nameTextView = listItem.findViewById(R.id.room_name);
        nameTextView.setText(currentRoom.getName());

        if ("hết".equalsIgnoreCase(currentRoom.getStatus())) {
            listItem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.xanhlanh));
        } else {
            listItem.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }
        return listItem;
    }
}
