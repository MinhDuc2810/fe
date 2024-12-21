package com.example.fitnessgym.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessgym.Giaodich;
import com.example.fitnessgym.R;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GiaodichDetail extends AppCompatActivity {
    private Button btnconfirm, btnexit;
    private TextView detailid,detailpackagename, detailpackagetype, detailpackageduration, detailpackageprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_giaodich_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Giaodich giaodichItem = (Giaodich) getIntent().getSerializableExtra("giaodich");

        detailid = findViewById(R.id.detailid);
        detailpackagetype = findViewById(R.id.detailpackagetype);
        detailpackageprice = findViewById(R.id.detailpackageprice);

        if( giaodichItem != null) {
            detailid.setText("Mã giao dịch : " + String.valueOf(giaodichItem.getId()));
            detailpackagetype.setText("Loại gói tập : " + giaodichItem.getType());
            detailpackageprice.setText("Giá : " + String.valueOf(giaodichItem.getPrice()) + "đ");


            btnconfirm  = findViewById(R.id.btnconfirm);
            btnconfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = giaodichItem.getId();

                    updateTransaction(id);
                }
            });


        }

        btnexit = findViewById(R.id.btnexitgiaodichdetail);
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiaodichDetail.this, Giaodich.class);
                finish();
            }
        });

    }

    private void updateTransaction(int id) {
        // Khởi tạo Retrofit instance
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        // Gọi API DELETE
        Call<ApiResponse> call = apiService.updateTransaction(id);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Nếu xoá thành công
                    ApiResponse apiResponse = response.body();
                    if ("success".equals(apiResponse.getStatus())) {
                        Toast.makeText(GiaodichDetail.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(GiaodichDetail.this, MainAdmin.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(GiaodichDetail.this, "Không tìm thấy giao dich với ID này", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Nếu có lỗi trong quá trình gọi API
                    Toast.makeText(GiaodichDetail.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Nếu xảy ra lỗi khi kết nối mạng
                Toast.makeText(GiaodichDetail.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}