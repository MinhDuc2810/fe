package com.example.fitnessgym.PtActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.Loginactivities;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Sessions;
import com.example.fitnessgym.Taikhoan;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.User.Userinfo;
import com.example.fitnessgym.Doimatkhau;
import com.example.fitnessgym.adapter.SessionsAdapter;
import com.example.fitnessgym.api.ApiGetSlotforPT;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainPTActivity extends AppCompatActivity {
    TabHost tabpt;
    Button btntaikhoanpt, btndangxuatpt, btndoimatkhaupt, btnduyetlichday, btndanhsachhocvien;
    TextView namept, emailpt, phonenumberpt;
    ImageView imagept;
    RecyclerView recycleLichday;
    SessionsAdapter sessionsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RetrofitClient.init(this);
        setContentView(R.layout.activity_main_ptactivity);
        Userinfo user = SharedPreferencesManager.getUserInfo(this);
        namept = findViewById(R.id.namept);
        emailpt = findViewById(R.id.emailpt);
        phonenumberpt = findViewById(R.id.phonenumberpt);
        imagept = findViewById(R.id.imagept);

        namept.setText(user.getUserName());
        emailpt.setText(user.getEmail());
        phonenumberpt.setText(user.getPhoneNumber());

        Picasso.get()
                .load(user.getAvatar())
                .into(imagept);


        btndanhsachhocvien = findViewById(R.id.btnhocvien);
        btndanhsachhocvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPTActivity.this, DanhsachhocvienActivity.class);
                startActivity(intent);
            }
        });


      btndoimatkhaupt = findViewById(R.id.btndoimatkhaupt);
        btndoimatkhaupt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPTActivity.this, Doimatkhau.class);
                startActivity(intent);
            }
        });



        btnduyetlichday = findViewById(R.id.btnduyetlichday);
        btnduyetlichday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPTActivity.this, Duyetlichday.class);
                startActivity(intent);
            }
        });






        btndangxuatpt = findViewById(R.id.btndangxuatpt);
        btndangxuatpt.setOnClickListener(view -> {
            // Xóa token khi người dùng đăng xuất
            SharedPreferencesManager.clearToken(MainPTActivity.this);

            // Chuyển đến màn hình Login
            Intent intent = new Intent(MainPTActivity.this, Loginactivities.class);
            startActivity(intent);
            finish();
        });



        addControl();

        btntaikhoanpt = findViewById(R.id.btntaikhoanpt);
        btntaikhoanpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPTActivity.this, Taikhoan.class);
                startActivity(intent);
            }
        });


        recycleLichday = findViewById(R.id.recycleLichday);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleLichday.setLayoutManager(linearLayoutManager);
        fetchLichday();

    }












    // quản lý tab host
    private void addControl() {

            tabpt = findViewById(R.id.tabpt);
            tabpt.setup();
            // Khai báo tab con
            TabHost.TabSpec spec1 = tabpt.newTabSpec("t1");
            spec1.setContent(R.id.tabpt1);
            spec1.setIndicator("Cá nhân");
            tabpt.addTab(spec1);


            TabHost.TabSpec spec3 = tabpt.newTabSpec("t3");
            spec3.setContent(R.id.tabpt3);
            spec3.setIndicator("Lịch dạy");
            tabpt.addTab(spec3);
    }




    private void fetchLichday() {
        int pt_id = SharedPreferencesManager.getUserInfo(MainPTActivity.this).getId();
        String status = "approved";
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ApiGetSlotforPT> call = apiService.getLichday(pt_id, status);

        call.enqueue(new Callback<ApiGetSlotforPT>() {
            @Override
            public void onResponse(Call<ApiGetSlotforPT> call, Response<ApiGetSlotforPT> response) {
                if (response.isSuccessful() && response.body().getSessions() != null) {
                    List<Sessions> availablePtsList = (List<Sessions>) response.body().getSessions();  // Lấy dữ liệu từ phản hồi


                    // Tạo Adapter và gán vào RecyclerView với danh sách đã lọc
                    
                    sessionsAdapter = new SessionsAdapter(availablePtsList);
                    recycleLichday.setAdapter(sessionsAdapter);

                } else {
                    Toast.makeText(MainPTActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiGetSlotforPT> call, Throwable t) {
                Toast.makeText(MainPTActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    //
}