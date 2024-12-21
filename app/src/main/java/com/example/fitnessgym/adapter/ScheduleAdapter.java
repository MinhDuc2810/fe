package com.example.fitnessgym.adapter;

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
import com.example.fitnessgym.R;
import com.example.fitnessgym.Schedule;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.UserActivity.MainActivity;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.PackageViewHolder> {

    private final List<Schedule> schedules;
    private final Context context;



    // Constructor
    public ScheduleAdapter(List<Schedule> schedules, Context context) {
        this.context = context;
        this.schedules = schedules;

    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_schedule, parent, false);
        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {
        Schedule schedule = schedules.get(position);
        if (schedule == null) {
            return;
        }

        holder.scheduleptname.setText("Tên PT : " + schedule.getPt_name());
        holder.scheduleptphoneNumber.setText("Sđt PT : " + schedule.getPt_phone());
        holder.scheduleptdate.setText("Ngày : " + DateUtils.formatDate(schedule.getDate()));
        holder.schedulepttime.setText("Thời gian : " + schedule.getTime());

        holder.btnhuylichtap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Xử lý sự kiện khi người dùng nhấn nút "Hủy lịch tập"
                cancelSchedule(schedule.getRequest_id(), schedule.getPt_id());
            }
        });

    }

    private void cancelSchedule(int requestId, int ptId) {
        String action = "rejected";

        Response response = new Response(requestId, action, ptId);

        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ApiResponse> call = apiService.changestatusrequest(response);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(context, "Huy lich tap thanh cong", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);

                }else {
                    Toast.makeText(context, "Huy lich tap that bai", Toast.LENGTH_SHORT).show();
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
        return schedules != null ? schedules.size() : 0;
    }

    // ViewHolder class
    public static class PackageViewHolder extends RecyclerView.ViewHolder {
        private final TextView scheduleptname, scheduleptphoneNumber, scheduleptdate, schedulepttime;
        private final Button btnhuylichtap;

        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);

            scheduleptname = itemView.findViewById(R.id.scheduleptname);
            scheduleptphoneNumber = itemView.findViewById(R.id.scheduleptphoneNumber);
            scheduleptdate = itemView.findViewById(R.id.scheduleptdate);
            schedulepttime = itemView.findViewById(R.id.schedulepttime);
            btnhuylichtap = itemView.findViewById(R.id.btnhuylichtap);
        }
    }
}
