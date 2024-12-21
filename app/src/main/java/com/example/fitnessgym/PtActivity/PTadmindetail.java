package com.example.fitnessgym.PtActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessgym.AdminActivity.MainAdmin;
import com.example.fitnessgym.AdminActivity.PackageAdmin;
import com.example.fitnessgym.AdminActivity.PackageAdminDetail;
import com.example.fitnessgym.R;
import com.example.fitnessgym.User.Ptinfo;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PTadmindetail extends AppCompatActivity {
TextView detailptadminname, detailptadminid, detailptadminemail, detailptadminphonenumber;
Button btnxoapt, btnexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ptadmindetail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        detailptadminemail = findViewById(R.id.detailptadminemail);
        detailptadminid = findViewById(R.id.detailptadminid);
        detailptadminname = findViewById(R.id.detailptadminname);
        detailptadminphonenumber = findViewById(R.id.detailptadminphonenumber);
        btnxoapt = findViewById(R.id.btnxoapt);

        Ptinfo ptinfo  = (Ptinfo) getIntent().getSerializableExtra("ptinfo");

        if (ptinfo != null) {
            // Sử dụng dữ liệu packageItem để hiển thị thông tin trong Activity
            // Ví dụ:
            TextView detailptadminname = findViewById(R.id.detailptadminname);
            detailptadminname.setText("Tên PT : " + ptinfo.getUserName());

            TextView detailptadminid = findViewById(R.id.detailptadminid);
            detailptadminid.setText("ID : " + String.valueOf(ptinfo.getId()) );

            TextView detailptadminphonenumber = findViewById(R.id.detailptadminphonenumber);
            detailptadminphonenumber.setText("Số điện thoại : " + ptinfo.getPhoneNumber());

            TextView detailptadminemail = findViewById(R.id.detailptadminemail);
            detailptadminemail.setText("Email : " + ptinfo.getEmail());


            btnxoapt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = ptinfo.getId();

                    deletePT(id);
                }
            });


        }

        btnexit = findViewById(R.id.btnexitptdetail);
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PTadmindetail.this, MainAdmin.class);
                finish();
            }
        });
    }

    private void deletePT(int id) {
        // Khởi tạo Retrofit instance
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        // Gọi API DELETE
        Call<ApiResponse> call = apiService.deletePT(id);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Nếu xoá thành công
                    ApiResponse apiResponse = response.body();
                    if ("success".equals(apiResponse.getStatus())) {
                        Toast.makeText(PTadmindetail.this, "Xoá PT thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PTadmindetail.this, MainAdmin.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(PTadmindetail.this, "Không tìm thấy PT", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Nếu có lỗi trong quá trình gọi API
                    Toast.makeText(PTadmindetail.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Nếu xảy ra lỗi khi kết nối mạng
                Toast.makeText(PTadmindetail.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}