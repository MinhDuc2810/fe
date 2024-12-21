package com.example.fitnessgym.AdminActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.fitnessgym.Loginactivities;
import com.example.fitnessgym.R;
import com.example.fitnessgym.UserActivity.Registeractivities;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;
import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemPt extends AppCompatActivity {



    private Button btnexithempt, btnthempt, btnPtUploadImage;
    private EditText ptname, ptmail, ptphone, ptpass;
    private ImageView imgPtSelected;
    private static final int PICK_IMAGE_REQUEST = 1; // Mã yêu cầu để chọn ảnh từ thư viện
    private Uri imageUri; // URI của ảnh đã chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_pt);
        RetrofitClient.init(this);

        // Initialize views
        initializeViews();

        // Exit button logic
        btnexithempt.setOnClickListener(view -> navigateToMainAdmin());

        // Register button logic
        btnthempt.setOnClickListener(view -> registerUser());

        // Upload Image button logic
        btnPtUploadImage.setOnClickListener(view -> openImageChooser());
    }

    // Initialize all views in a single method
    private void initializeViews() {
        btnexithempt = findViewById(R.id.btnexithempt);
        btnthempt = findViewById(R.id.btnthempt);
        ptname = findViewById(R.id.ptname);
        ptmail = findViewById(R.id.ptmail);
        ptphone = findViewById(R.id.ptphone);
        ptpass = findViewById(R.id.ptpass);
        btnPtUploadImage = findViewById(R.id.btnPtUploadImage);
        imgPtSelected = findViewById(R.id.imgPtSelected); // ImageView để hiển thị ảnh đã chọn
    }

    // Open image chooser
    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Hiển thị ảnh trong ImageView
            Glide.with(this)
                    .load(imageUri)
                    .into(imgPtSelected);

            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle user registration
    private void registerUser() {
        String name = ptname.getText().toString().trim();
        String email = ptmail.getText().toString().trim();
        String phoneNumber = ptphone.getText().toString().trim();
        String password = ptpass.getText().toString().trim();

        // Validate input
        if (!isInputValid(name, email, phoneNumber, password)) {
            return;
        }

        // Tạo RequestBody cho mỗi trường thông tin, bao gồm cả role
        RequestBody namePart = createRequestBody(name);
        RequestBody emailPart = createRequestBody(email);
        RequestBody phonePart = createRequestBody(phoneNumber);
        RequestBody passwordPart = createRequestBody(password);
        RequestBody rolePart = createRequestBody("pt"); // Trường role mặc định là "user"

        // Chuẩn bị ảnh (nếu có)
        MultipartBody.Part imagePart = null;
        if (imageUri != null) {
            File imageFile = new File(getRealPathFromURI(imageUri));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);
            imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
        }

        // Tạo Retrofit instance và gọi API
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Gọi API với các tham số
        Call<ApiResponse> call = apiService.registerPt(namePart, phonePart, emailPart, passwordPart, rolePart, imagePart);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    handleApiResponse(apiResponse);
                } else {
                    Toast.makeText(ThemPt.this, "Đã xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(ThemPt.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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

    // Helper method to create RequestBody
    private RequestBody createRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    // Handle API response
    private void handleApiResponse(ApiResponse apiResponse) {
        if ("success".equals(apiResponse.getMessage())) {
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            navigateToMainAdmin();
        } else {
            Toast.makeText(this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Navigate to login activity
    private void navigateToMainAdmin() {
        Intent intent = new Intent(ThemPt.this, MainAdmin.class);
        startActivity(intent);
        finish();
    }

    // Helper method to get the real path from URI
    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        try (Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(columnIndex);
            }
        }
        return null;
    }



    }


