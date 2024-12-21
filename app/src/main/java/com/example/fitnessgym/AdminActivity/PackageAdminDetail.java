package com.example.fitnessgym.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessgym.Package.Package;
import com.example.fitnessgym.Package.PackageDetail;
import com.example.fitnessgym.Package.PackageUpdate;
import com.example.fitnessgym.R;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PackageAdminDetail extends AppCompatActivity {
    Button btndeletepk1, btnupdatepk1;
    EditText edtpkname1, edtpktype1, edtpkduration1, edtpkprice1;
    RadioGroup radioGroup1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_admin_detail);
        radioGroup1 = findViewById(R.id.radioGroup1);
        btndeletepk1 = findViewById(R.id.btndeletepk1);
        edtpkname1 = findViewById(R.id.edtpkname1);
        edtpkduration1 = findViewById(R.id.edtpkduration1);
        edtpkprice1 = findViewById(R.id.edtpkprice1);
        btnupdatepk1 = findViewById(R.id.btnupdatepk1);

        // Nhận dữ liệu package đã truyền từ MainActivity
        PackageAdmin packageAdminItem = (PackageAdmin) getIntent().getSerializableExtra("adminpackage_details");


        if (packageAdminItem != null) {
            // Sử dụng dữ liệu packageItem để hiển thị thông tin trong Activity
            // Ví dụ:

            String type;

            if(packageAdminItem.getAdtype().equals("Monthly")) {
                type = "Tháng";
            } else {
                type = "Năm";
            }
           edtpkname1.setText(packageAdminItem.getAdname());
            edtpkduration1.setText(String.valueOf(packageAdminItem.getAdduration()));
            edtpkprice1.setText(String.valueOf(packageAdminItem.getAdprice()));



            btnupdatepk1.setOnClickListener(v -> updatePackage(packageAdminItem));


            btndeletepk1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int packageId = packageAdminItem.getAdid();

                    deletePackage(packageId);
                }
            });





        }
    }

    private void updatePackage(PackageAdmin packageAdminItem) {
        // Lấy dữ liệu từ các trường
        int selectedId = radioGroup1.getCheckedRadioButtonId(); // Lấy ID của radio button được chọn

        // Kiểm tra nếu chưa chọn radio button
        if (selectedId == -1) {
            Toast.makeText(this, "Vui lòng chọn loại gói tập!", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadioButton = findViewById(selectedId);
        String name = edtpkname1.getText().toString().trim();
        String type = selectedRadioButton.getText().toString();
        String price = edtpkprice1.getText().toString().trim();
        String durationStr = edtpkduration1.getText().toString().trim();

        // Kiểm tra dữ liệu
        if (name.isEmpty() || type.isEmpty() || price.isEmpty() || durationStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        int id;
        int duration;
        try {
            id = packageAdminItem.getAdid();
            duration = Integer.parseInt(durationStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "ID và Thời hạn phải là số!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng PackageUpdate
        PackageUpdate packageUpdate = new PackageUpdate(name, type, price, duration);

        // Gửi yêu cầu API
        sendUpdateRequest(id, packageUpdate);
    }



    //Xử lý xoa goi tap
    private void deletePackage(int id) {
        // Khởi tạo Retrofit instance
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        // Gọi API DELETE
        Call<ApiResponse> call = apiService.deletePackage(id);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Nếu xoá thành công
                    ApiResponse apiResponse = response.body();
                    if ("success".equals(apiResponse.getStatus())) {
                        Toast.makeText(PackageAdminDetail.this, "Xoá gói tập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PackageAdminDetail.this, MainAdmin.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(PackageAdminDetail.this, "Không tìm thấy gói tập với ID này", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Nếu có lỗi trong quá trình gọi API
                    Toast.makeText(PackageAdminDetail.this, "Lỗi khi gọi API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Nếu xảy ra lỗi khi kết nối mạng
                Toast.makeText(PackageAdminDetail.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendUpdateRequest(int id, PackageUpdate packageUpdate) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse> call = apiService.updatePackage(id, packageUpdate);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(PackageAdminDetail.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PackageAdminDetail.this, MainAdmin.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(PackageAdminDetail.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(PackageAdminDetail.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}