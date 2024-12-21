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

import com.example.fitnessgym.Hocvien;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.adapter.HocvienAdapter;
import com.example.fitnessgym.api.ApiGetHocvien;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class DanhsachhocvienActivity extends AppCompatActivity {
    RecyclerView recycledanhsach;
    HocvienAdapter hocvienAdapter;
    TextView tvexitdanhsachhocvien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_danhsachhocvien);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recycledanhsach = findViewById(R.id.recycledanhsach);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycledanhsach.setLayoutManager(linearLayoutManager);
        fetchHocvienData();

        tvexitdanhsachhocvien = findViewById(R.id.tvexitdanhsachhocvien);
        tvexitdanhsachhocvien.setOnClickListener(v -> {
            Intent intent = new Intent(DanhsachhocvienActivity.this, MainPTActivity.class);
            finish();
        });


    }

    private void fetchHocvienData() {
        int id = SharedPreferencesManager.getUserInfo(DanhsachhocvienActivity.this).getId();
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ApiGetHocvien> call = apiService.getHocvien(id);

        call.enqueue(new retrofit2.Callback<ApiGetHocvien>() {
            @Override
            public void onResponse(Call<ApiGetHocvien> call, retrofit2.Response<ApiGetHocvien> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Hocvien> hocvienList = response.body().getHocvien();
                    hocvienAdapter = new HocvienAdapter(hocvienList);
                    recycledanhsach.setAdapter(hocvienAdapter);
                } else {
                    Toast.makeText(DanhsachhocvienActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiGetHocvien> call, Throwable t) {
                Toast.makeText(DanhsachhocvienActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }}
        );
    }

}