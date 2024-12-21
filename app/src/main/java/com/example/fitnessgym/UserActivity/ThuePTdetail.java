package com.example.fitnessgym.UserActivity;

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
import com.example.fitnessgym.PaymentRequest;
import com.example.fitnessgym.PaymentRequetsPT;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.User.Ptinfo;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThuePTdetail extends AppCompatActivity {
    TextView thueptname, thueptprice;
    EditText sessionpt;
    RadioGroup radioGroupmethodpt;
    Button btnthanhtoanpt, btnexitpt, btntotalthuept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thue_ptdetail);

        Ptinfo ptinfo = (Ptinfo) getIntent().getSerializableExtra("ptinfo");


        thueptname = findViewById(R.id.thueptname);
        sessionpt = findViewById(R.id.sessionpt);
        btnthanhtoanpt = findViewById(R.id.btnthanhtoanpt);
        btntotalthuept  = findViewById(R.id.btntotalthuept);
        thueptprice = findViewById(R.id.thueptprice);
btntotalthuept.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        int sessions = Integer.parseInt(sessionpt.getText().toString());
        int totalprice = sessions * 300000;
        thueptprice.setText("Total price: " + totalprice + " đ");
    }
});


        if (ptinfo != null) {
            // Sử dụng dữ liệu packageItem để hiển thị thông tin trong Activity
            // Ví dụ:
            TextView thueptname = findViewById(R.id.thueptname);
            thueptname.setText("PT : " + ptinfo.getUserName());

        }
        btnthanhtoanpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentrequestpt();
            }
        });


 btnexitpt = findViewById(R.id.btnexitthanhtoanpt);
        btnexitpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void paymentrequestpt() {
        Ptinfo ptinfo = (Ptinfo) getIntent().getSerializableExtra("ptinfo");
        int user_id = SharedPreferencesManager.getUserInfo(this).getId();
        int pt_id = ptinfo.getId();
        int sessions = Integer.parseInt(sessionpt.getText().toString());
        String payment_method = "Cash";

        // Tạo đối tượng PaymentRequest
        PaymentRequetsPT request = new PaymentRequetsPT(user_id, pt_id, sessions, payment_method);

        // Gửi request
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse> call = apiService.paymentrequestpt(request);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ThuePTdetail.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ThuePTdetail.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ThuePTdetail.this, "Failed to process payment.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(ThuePTdetail.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}