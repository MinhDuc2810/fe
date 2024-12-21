package com.example.fitnessgym;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Quenmatkhau extends AppCompatActivity {
    EditText edtsendotp;
    Button btnsendmail, btnexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quenmatkhau);

        // Khởi tạo RetrofitClient
        RetrofitClient.init(getApplicationContext());
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        edtsendotp = findViewById(R.id.edtsendotp);
        btnsendmail = findViewById(R.id.btnsendmail);

        btnexit = findViewById(R.id.btnexitquenmatkhau);
        btnexit.setOnClickListener(v -> finish());

        btnsendmail.setOnClickListener(v -> {
            String email = edtsendotp.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo đối tượng Email
            Email emailData = new Email(email);

            // Gửi request thông qua Retrofit
            apiService.sendEmail(emailData).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse apiResponse = response.body();

                        if (apiResponse.isSuccess()) {
                            // Hiển thị thông báo thành công
                            Toast.makeText(Quenmatkhau.this, "Email sent successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Hiển thị lỗi từ API
                            Toast.makeText(Quenmatkhau.this, "Error: " + apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Xử lý khi phản hồi không thành công (như 404, 500, v.v.)
                        Toast.makeText(Quenmatkhau.this, "Error: Unable to process your request. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(Quenmatkhau.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}