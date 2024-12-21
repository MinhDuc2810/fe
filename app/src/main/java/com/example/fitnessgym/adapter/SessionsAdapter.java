package com.example.fitnessgym.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.DateUtils;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Sessions;
import java.util.List;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.SessionViewHolder>  {

    private   List<Sessions> sessionsList;





    public SessionsAdapter(List<Sessions> sessionsList) {
        this.sessionsList = sessionsList;

    }


    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sessions, parent,false);

        return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        Sessions sessions = sessionsList.get(position);
        DateUtils dateUtils = new DateUtils();

        if(sessions == null){
            return;
        }

        holder.ssusername.setText("Tên khách hàng : " + sessions.getUserName());
        holder.ssuserid.setText("Mã khách hàng :" + String.valueOf(sessions.getUser_id()));
        holder.ssphoneNumber.setText("Số điện thoại : " +sessions.getPhoneNumber());
        holder.ssdate.setText("Ngày : " +sessions.getDate());
        holder.sstime.setText("Thời gian : " + dateUtils.formatDate(sessions.getTime()));

    }



    @Override
    public int getItemCount() {
        if(sessionsList != null){
            return sessionsList.size();
        }
        return 0;
    }




    public static class SessionViewHolder extends RecyclerView.ViewHolder{

        private final TextView ssusername, ssuserid,ssphoneNumber, ssdate, sstime ;

        public SessionViewHolder(View itemView) {
            super(itemView);

            ssusername = itemView.findViewById(R.id.ssusername);
            ssuserid = itemView.findViewById(R.id.ssuserid);
            ssphoneNumber = itemView.findViewById(R.id.ssphoneNumber);
            ssdate = itemView.findViewById(R.id.ssdate);
            sstime = itemView.findViewById(R.id.sstime);




        }
    }

}
