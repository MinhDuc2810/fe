package com.example.fitnessgym.AdminActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fitnessgym.Package.ClickAdminItemPackageListener;
import com.example.fitnessgym.R;
import java.util.List;

public class PackageAdminAdapter extends RecyclerView.Adapter<PackageAdminAdapter.PackageAdminViewHolder>  {
    private final List<PackageAdmin> adminpackageList;
    private final ClickAdminItemPackageListener clickAdminItemPackageListener;


    // Constructor
    public PackageAdminAdapter(List<PackageAdmin> adminpackageList, ClickAdminItemPackageListener clickAdminItemPackageListener) {
        this.adminpackageList = adminpackageList;
        this.clickAdminItemPackageListener = clickAdminItemPackageListener;
    }

    @NonNull
    @Override
    public PackageAdminAdapter.PackageAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaithetap, parent, false);
        return new PackageAdminAdapter.PackageAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageAdminAdapter.PackageAdminViewHolder holder, int position) {
        PackageAdmin packageAdmin = adminpackageList.get(position);
        if (packageAdmin == null) {
            return;
        }

        String type;

        if(packageAdmin.getAdtype().equals("Monthly")) {
            type = "Tháng";
        } else {
            type = "Năm";
        }
        holder.adid.setText("ID : "+String.valueOf(packageAdmin.getAdid()));
        holder.adname.setText(packageAdmin.getAdname());
        holder.adduration.setText("Thời gian : " + String.valueOf(packageAdmin.getAdduration()) + " " + type);
        holder.adtype.setText("Loại gói tập : " + packageAdmin.getAdtype());
        holder.adprice.setText("Giá : " + packageAdmin.getAdprice() + "đ");

        // Thiết lập sự kiện click cho mỗi item
        holder.itemView.setOnClickListener(v -> clickAdminItemPackageListener.onClickAdminItemPackage(packageAdmin));
    }

    @Override
    public int getItemCount() {
        return adminpackageList != null ? adminpackageList.size() : 0;
    }

    // ViewHolder class
    public static class PackageAdminViewHolder extends RecyclerView.ViewHolder {
        private final TextView adname, adduration, adtype, adprice, adid;

        public PackageAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            adid = itemView.findViewById(R.id.package_id);
            adname = itemView.findViewById(R.id.package_name);
            adduration = itemView.findViewById(R.id.package_duration);
            adtype = itemView.findViewById(R.id.package_type);
            adprice = itemView.findViewById(R.id.package_price);
        }
    }


}
