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
import com.example.fitnessgym.R;
import com.example.fitnessgym.User.Userinfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> implements Filterable {

    private   List<Userinfo> userinfoList;
    private  List<Userinfo> userinfoListOld;



    public UserAdapter(List<Userinfo> userinfoList) {
        this.userinfoList = userinfoList;
        this.userinfoListOld = userinfoList;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thongtinkhachhang, parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Userinfo userinfo = userinfoList.get(position);

        if(userinfo == null){
            return;
        }
        holder.tvUserID.setText(String.valueOf("ID :" + userinfo.getId()));
        holder.tvName.setText(userinfo.getUserName());
        holder.tvEmail.setText("Email :" + userinfo.getEmail());
        holder.tvPhonenumber.setText("Số điện thoại :" + userinfo.getPhoneNumber());



        Picasso.get()
                .load(userinfo.getAvatar())  // URL của hình ảnh
                .into(holder.imgUser);
    }



    @Override
    public int getItemCount() {
        if(userinfoList != null){
            return userinfoList.size();
        }
        return 0;
    }




    public static class UserViewHolder extends RecyclerView.ViewHolder{
       private final ImageView imgUser;
        private final TextView tvUserID ,tvName, tvEmail, tvPhonenumber;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvUserID = itemView.findViewById(R.id.tv_userid);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvPhonenumber = itemView.findViewById(R.id.tv_phonenumber);
            imgUser = itemView.findViewById(R.id.img_user);


        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    userinfoList = userinfoListOld;
                }else {
                    List<Userinfo> List = new ArrayList<>();
                    for (Userinfo userinfo : userinfoListOld){
                        if(String.valueOf(userinfo.getUserName()).contains(strSearch)){
                            List.add(userinfo);
                        }
                    }
                    userinfoList = List;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = userinfoList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            userinfoList = (List<Userinfo>) filterResults.values;
            notifyDataSetChanged();
            }
        };
    }

}
