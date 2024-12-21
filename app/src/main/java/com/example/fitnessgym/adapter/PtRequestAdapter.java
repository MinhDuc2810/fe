package com.example.fitnessgym.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fitnessgym.Available_pts;
import com.example.fitnessgym.R;
import com.example.fitnessgym.User.ClickPtRequestListener;

import java.util.List;

public class PtRequestAdapter extends RecyclerView.Adapter<PtRequestAdapter.PackageViewHolder> {

    private final List<Available_pts> available_ptsList;
    private final ClickPtRequestListener clickPtRequestListener;


    // Constructor
    public PtRequestAdapter(List<Available_pts> available_ptsList, ClickPtRequestListener clickPtRequestListener) {
        this.available_ptsList = available_ptsList;
        this.clickPtRequestListener = clickPtRequestListener;
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ptrequest, parent, false);
        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {
        Available_pts  ptRequest = available_ptsList.get(position);
        if (ptRequest == null) {
            return;
        }else  {
            holder.nameptrequest.setText("Tên : " + ptRequest.getPtName());
            holder.emailptrequest.setText("Email : " + ptRequest.getEmail());
            holder.phonenumberptrequest.setText("Sđt : " + ptRequest.getPhoneNumber());
            holder.sessionLeft.setText("Số buổi còn lại : " + String.valueOf(ptRequest.getSessionsLeft()));

            // Thiết lập sự kiện click cho mỗi item
            holder.itemView.setOnClickListener(v -> clickPtRequestListener.onClickPtRequest(ptRequest));
        }



    }

    @Override
    public int getItemCount() {
        return available_ptsList != null ? available_ptsList.size() : 0;
    }

    // ViewHolder class
    public static class PackageViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameptrequest, emailptrequest ,phonenumberptrequest, sessionLeft;

        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);

            nameptrequest = itemView.findViewById(R.id.nameptrequest);
            emailptrequest = itemView.findViewById(R.id.emailptrequest);
            phonenumberptrequest = itemView.findViewById(R.id.phonenumberptrequest);
            sessionLeft = itemView.findViewById(R.id.sessionLeft);

        }
    }
}

