package com.example.fitnessgym;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.User.Userinfo;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Doimatkhau extends AppCompatActivity {

    EditText edtcurrentPass, edtnewPass;
    Button btnsubmit, btnexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doimatkhau);
        RetrofitClient.init(this);

        Userinfo user = SharedPreferencesManager.getUserInfo(this);

//        InputFilter filter = new InputFilter() {
//            @Override
//            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                // Biểu thức chính quy: chỉ cho phép số, chữ cái, và @
//                if (source != null && !source.toString().matches("^[a-zA-Z0-9@.#!%&]*$")) {
//                    return ""; // Loại bỏ ký tự không hợp lệ
//                }
//                return null; // Cho phép ký tự hợp lệ
//            }
//        };
//
//        edtcurrentPass.setFilters(new InputFilter[]{filter});
//        edtnewPass.setFilters(new InputFilter[]{filter});

        edtcurrentPass = findViewById(R.id.edtcurrentPass);
        edtnewPass = findViewById(R.id.edtnewPass);

        btnsubmit = findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(v -> passwordUpdate(user));

        btnexit = findViewById(R.id.btnexitdoimatkhau);
        btnexit.setOnClickListener(v -> finish());

    }

    private void passwordUpdate(Userinfo user) {
        // Lấy dữ liệu từ các trường
        String currentPassword = edtcurrentPass.getText().toString().trim();
        String newPassword = edtnewPass.getText().toString().trim();



        // Kiểm tra dữ liệu
        if (currentPassword.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = SharedPreferencesManager.getUserInfo(Doimatkhau.this).getEmail();

        // Tạo đối tượng Userupdate
        Password passwordupdate = new Password(currentPassword, newPassword);

        // Gửi yêu cầu API
        Passwordrequest(email, passwordupdate);


    }

    private void Passwordrequest(String email, Password passwordupdate) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse> call = apiService.Passwordupdatecall(email, passwordupdate);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(Doimatkhau.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Doimatkhau.this, Loginactivities.class);
                    startActivity(intent);
                    finish();


                } else {
                    Toast.makeText(Doimatkhau.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(Doimatkhau.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}