package com.example.fitnessgym.UserActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.DateUtils;
import com.example.fitnessgym.Doimatkhau;
import com.example.fitnessgym.Loginactivities;
import com.example.fitnessgym.Membership.Membership;
import com.example.fitnessgym.Package.ClickItemPackageListener;
import com.example.fitnessgym.Package.PackageDetail;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Package.Package;
import com.example.fitnessgym.Schedule;
import com.example.fitnessgym.adapter.PackageAdapter;
import com.example.fitnessgym.Taikhoan;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.User.Userinfo;
import com.example.fitnessgym.adapter.ScheduleAdapter;
import com.example.fitnessgym.api.ApiGetSchedule;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements ClickItemPackageListener {
    TabHost mytab;
    TextView name,email ,phonenumber;
    ImageView image;
    Button btndangxuat, btntaikhoan, btndoimatkhau, btnthuept, btnthetap, btnhistoty, btnrequest;
    PackageAdapter packageAdapter;
    ScheduleAdapter scheduleAdapter;
    RecyclerView recyclePackage, recycleschedule;
    List<Package> packageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RetrofitClient.init(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Userinfo user = SharedPreferencesManager.getUserInfo(this);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phonenumber = findViewById(R.id.phonenumber);
        image = findViewById(R.id.image1);


        btnrequest = findViewById(R.id.btnrequest);
        btnrequest.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PTRequest.class);
            startActivity(intent);
        });



        name.setText(user.getUserName());
        email.setText(user.getEmail());
        phonenumber.setText(user.getPhoneNumber());

        Picasso.get()
                .load(user.getAvatar())
                .into(image);


        btnhistoty = findViewById(R.id.btnhistoty);
        btnhistoty.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PaymentHistoryActivity.class);
            startActivity(intent);
        });

        //Chuyển sang trnag đổi mk
        btndoimatkhau = findViewById(R.id.btndoimatkhau);
        btndoimatkhau.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Doimatkhau.class);
            startActivity(intent);
        });


        btnthuept = findViewById(R.id.btnthuept);
        btnthuept.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ThuePT.class);
            startActivity(intent);
        });


        //Hiển thị thẻ tập trong dialog
        btnthetap = findViewById(R.id.btnthetap);


        // Thêm sự kiện click cho Button
        btnthetap.setOnClickListener(v -> {
            int userId = user.getId();
            showMembershipDialog( userId);
        });

        recycleschedule = findViewById(R.id.recycleschedule);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        recycleschedule.setLayoutManager(linearLayoutManager1);
        fetchScheduleData();




        recyclePackage = findViewById(R.id.recyclePackage);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclePackage.setLayoutManager(linearLayoutManager);

        // Fetch gói tập từ server
        fetchPackageData();

        addControl();

        btntaikhoan = findViewById(R.id.btntaikhoan);
        btntaikhoan.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Taikhoan.class);
            startActivity(intent);
        });

        btndangxuat = findViewById(R.id.btndangxuat);
        btndangxuat.setOnClickListener(view -> {
            // Xóa token khi người dùng đăng xuất
            SharedPreferencesManager.clearToken(MainActivity.this);

            // Chuyển đến màn hình Login
            Intent intent = new Intent(MainActivity.this, Loginactivities.class);
            startActivity(intent);
            finish();
        });

    }


 // Hiển thị lịch tập
    private void fetchScheduleData() {
        int id = SharedPreferencesManager.getUserInfo(this).getId();
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ApiGetSchedule> call = apiService.getSchedule(id);

        call.enqueue(new Callback<ApiGetSchedule>() {
            @Override
            public void onResponse(Call<ApiGetSchedule> call, Response<ApiGetSchedule> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Schedule> scheduleList = response.body().getSchedule();
                    scheduleAdapter = new ScheduleAdapter( scheduleList , MainActivity.this);
                    recycleschedule.setAdapter(scheduleAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Error fetching schedule data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiGetSchedule> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }








    // Hàm để hiển thị thẻ tập trong dialog/
    private void showMembershipDialog(int userId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.activity_my_membership, null);

        // Tạo Dialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);
        AlertDialog dialog = dialogBuilder.create();

        // Ánh xạ các view trong dialog
        TextView mycardname = dialogView.findViewById(R.id.mycardname);
        TextView mycardstart = dialogView.findViewById(R.id.mycardstart);
        TextView mycardend = dialogView.findViewById(R.id.mycardend);
        TextView mycardid = dialogView.findViewById(R.id.mycardid);
        Button btnexitcard = dialogView.findViewById(R.id.btnexitcard);




        // Gọi API để lấy dữ liệu membership
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Membership>> call = apiService.getMembershipsByUserId(userId);
        call.enqueue(new Callback<List<Membership>>() {
            @Override
            public void onResponse(Call<List<Membership>> call, Response<List<Membership>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Membership membership = response.body().get(0); //

                    String formattedStartDate = DateUtils.formatDate(membership.getMembershipStart());
                    String formattedEndDate = DateUtils.formatDate(membership.getMembershipEnd());// Giả sử lấy thẻ đầu tiên

                    // Gán dữ liệu vào Dialog
                    mycardname.setText("Tên : " + membership.getUserName());
                    mycardstart.setText("Bắt đầu : " + formattedStartDate);
                    mycardend.setText("Kết thúc : " + formattedEndDate);
                    mycardid.setText("ID : " + membership.getId());
                } else {
                    mycardname.setText("No membership found.");
                }
            }

            @Override
            public void onFailure(Call<List<Membership>> call, Throwable t) {
                mycardname.setText("Error loading data.");
            }
        });

        // Sự kiện đóng Dialog
        btnexitcard.setOnClickListener(v -> dialog.dismiss());

        // Hiển thị Dialog
        dialog.show();
    }
















    // Hiển thị list gói tập
    private void fetchPackageData() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Package>> call = apiService.getPackages();

        call.enqueue(new Callback<List<Package>>() {
            @Override
            public void onResponse(Call<List<Package>> call, Response<List<Package>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    packageList = response.body();  // Lấy dữ liệu từ phản hồi

                    // Tạo Adapter và gán vào RecyclerView
                    packageAdapter = new PackageAdapter(packageList, MainActivity.this);
                    recyclePackage.setAdapter(packageAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Package>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm để xử lý sự kiện click vào package
    @Override
    public void onClickItemPackage(Package packageItem) {
        // Mở Activity DetailPackageActivity và truyền dữ liệu package
        Intent intent = new Intent(MainActivity.this, PackageDetail.class);
        intent.putExtra("package_details",  packageItem);  // Truyền package vào Intent
        startActivity(intent);
    }

    private void addControl() {
        mytab = findViewById(R.id.mytab);
        mytab.setup();
        // Khai báo tab con
        TabHost.TabSpec spec1 = mytab.newTabSpec("t1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("", getResources().getDrawable(R.drawable.info));
        mytab.addTab(spec1);

        TabHost .TabSpec spec2 = mytab.newTabSpec("t2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("", getResources().getDrawable(R.drawable.calendar));
        mytab.addTab(spec2);

        TabHost.TabSpec spec3 = mytab.newTabSpec("t3");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("", getResources().getDrawable(R.drawable.shop));
        mytab.addTab(spec3);
    }
}
