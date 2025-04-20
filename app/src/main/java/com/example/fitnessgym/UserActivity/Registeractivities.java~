package com.example.fitnessgym.UserActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fitnessgym.OTP;
import com.example.fitnessgym.R;
import com.example.fitnessgym.User.RegisterRequest;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class Registeractivities extends AppCompatActivity {
    private Button  btnRegister,btnverify ;
    private EditText edtName, edtMail, edtPhone, edtPass, edtotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RetrofitClient.init(this);
        setContentView(R.layout.activity_register_activities);

        // Initialize views
        initializeViews();

        //Khai báo OTP
        RetrofitClient.init(getApplicationContext());
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

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
//        edtotp.setFilters(new InputFilter[]{filter});
//        edtMail.setFilters(new InputFilter[]{filter});
//        edtName.setFilters(new InputFilter[]{filter});
//        edtPass.setFilters(new InputFilter[]{filter});
//        edtPhone.setFilters(new InputFilter[]{filter});

        btnverify = findViewById(R.id.btnverify);
        edtotp = findViewById(R.id.edtotp);


        btnverify.setOnClickListener(v -> {
            String otp = edtotp.getText().toString().trim();
            String email = edtMail.getText().toString().trim();
            if (otp.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập otp", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tạo đối tượng Email
            OTP otpData = new OTP(otp,email);

            // Gửi request thông qua Retrofit
            apiService.sendOtp(otpData).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Registeractivities.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(Registeractivities.this, Loginactivities.class);
//                        startActivity(intent);
//                        finish();
                    } else {
                        Toast.makeText(Registeractivities.this, response.body().getMessage() + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(Registeractivities.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


        // Register button logic
        btnRegister.setOnClickListener(view -> registerUser());



    }

    // Initialize all views in a single method
    private void initializeViews() {
        btnRegister = findViewById(R.id.btnregister);
        edtName = findViewById(R.id.edtname);
        edtMail = findViewById(R.id.edtmail);
        edtPhone = findViewById(R.id.edtphone);
        edtPass = findViewById(R.id.edtpass);
         // ImageView để hiển thị ảnh đã chọn
    }

    // Open image chooser

    // Handle user registration
    private void registerUser() {
        String name = edtName.getText().toString().trim();
        String email = edtMail.getText().toString().trim();
        String phoneNumber = edtPhone.getText().toString().trim();
        String password = edtPass.getText().toString().trim();

        // Validate input
        if (!isInputValid(name, email, phoneNumber, password)) {
            return;
        }

        // Tạo Retrofit instance và gọi API
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Tạo đối tượng RegisterRequest
        RegisterRequest request = new RegisterRequest(name, phoneNumber, email, password);

        // Gửi yêu cầu POST
        Call<ApiResponse> call = apiService.registerWithImage(request);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    handleApiResponse(apiResponse);
                } else {
                    Toast.makeText(Registeractivities.this, "Đã xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(Registeractivities.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Validate user input
    private boolean isInputValid(String name, String email, String phoneNumber, String password) {
        if (name.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phoneNumber.length() < 10) {
            Toast.makeText(this, "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Handle API response
    private void handleApiResponse(ApiResponse apiResponse) {
        if ("success".equals(apiResponse.getStatus())) { // Giả sử `status` là trường trả về
            Toast.makeText(this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
            // Điều hướng sang trang khác nếu cần
        } else {
            Toast.makeText(this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}






