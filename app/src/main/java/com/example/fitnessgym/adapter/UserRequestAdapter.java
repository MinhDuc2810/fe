package com.example.fitnessgym.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fitnessgym.PtActivity.ClickRequestListener;
import com.example.fitnessgym.PtActivity.UserRequest;
import com.example.fitnessgym.R;

import java.util.List;

public class UserRequestAdapter extends RecyclerView.Adapter<UserRequestAdapter.PackageViewHolder> {

    private final List<UserRequest> userRequestList;
    private final ClickRequestListener clickRequestListener;


    // Constructor
    public UserRequestAdapter(List<UserRequest> userRequestList, ClickRequestListener clickRequestListener) {
        this.userRequestList = userRequestList;
        this.clickRequestListener = clickRequestListener;
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {
        UserRequest  userRequest = userRequestList.get(position);
        if (userRequest == null) {
            return;
        }


        // Thiết lập sự kiện click cho mỗi item
        holder.itemView.setOnClickListener(v -> clickRequestListener.onClickRequest(userRequest));
    }

    @Override
    public int getItemCount() {
        return userRequestList != null ? userRequestList.size() : 0;
    }

    // ViewHolder class
    public static class PackageViewHolder extends RecyclerView.ViewHolder {
//        private final TextView ;

        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
