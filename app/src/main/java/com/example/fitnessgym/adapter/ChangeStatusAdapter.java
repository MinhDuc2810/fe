package com.example.fitnessgym.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.DateUtils;
import com.example.fitnessgym.PTrequest.Response;
import com.example.fitnessgym.PtActivity.MainPTActivity;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Sessions;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class ChangeStatusAdapter extends RecyclerView.Adapter<ChangeStatusAdapter.SessionViewHolder>  {

    private List<Sessions> sessionsList;
    private final Context context;





    public ChangeStatusAdapter(List<Sessions> sessionsList, Context context) {
        this.sessionsList = sessionsList;

        this.context = context;
    }


    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_changestatus, parent,false);

        return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        Sessions sessions = sessionsList.get(position);
        DateUtils dateUtils = new DateUtils();


        if(sessions == null){
            return;
        }

        holder.changeusername.setText("Tên khách hàng : " + sessions.getUserName());
        holder.changeuserid.setText("Mã khách hàng :" + String.valueOf(sessions.getUser_id()));
        holder.changephoneNumber.setText("Số điện thoại : " +sessions.getPhoneNumber());
        holder.changedate.setText("Ngày : " +sessions.getDate());
        holder.changetime.setText("Thời gian : " + dateUtils.formatDate(sessions.getTime()));

        holder.btnacceptchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int request_id = sessions.getRequest_id();
                acceptChange(request_id);
            }
        });
        holder.btnrejectchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int request_id = sessions.getRequest_id();
                rejectChange(request_id);
            }
        });

    }
    //XỬ LÝ CLICK TỪ CHỐI
    private void rejectChange(int request_id) {
        String action = "rejected";
        int pt_id = SharedPreferencesManager.getUserInfo(context).getId();
        Response response = new Response(request_id, action, pt_id);

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ApiResponse> call = apiService.changestatusrequest(response);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(context, "Từ chối thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainPTActivity.class);
                    context.startActivity(intent);


                }else {
                    Toast.makeText(context, "Từ chối thất bại", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(context, "Lỗi mạng:" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });

    }



    //XỬ LÝ CLICK ĐỒNG Ý

    private void acceptChange(int request_id) {
        String action = "approved";
        int pt_id = SharedPreferencesManager.getUserInfo(context).getId();
        Response response = new Response(request_id, action, pt_id);

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ApiResponse> call = apiService.changestatusrequest(response);

        call.enqueue(new Callback<ApiResponse>() {


            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(context, "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainPTActivity.class);
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "Xac nhận thất bại", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(context, "Lỗi mạng:" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });
    }


    @Override
    public int getItemCount() {
        if(sessionsList != null){
            return sessionsList.size();
        }
        return 0;
    }




    public static class SessionViewHolder extends RecyclerView.ViewHolder{

        private final TextView changeusername, changeuserid,changephoneNumber, changedate, changetime ;
        private final Button btnacceptchange, btnrejectchange;

        public SessionViewHolder(View itemView) {
            super(itemView);

            changeusername = itemView.findViewById(R.id.changeusername);
            changeuserid = itemView.findViewById(R.id.changeuserid);
            changephoneNumber = itemView.findViewById(R.id.changephoneNumber);
            changedate = itemView.findViewById(R.id.changedate);
            changetime = itemView.findViewById(R.id.changetime);
            btnrejectchange = itemView.findViewById(R.id.btnrejectchange);
            btnacceptchange = itemView.findViewById(R.id.btnacceptchange);




        }
    }

}
