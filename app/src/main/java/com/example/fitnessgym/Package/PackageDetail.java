package com.example.fitnessgym.Package;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessgym.AdminActivity.MainAdmin;
import com.example.fitnessgym.AdminActivity.PackageAdmin;
import com.example.fitnessgym.AdminActivity.PackageAdminAdapter;
import com.example.fitnessgym.AdminActivity.PackageAdminDetail;
import com.example.fitnessgym.PaymentRequest;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.User.Userinfo;
import com.example.fitnessgym.UserActivity.MainActivity;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PackageDetail extends AppCompatActivity {
    Button btnthanhtoan, btnexit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_detail);


        btnthanhtoan = findViewById(R.id.btnthanhtoan);

        // Nhận dữ liệu package đã truyền từ MainActivity
        Package packageItem = (Package) getIntent().getSerializableExtra("package_details");



        if (packageItem != null) {
            // Sử dụng dữ liệu packageItem để hiển thị thông tin trong Activity
            // Ví dụ:
            String type;
            if(packageItem.getType().equals("Monthly")) {
                type = "Tháng";
            } else {
                type = "Năm";
            }
            TextView detailname = findViewById(R.id.detailname);
            detailname.setText("Tên gói tập : "+packageItem.getName());

            TextView detailduration = findViewById(R.id.detailduration);
            detailduration.setText("Thời hạn : "+String.valueOf(packageItem.getDuration())+"  "+ type);

            TextView detailprice = findViewById(R.id.detailprice);
            detailprice.setText("Giá : "+packageItem.getPrice()+"đ");



            btnthanhtoan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paymentrequest();
                }
            });

        }

        btnexit = findViewById(R.id.btnexitpackagedetail);
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PackageDetail.this, MainActivity.class);
                finish();
            }
        });
    }

    private void paymentrequest() {
        Package packageItem = (Package) getIntent().getSerializableExtra("package_details");
        int user_id = SharedPreferencesManager.getUserInfo(this).getId();
        int package_id = packageItem.getId();
        String payment_method = "Cash";

        // Tạo đối tượng PaymentRequest
        PaymentRequest request = new PaymentRequest(user_id, package_id, payment_method);

        // Gửi request
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse> call = apiService.paymentrequest(request);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(PackageDetail.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PackageDetail.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(PackageDetail.this, "Failed to process payment.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(PackageDetail.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}




