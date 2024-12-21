package com.example.fitnessgym.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.DateUtils;
import com.example.fitnessgym.Hocvien;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Schedule;

import java.util.List;

public class HocvienAdapter extends RecyclerView.Adapter<HocvienAdapter.PackageViewHolder> {

    private final List<Hocvien> hocvienList;



    // Constructor
    public HocvienAdapter(List<Hocvien> hocvienList) {
        this.hocvienList = hocvienList;

    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khachcuapt, parent, false);
        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {
        Hocvien hocvien = hocvienList.get(position);
        if (hocvien == null) {
            return;
        }

        holder.ssptusername.setText("Tên : " + hocvien.getUserName());
        holder.ssptuserid.setText("ID : " + String.valueOf(hocvien.getUserId()));
        holder.ssptphoneNumber.setText("Số điện thoại : " + hocvien.getPhoneNumber());
        holder.ptsessionLeft.setText("Số buổi còn lại : " + String.valueOf(hocvien.getSessionsLeft()));



    }

    @Override
    public int getItemCount() {
        return hocvienList != null ? hocvienList.size() : 0;
    }

    // ViewHolder class
    public static class PackageViewHolder extends RecyclerView.ViewHolder {
        private final TextView ssptusername, ssptuserid, ssptphoneNumber, ptsessionLeft;

        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);

            ssptusername = itemView.findViewById(R.id.ssptusername);
            ssptuserid = itemView.findViewById(R.id.ssptuserid);
            ssptphoneNumber = itemView.findViewById(R.id.ssptphoneNumber);
            ptsessionLeft = itemView.findViewById(R.id.ptsessionLeft);
        }
    }
}
