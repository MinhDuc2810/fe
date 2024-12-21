package com.example.fitnessgym.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.Giaodich;
import com.example.fitnessgym.R;
import com.example.fitnessgym.adapter.GiaodichAdapter;
import com.example.fitnessgym.adapter.MembershipAdapter;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Xacnhangiaodich extends AppCompatActivity {
    private RecyclerView recyclegiaodich;
    private SearchView searchgiaodich;
    private TextView tvgiaodich;
    private GiaodichAdapter giaodichAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_xacnhangiaodich);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvgiaodich = findViewById(R.id.tvexitgiaodich);
        tvgiaodich.setOnClickListener(v -> {
            Intent intent = new Intent(Xacnhangiaodich.this, MainAdmin.class);
            finish();
        });




        recyclegiaodich = findViewById(R.id.recyclegiaodich);
        LinearLayoutManager linearLayoutManagergiaodich = new LinearLayoutManager(this);
        recyclegiaodich.setLayoutManager(linearLayoutManagergiaodich);  // Sửa lại ở đây
        fetchgiaodichData();


        searchgiaodich = findViewById(R.id.searchgiaodich);
        searchgiaodich.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                giaodichAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                giaodichAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    private void fetchgiaodichData() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Giaodich>> call = apiService.getGiaodich();

        call.enqueue(new Callback<List<Giaodich>>() {
            @Override
            public void onResponse(Call<List<Giaodich>> call, Response<List<Giaodich>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<Giaodich> giaodichList = response.body();

                    // Tạo Adapter và gán vào RecyclerView với toàn bộ danh sách
                    ClickGiaodichListener clickGiaodichListener = new ClickGiaodichListener() {
                        @Override
                        public void onClickGiaodich(Giaodich giaodich) {
                            Intent intent = new Intent(Xacnhangiaodich.this, GiaodichDetail.class);
                            intent.putExtra("giaodich", giaodich);
                            startActivity(intent);

                        }

                    };

                    // Gán dữ liệu vào Adapter và RecyclerView
                    giaodichAdapter = new GiaodichAdapter(giaodichList, clickGiaodichListener);
                    recyclegiaodich.setAdapter(giaodichAdapter);

                } else {
                    Toast.makeText(Xacnhangiaodich.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Giaodich>> call, Throwable t) {
                Toast.makeText(Xacnhangiaodich.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}