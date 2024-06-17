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

public class HopDongAdapter extends ArrayAdapter<HopDongCLass> {

    private Context mContext;
    private List<HopDongCLass> mHopDongList;

    public HopDongAdapter(Context context, List<HopDongCLass> hopDongList) {
        super(context, 0, hopDongList);
        mContext = context;
        mHopDongList = hopDongList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.itemhopdong, parent, false);
        }

        HopDongCLass currentHopDong = mHopDongList.get(position);

        TextView tvNameRoom = listItem.findViewById(R.id.tvNameRoom);
        tvNameRoom.setText(currentHopDong.getRoomName());

        TextView nameRent = listItem.findViewById(R.id.nameRent);
        nameRent.setText(currentHopDong.getRentName());

        TextView price = listItem.findViewById(R.id.price);
        price.setText(currentHopDong.getPrice());

        TextView dateStart = listItem.findViewById(R.id.DateStar);
        dateStart.setText(currentHopDong.getStartDate());

        TextView dateEnd = listItem.findViewById(R.id.DateEnd);
        dateEnd.setText(currentHopDong.getEndDate());

        return listItem;
    }
}
