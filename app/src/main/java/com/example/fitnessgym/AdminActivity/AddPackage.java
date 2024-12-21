package com.example.fitnessgym.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessgym.Package.aPackage;
import com.example.fitnessgym.R;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPackage extends AppCompatActivity {

    private EditText pkName, pkType, pkDuration, pkPrice;
    private Button btnAdd, btnBack;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_package);

        // Ánh xạ view
        pkName = findViewById(R.id.pkname);
        pkDuration = findViewById(R.id.pkduration);
        pkPrice = findViewById(R.id.pkprice);
        btnAdd = findViewById(R.id.btntaddgoitap);
        radioGroup = findViewById(R.id.radioGroup1);

        btnBack = findViewById(R.id.btnexitthemgoitap);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPackage.this, MainAdmin.class);
                finish();
            }
        });


        // Xử lý khi nhấn nút "Thêm"
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkoutPackage();
            }
        });


    }

    private void addWorkoutPackage() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedId);
        // Lấy dữ liệu từ các trường nhập liệu
        String name = pkName.getText().toString().trim();
        String type = selectedRadioButton.getText().toString();
        String durationStr = pkDuration.getText().toString().trim();
        String price = pkPrice.getText().toString().trim();

        // Kiểm tra đầu vào
        if (name.isEmpty() || type.isEmpty() || durationStr.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        int duration;
        try {
            duration = Integer.parseInt(durationStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Thời hạn phải là số nguyên!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng WorkoutPackage
        aPackage aPackage = new aPackage(name , duration, type, price);

        // Gọi API qua Retrofit
        ApiService api = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse> call = api.addWorkoutPackage(aPackage);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if ("success".equalsIgnoreCase(apiResponse.getStatus())) {
                        Toast.makeText(AddPackage.this, "Thêm gói tập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddPackage.this, MainAdmin.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AddPackage.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddPackage.this, "Thêm gói tập thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(AddPackage.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

