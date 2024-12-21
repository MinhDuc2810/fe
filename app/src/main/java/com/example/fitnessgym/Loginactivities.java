package com.example.fitnessgym;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessgym.AdminActivity.MainAdmin;
import com.example.fitnessgym.PtActivity.MainPTActivity;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.User.LoginRequest;
import com.example.fitnessgym.UserActivity.MainActivity;
import com.example.fitnessgym.UserActivity.Registeractivities;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Loginactivities extends AppCompatActivity {
    private EditText edttendangnhap, edtmatkhau;
    private Button btnlogin;
    private TextView btnquenmk, btnsignup;

    private SharedPreferencesManager tokenManager;
    private ApiService apiService;

    private int failedLoginAttempts = 0; // Đếm số lần thất bại
    private long firstAttemptTime = 0;   // Lưu thời điểm lần đầu đăng nhập thất bại
    private boolean isLocked = false;   // Trạng thái khóa tạm thời

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivities);

        // Khởi tạo Retrofit và TokenManager
        RetrofitClient.init(this);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        apiService = retrofit.create(ApiService.class);

        // Ánh xạ View
        edttendangnhap = findViewById(R.id.edttendangnhap);
        edtmatkhau = findViewById(R.id.edtmatkhau);
        btnlogin = findViewById(R.id.btnlogin);
        btnsignup = findViewById(R.id.edtdangkytk);

        // Quên mật khẩu
        btnquenmk = findViewById(R.id.btnquenmk);
        btnquenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Loginactivities.this, Quenmatkhau.class);
                startActivity(intent);
            }
        });

        // Chuyển sang màn hình đăng ký
        btnsignup.setOnClickListener(view -> {
            Intent myIntent = new Intent(Loginactivities.this, Registeractivities.class);
            startActivity(myIntent);
        });

        // Xử lý đăng nhập
        btnlogin.setOnClickListener(v -> {
            if (isLocked) {
                Toast.makeText(Loginactivities.this, "Login temporarily locked. Please wait 30 seconds.", Toast.LENGTH_SHORT).show();
                return;
            }

            String email = edttendangnhap.getText().toString().trim();
            String password = edtmatkhau.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Loginactivities.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gửi yêu cầu đăng nhập
            LoginRequest loginRequest = new LoginRequest(email, password);
            Call<ApiResponse> call = apiService.login(loginRequest);

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse apiResponse = response.body();

                        if (apiResponse.isSuccess()) {
                            // Reset đếm số lần thất bại nếu đăng nhập thành công
                            failedLoginAttempts = 0;
                            firstAttemptTime = 0;

                            // Lưu token và chuyển màn hình
                            SharedPreferencesManager.saveToken(Loginactivities.this, apiResponse.getToken());
                            SharedPreferencesManager.saveUserInfo(Loginactivities.this, apiResponse.getUser());
                            Toast.makeText(Loginactivities.this, "Login successful!", Toast.LENGTH_SHORT).show();

                            if (SharedPreferencesManager.getUserInfo(Loginactivities.this).getRole().equals("Admin")) {
                                Intent myIntent = new Intent(Loginactivities.this, MainAdmin.class);
                                startActivity(myIntent);
                                finish();
                            } else if (SharedPreferencesManager.getUserInfo(Loginactivities.this).getRole().equals("User")) {
                                Intent myIntent1 = new Intent(Loginactivities.this, MainActivity.class);
                                startActivity(myIntent1);
                                finish();
                            } else {
                                Intent myIntent1 = new Intent(Loginactivities.this, MainPTActivity.class);
                                startActivity(myIntent1);
                                finish();
                            }

                        } else {
                            handleFailedLogin(); // Gọi hàm xử lý đăng nhập thất bại
                        }
                    } else {
                        handleFailedLogin(); // Gọi hàm xử lý đăng nhập thất bại
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(Loginactivities.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void handleFailedLogin() {
        long currentTime = System.currentTimeMillis();

        if (firstAttemptTime == 0 || currentTime - firstAttemptTime > 60000) {
            // Reset đếm nếu quá 1 phút kể từ lần đầu thất bại
            firstAttemptTime = currentTime;
            failedLoginAttempts = 0;
        }

        failedLoginAttempts++;

        if (failedLoginAttempts >= 10) {
            isLocked = true;
            Toast.makeText(Loginactivities.this, "Too many failed attempts. Locked for 30 seconds.", Toast.LENGTH_SHORT).show();

            // Sử dụng Handler để mở khóa sau 30 giây
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                isLocked = false;
                failedLoginAttempts = 0; // Reset số lần đăng nhập
                firstAttemptTime = 0;
            }, 30000);
        } else {
            Toast.makeText(Loginactivities.this, "Login failed!", Toast.LENGTH_SHORT).show();
        }
    }
}



