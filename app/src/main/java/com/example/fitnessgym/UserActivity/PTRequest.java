package com.example.fitnessgym.UserActivity;

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

import com.example.fitnessgym.Available_pts;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.User.ClickPtRequestListener;
import com.example.fitnessgym.adapter.PtRequestAdapter;
import com.example.fitnessgym.api.ApiListRequestResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PTRequest extends AppCompatActivity {
    RecyclerView recyclePtRequest;
    PtRequestAdapter ptRequestAdapter;
    List<Available_pts> available_ptsList;
    TextView tvexit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ptrequest);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclePtRequest = findViewById(R.id.recyclePtRequest);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclePtRequest.setLayoutManager(linearLayoutManager);
        fetchPtrequestData();


        tvexit = findViewById(R.id.tvexitdanhsachpt);
        tvexit.setOnClickListener(v -> {
            finish();
        });

    }

    private void fetchPtrequestData() {
    int id = SharedPreferencesManager.getUserInfo(PTRequest.this).getId();
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ApiListRequestResponse> call = apiService.getRequest(id);

        call.enqueue(new Callback<ApiListRequestResponse>() {
            @Override
            public void onResponse(Call<ApiListRequestResponse> call, Response<ApiListRequestResponse> response) {
                if (response.isSuccessful() && response.body().getAvailable_pts() != null) {
                    List<Available_pts> availablePtsList = (List<Available_pts>) response.body().getAvailable_pts();  // Lấy dữ liệu từ phản hồi


                    // Tạo Adapter và gán vào RecyclerView với danh sách đã lọc
                    ClickPtRequestListener clickPtRequestListener = new ClickPtRequestListener() {
                        @Override
                        public void onClickPtRequest(Available_pts available_pts) {

                            // Ví dụ: Mở một Activity để hiển thị chi tiết PT
                            Intent intent = new Intent(PTRequest.this, PTRequestDetail.class);
                            intent.putExtra("ptrequest", available_pts);
                            startActivity(intent);
                        }
                    };
                    ptRequestAdapter = new PtRequestAdapter(availablePtsList, clickPtRequestListener);
                    recyclePtRequest.setAdapter(ptRequestAdapter);

                } else {
                    Toast.makeText(PTRequest.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiListRequestResponse> call, Throwable t) {
                Toast.makeText(PTRequest.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}