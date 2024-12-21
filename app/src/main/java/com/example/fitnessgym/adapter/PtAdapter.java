package com.example.fitnessgym.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.fitnessgym.PtActivity.ClickPTListener;
import com.example.fitnessgym.R;
import com.example.fitnessgym.User.Ptinfo;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class PtAdapter extends RecyclerView.Adapter<PtAdapter.UserViewHolder> implements Filterable {

    private   List<Ptinfo> ptinfoList;
    private  List<Ptinfo> ptinfoListOld;
    private  com.example.fitnessgym.PtActivity.ClickPTListener clickPTListener;



    public PtAdapter(List<Ptinfo> ptinfoList, ClickPTListener clickPTListener) {
        this.ptinfoList = ptinfoList;
        this.ptinfoListOld = ptinfoList;
        this.clickPTListener = clickPTListener;
    }



    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongtinpt, parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Ptinfo ptinfo = ptinfoList.get(position);

        if(ptinfo == null){
            return;
        }
        holder.tvUserID.setText(String.valueOf("ID :" + ptinfo.getId()));
        holder.tvName.setText(ptinfo.getUserName());
        holder.tvEmail.setText("Email :" + ptinfo.getEmail());
        holder.tvPhonenumber.setText("Số điện thoại :" + ptinfo.getPhoneNumber());



        Picasso.get()
                .load(ptinfo.getAvatar())  // URL của hình ảnh
                .into(holder.imgUser);


       holder.itemView.setOnClickListener(v -> clickPTListener.onClickPT( ptinfo));


    }



    @Override
    public int getItemCount() {
        if(ptinfoList != null){
            return ptinfoList.size();
        }
        return 0;
    }




    public static class UserViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imgUser;
        private final TextView tvUserID ,tvName, tvEmail, tvPhonenumber;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvUserID = itemView.findViewById(R.id.tv_ptid);
            tvName = itemView.findViewById(R.id.tv_ptname);
            tvEmail = itemView.findViewById(R.id.tv_ptemail);
            tvPhonenumber = itemView.findViewById(R.id.tv_ptphonenumber);
            imgUser = itemView.findViewById(R.id.img_pt);


        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    ptinfoList = ptinfoListOld;
                }else {
                    List<Ptinfo> List = new ArrayList<>();
                    for (Ptinfo ptinfo : ptinfoListOld){
                        if(String.valueOf(ptinfo.getUserName()).contains(strSearch)){
                            List.add(ptinfo);
                        }
                    }
                    ptinfoList = List;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = ptinfoList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                ptinfoList = (List<Ptinfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}