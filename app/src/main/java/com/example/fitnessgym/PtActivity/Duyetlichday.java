package com.example.fitnessgym.PtActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.R;
import com.example.fitnessgym.Sessions;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.adapter.ChangeStatusAdapter;
import com.example.fitnessgym.adapter.SessionsAdapter;
import com.example.fitnessgym.api.ApiGetSlotforPT;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Duyetlichday extends AppCompatActivity {
    RecyclerView recycleDangky;
    ChangeStatusAdapter changeStatusAdapter;
    TextView tvexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_duyetlichday);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recycleDangky = findViewById(R.id.recycleDangky);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleDangky.setLayoutManager(linearLayoutManager);
        fetchDangkyData();

        tvexit = findViewById(R.id.tvexitdanhsachdangky);
        tvexit.setOnClickListener(v -> {
            Intent intent = new Intent(Duyetlichday.this, MainPTActivity.class);
            finish();
        });
    }

    private void fetchDangkyData() {
        int pt_id = SharedPreferencesManager.getUserInfo(Duyetlichday.this).getId();
        String status = "pending";
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ApiGetSlotforPT> call = apiService.getRequest(pt_id, status);

        call.enqueue(new Callback<ApiGetSlotforPT>() {
            @Override
            public void onResponse(Call<ApiGetSlotforPT> call, Response<ApiGetSlotforPT> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getSessions() != null) {
                    // Lấy danh sách từ API
                    List<Sessions> availablePtsList = response.body().getSessions();

                    // Tạo Adapter và gán vào RecyclerView
                    changeStatusAdapter = new ChangeStatusAdapter(availablePtsList ,Duyetlichday.this );
                    recycleDangky.setAdapter(changeStatusAdapter);

                } else {
                    Toast.makeText(Duyetlichday.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiGetSlotforPT> call, Throwable t) {
                Toast.makeText(Duyetlichday.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}