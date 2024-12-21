package com.example.fitnessgym.UserActivity;

import android.os.Bundle;
import android.util.Log;
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

import com.example.fitnessgym.AdminActivity.MainAdmin;
import com.example.fitnessgym.PaymentHistory;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.User.Userinfo;
import com.example.fitnessgym.adapter.PaymentHistoryAdapter;
import com.example.fitnessgym.adapter.UserAdapter;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PaymentHistoryActivity extends AppCompatActivity {
    RecyclerView recycleHistory;
    SearchView searchHistory;
    PaymentHistoryAdapter paymentHistoryAdapter;
    TextView tvexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_history);


        recycleHistory = findViewById(R.id.recycleHistory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleHistory.setLayoutManager(linearLayoutManager);
        int id = SharedPreferencesManager.getUserInfo(PaymentHistoryActivity.this).getId();
        fetchHistoryData(id);  // Truyến thêm tham số userId với phương thức fetchHistoryData() khóint id);

        searchHistory = findViewById(R.id.searchHistory);
        searchHistory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                paymentHistoryAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                paymentHistoryAdapter.getFilter().filter(newText);
                return false;
            }
        });

        tvexit = findViewById(R.id.tvexitlichsugiaodich);
        tvexit.setOnClickListener(v -> {
            finish();
        });
    }

    private void fetchHistoryData(int id) {
        // Tạo đối tượng ApiService từ RetrofitClient
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Gọi API với @Query("id")
        Call<List<PaymentHistory>> call = apiService.getHistory(id);

        // Thực hiện enqueue cho lời gọi API
        call.enqueue(new Callback<List<PaymentHistory>>() {
            @Override
            public void onResponse(Call<List<PaymentHistory>> call, Response<List<PaymentHistory>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy danh sách lịch sử thanh toán từ phản hồi API
                    List<PaymentHistory> paymentHistoryList = response.body();

                    // Cập nhật adapter cho RecyclerView
                    paymentHistoryAdapter = new PaymentHistoryAdapter(paymentHistoryList);
                    recycleHistory.setAdapter(paymentHistoryAdapter);
                } else {
                    // Thông báo khi không có dữ liệu trả về
                    Toast.makeText(PaymentHistoryActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PaymentHistory>> call, Throwable t) {
                // Log lỗi và thông báo người dùng khi có lỗi xảy ra
                Log.e("API Error", t.getMessage());
                Toast.makeText(PaymentHistoryActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}