package com.example.fitnessgym;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessgym.AdminActivity.MainAdmin;
import com.example.fitnessgym.AdminActivity.PackageAdmin;
import com.example.fitnessgym.AdminActivity.PackageAdminDetail;
import com.example.fitnessgym.Package.PackageUpdate;
import com.example.fitnessgym.PtActivity.MainPTActivity;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.User.Userinfo;
import com.example.fitnessgym.User.Userupdate;
import com.example.fitnessgym.UserActivity.MainActivity;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Taikhoan extends AppCompatActivity {
    Button btneditinfo, btnexit;
    EditText tkmail, tkphone, tkname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_taikhoan);
        RetrofitClient.init(this);

        Userinfo user = SharedPreferencesManager.getUserInfo(this);



        tkname = findViewById(R.id.tkname);
        tkmail = findViewById(R.id.tkmail);
        tkphone = findViewById(R.id.tkphone);

        tkname.setText(user.getUserName());
        tkmail.setText(user.getEmail());
        tkphone.setText(user.getPhoneNumber());

        btneditinfo = findViewById(R.id.btneditinfo);
        btneditinfo.setOnClickListener(v -> userUpdate(user));

        btnexit = findViewById(R.id.buttonexittaikhoan);
        btnexit.setOnClickListener(v -> {
            finish();
        });

//        InputFilter filter = new InputFilter() {
//            private final int minLength = 6; // Độ dài tối thiểu
//            private final int maxLength = 20; // Độ dài tối đa
//
//            @Override
//            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
//                // Kết quả sau khi thêm ký tự vào EditText
//                String newText = dest.subSequence(0, dstart).toString() + source.toString() + dest.subSequence(dend, dest.length()).toString();
//
//                // Kiểm tra độ dài tối đa
//                if (newText.length() > maxLength) {
//                    return ""; // Không cho phép thêm ký tự nếu vượt quá maxLength
//                }
//
//                // Kiểm tra ký tự hợp lệ (chỉ cho phép chữ, số, @ và +)
//                if (!source.toString().matches("^[a-zA-Z0-9@#$%&]*$")) {
//                    return ""; // Loại bỏ ký tự không hợp lệ
//                }
//
//                return null; // Cho phép input nếu hợp lệ
//            }
//
//            // Kiểm tra độ dài tối thiểu khi xác nhận
//            public boolean isValidLength(String input) {
//                return input.length() >= minLength;
//            }
//        };
//
//        tkmail.setFilters(new InputFilter[]{filter});
//        tkname.setFilters(new InputFilter[]{filter});
//        tkphone.setFilters(new InputFilter[]{filter});
//
  }

    private void userUpdate(Userinfo user) {
        // Lấy dữ liệu từ các trường
        String name = tkname.getText().toString().trim();
        String phone = tkphone.getText().toString().trim();
        String email = tkmail.getText().toString().trim();



        // Kiểm tra dữ liệu
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }
        int id = SharedPreferencesManager.getUserInfo(Taikhoan.this).getId();

        // Tạo đối tượng Userupdate
        Userupdate userupdate = new Userupdate(name, phone, email);

        // Gửi yêu cầu API
        Userupdaterequest(id, userupdate);


    }

    private void Userupdaterequest(int id, Userupdate userupdate) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse> call = apiService.Userupdatecall(id, userupdate);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SharedPreferencesManager.saveUserInfo(Taikhoan.this, response.body().getUser());
                    Toast.makeText(Taikhoan.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    if (SharedPreferencesManager.getUserInfo(Taikhoan.this).getRole().equals("Admin")) {
                        Intent myIntent = new Intent(Taikhoan.this, MainAdmin.class);
                        startActivity(myIntent);
                        finish(); // Đóng màn hình login
                    }else if (SharedPreferencesManager.getUserInfo(Taikhoan.this).getRole().equals("User")){
                        Intent myIntent1 = new Intent(Taikhoan.this, MainActivity.class);
                        startActivity(myIntent1);
                        finish();
                    }else {
                        Intent myIntent1 = new Intent(Taikhoan.this, MainPTActivity.class);
                        startActivity(myIntent1);
                        finish();
                    }

                } else {
                    Toast.makeText(Taikhoan.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(Taikhoan.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}