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

import com.example.fitnessgym.DateUtils;
import com.example.fitnessgym.Membership.ClickMembershipListener;
import com.example.fitnessgym.Membership.Membership;
import com.example.fitnessgym.R;

import java.util.ArrayList;
import java.util.List;

public class MembershipAdapter extends RecyclerView.Adapter<MembershipAdapter.UserViewHolder> implements Filterable {

    private List<Membership> membershipList;
    private  List<Membership> membershipListOld;
    private  com.example.fitnessgym.Membership.ClickMembershipListener clickMembershipListener;



    public MembershipAdapter(List<Membership> membershipList, ClickMembershipListener clickMembershipListener) {
        this.membershipList = membershipList;
        this.membershipListOld = membershipList;
        this.clickMembershipListener = clickMembershipListener;
    }



    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thetap, parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Membership membership = membershipList.get(position);
        String formattedStartDate = DateUtils.formatDate(membership.getMembershipStart());
        String formattedEndDate = DateUtils.formatDate(membership.getMembershipEnd());// Giả sử lấy thẻ đầu tiên

        if(membership == null){
            return;
        }
        holder.cardid.setText(String.valueOf("ID :" + membership.getId()));
        holder.cardstart.setText("Ngày bắt đầu :" + formattedStartDate);
        holder.cardend.setText("Ngày kết thúc :" + formattedEndDate);
        holder .cardname.setText("Tên :" +membership.getUserName());



        holder.itemView.setOnClickListener(v -> clickMembershipListener.onClickMembership( membership));


    }



    @Override
    public int getItemCount() {
        if(membershipList != null){
            return membershipList.size();
        }
        return 0;
    }




    public static class UserViewHolder extends RecyclerView.ViewHolder{
        private final TextView cardid ,cardstart, cardend, cardname;

        public UserViewHolder(View itemView) {
            super(itemView);
            cardname = itemView.findViewById(R.id.cardname);
            cardid = itemView.findViewById(R.id.cardid);
            cardstart = itemView.findViewById(R.id.cardstart);
            cardend = itemView.findViewById(R.id.cardend);



        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    membershipList = membershipListOld;
                }else {
                    List<Membership> List = new ArrayList<>();
                    for (Membership membership : membershipListOld){
                        if(String.valueOf(membership.getId()).contains(strSearch)){
                            List.add(membership);
                        }
                    }
                    membershipList = List;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = membershipList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                membershipList = (List<Membership>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}