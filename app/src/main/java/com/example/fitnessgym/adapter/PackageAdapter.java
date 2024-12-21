package com.example.fitnessgym.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.Package.ClickItemPackageListener;
import com.example.fitnessgym.Package.Package;
import com.example.fitnessgym.R;

import java.util.List;


public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {

    private final List<Package> packageList;
    private final ClickItemPackageListener clickItemPackageListener;


    // Constructor
    public PackageAdapter(List<Package> packageList, ClickItemPackageListener clickItemPackageListener) {
        this.packageList = packageList;
        this.clickItemPackageListener = clickItemPackageListener;
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaithetap, parent, false);
        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {
        Package aPackage = packageList.get(position);
        if (aPackage == null) {
            return;
        }
        String type;
        if(aPackage.getType().equals("Monthly")) {
            type = "Tháng";
        } else {
            type = "Năm";
        }
        holder.id.setText("ID : " + String.valueOf(aPackage.getId()));
        holder.name.setText(aPackage.getName());
        holder.duration.setText("Thời gian : " + String.valueOf(aPackage.getDuration()) +" " + type);
        holder.type.setText("Loại gói tập : " + aPackage.getType());
        holder.price.setText("Giá : " + aPackage.getPrice() + "đ");

        // Thiết lập sự kiện click cho mỗi item
        holder.itemView.setOnClickListener(v -> clickItemPackageListener.onClickItemPackage(aPackage));
    }

    @Override
    public int getItemCount() {
        return packageList != null ? packageList.size() : 0;
    }

    // ViewHolder class
    public static class PackageViewHolder extends RecyclerView.ViewHolder {
        private final TextView name, duration, type, price, id;

        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.package_id);
            name = itemView.findViewById(R.id.package_name);
            duration = itemView.findViewById(R.id.package_duration);
            type = itemView.findViewById(R.id.package_type);
            price = itemView.findViewById(R.id.package_price);
        }
    }
}

