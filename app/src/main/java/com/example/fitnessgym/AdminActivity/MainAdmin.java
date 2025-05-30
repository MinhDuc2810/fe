package com.example.fitnessgym.AdminActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.Doimatkhau;
import com.example.fitnessgym.Loginactivities;
import com.example.fitnessgym.Membership.ClickMembershipListener;
import com.example.fitnessgym.Membership.Membership;
import com.example.fitnessgym.Membership.MembershipDetail;
import com.example.fitnessgym.Package.ClickAdminItemPackageListener;
import com.example.fitnessgym.PtActivity.ClickPTListener;
import com.example.fitnessgym.PtActivity.PTadmindetail;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Package.Package;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.User.Ptinfo;
import com.example.fitnessgym.User.Userinfo;
import com.example.fitnessgym.adapter.PackageAdapter;
import com.example.fitnessgym.Taikhoan;
import com.example.fitnessgym.adapter.MembershipAdapter;
import com.example.fitnessgym.adapter.PtAdapter;
import com.example.fitnessgym.adapter.UserAdapter;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainAdmin extends AppCompatActivity implements ClickAdminItemPackageListener {
    TabHost admintab;
    Button btndangxuatadmin, btngotothempt, btnaddgoitap, btntaikhoanadmin, btndoimatkhauadmin, btnxacnhangiaodich, btndoanhthu;
    private RecyclerView recycleKhach, recyclept, recycleGoitap, recycleThetap ;
    private TextView adminname,adminemail ,adminphonenumber;
    private ImageView adminimage;
    private PackageAdapter packageAdapter;
    private UserAdapter userAdapter;
    private PtAdapter ptAdapter;
    private MembershipAdapter membershipAdapter;
    private PackageAdminAdapter packageAdminAdapter;
    private List<PackageAdmin> packageAdminList;
    private List<Package> packageList;
    private SearchView searchKhach, searchpt, searchThetap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RetrofitClient.init(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_admin);

        adminname = findViewById(R.id.adminname);
        adminemail = findViewById(R.id.adminemail);
        adminphonenumber = findViewById(R.id.adminphonenumber);
        adminimage = findViewById(R.id.adminimage);

        Userinfo user = SharedPreferencesManager.getUserInfo(this);

        adminname.setText(user.getUserName());
        adminemail.setText(user.getEmail());
        adminphonenumber.setText(user.getPhoneNumber());

        Picasso.get()
                .load(user.getAvatar())  // URL của hình ảnh
                .into(adminimage);


        btnxacnhangiaodich = findViewById(R.id.btnxacnhangiaodich);
        btnxacnhangiaodich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAdmin.this, Xacnhangiaodich.class);
                startActivity(intent);
            }
        });


        btndoanhthu = findViewById(R.id.btndoanhthu);
        btndoanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainAdmin.this, DoanhthuActivity.class);
                startActivity(intent);
            }
        });







        


        btndoimatkhauadmin = findViewById(R.id.btndoimatkhauadmin);
        btndoimatkhauadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(MainAdmin.this, Doimatkhau.class);
                startActivity(myintent);
            }
        });





        btndangxuatadmin = findViewById(R.id.btndangxuatadmin);
        btndangxuatadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesManager.clearToken(MainAdmin.this);

                Intent intent =new Intent(MainAdmin.this, Loginactivities.class);
                startActivity(intent);
                finish();
            }
        });

        addControl();



        recycleKhach = findViewById(R.id.recycleKhach);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycleKhach.setLayoutManager(linearLayoutManager);
        fetchUserData();

        recyclept = findViewById(R.id.recyclept);
        LinearLayoutManager linearLayoutManagerpt = new LinearLayoutManager(this);
        recyclept.setLayoutManager(linearLayoutManagerpt);  // Sửa lại ở đây
        fetchPtData();

        recycleThetap = findViewById(R.id.recycleThetap);
        LinearLayoutManager linearLayoutManagerMembership = new LinearLayoutManager(this);
        recycleThetap.setLayoutManager(linearLayoutManagerMembership);  // Sửa lại ở đây
        fetchmembershipData();

        btngotothempt = findViewById(R.id.btngotothempt);
        btngotothempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(MainAdmin.this, ThemPt.class);
                startActivity(myintent);
            }
        });

        recycleGoitap = findViewById(R.id.recycleGoitap);
        LinearLayoutManager linearLayoutManagerpackage = new LinearLayoutManager(this);
        recycleGoitap.setLayoutManager(linearLayoutManagerpackage);  // Sửa lại ở đây
        fetchAdminPackageData();





        searchpt = findViewById(R.id.searchpt);
        searchpt.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ptAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ptAdapter.getFilter().filter(newText);
                return false;
            }
        });



        searchThetap = findViewById(R.id.searchThetap);
        searchThetap.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                membershipAdapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                membershipAdapter.getFilter().filter(newText);
                return false;
            }
        });



        searchKhach = findViewById(R.id.searchKhach);
        searchKhach.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                userAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userAdapter.getFilter().filter(newText);
                return false;
            }
        });


        btnaddgoitap = findViewById(R.id.btnthemgoitap);
        btnaddgoitap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainAdmin.this, AddPackage.class);
                startActivity(intent);
            }
        });


    }




    //Đổ data lên recycle thẻ tập
    private void fetchmembershipData() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Membership>> call = apiService.getAllMembership();

        call.enqueue(new Callback<List<Membership>>() {
            @Override
            public void onResponse(Call<List<Membership>> call, Response<List<Membership>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy toàn bộ danh sách Ptinfo từ API
                    List<Membership> membershipList = response.body();

                    // Tạo Adapter và gán vào RecyclerView với toàn bộ danh sách
                    ClickMembershipListener clickMembershipListener = new ClickMembershipListener() {
                        @Override
                        public void onClickMembership(Membership membership) {
                            // Ví dụ: Mở một Activity để hiển thị chi tiết PT
                            Intent intent = new Intent(MainAdmin.this, MembershipDetail.class);
                            intent.putExtra("membership", membership);
                            startActivity(intent);
                        }
                    };

                    // Gán dữ liệu vào Adapter và RecyclerView
                    membershipAdapter = new MembershipAdapter(membershipList, clickMembershipListener);
                    recycleThetap.setAdapter(membershipAdapter);

                } else {
                    Toast.makeText(MainAdmin.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Membership>> call, Throwable t) {
                Toast.makeText(MainAdmin.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }




    //Đổ data lên recycle PT

    private void fetchPtData() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Ptinfo>> call = apiService.getPts();

        call.enqueue(new Callback<List<Ptinfo>>() {
            @Override
            public void onResponse(Call<List<Ptinfo>> call, Response<List<Ptinfo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Ptinfo> ptinfoList = response.body();  // Lấy dữ liệu từ phản hồi

                    // Tạo danh sách mới để lưu người dùng có role là 'user'
                    List<Ptinfo> filteredPtList = new ArrayList<>();

                    // Lọc người dùng có role = "user"
                    for (Ptinfo ptinfo : ptinfoList) {
                        if ("pt".equals(ptinfo.getRole())) {
                            filteredPtList.add(ptinfo);
                        }
                    }

                    // Tạo Adapter và gán vào RecyclerView với danh sách đã lọc
                    ClickPTListener clickPTListener = new ClickPTListener() {
                        @Override
                        public void onClickPT(Ptinfo ptinfo) {

                            // Ví dụ: Mở một Activity để hiển thị chi tiết PT
                            Intent intent = new Intent(MainAdmin.this, PTadmindetail.class);
                            intent.putExtra("ptinfo", ptinfo);
                            startActivity(intent);
                        }
                    };
                    ptAdapter = new PtAdapter(filteredPtList, clickPTListener);
                    recyclept.setAdapter(ptAdapter);

                } else {
                    Toast.makeText(MainAdmin.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ptinfo>> call, Throwable t) {
                Toast.makeText(MainAdmin.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }






    //Đổ dữ liệu lên adapter gói tập

    private void fetchAdminPackageData() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<PackageAdmin>> call = apiService.getAdminPackages();

        call.enqueue(new Callback<List<PackageAdmin>>() {
            @Override
            public void onResponse(Call<List<PackageAdmin>> call, Response<List<PackageAdmin>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    packageAdminList = response.body();  // Lấy dữ liệu từ phản hồi

                    // Tạo Adapter và gán vào RecyclerView
                    packageAdminAdapter = new PackageAdminAdapter(packageAdminList, MainAdmin.this);
                    recycleGoitap.setAdapter(packageAdminAdapter);
                } else {
                    Toast.makeText(MainAdmin.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PackageAdmin>> call, Throwable t) {
                Toast.makeText(MainAdmin.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm để xử lý sự kiện click vào package
    @Override
    public void onClickAdminItemPackage(PackageAdmin packageAdminItem) {
        // Mở Activity DetailPackageActivity và truyền dữ liệu package
        Intent intent = new Intent(MainAdmin.this, PackageAdminDetail.class);
        intent.putExtra("adminpackage_details",  packageAdminItem);  // Truyền package vào Intent
        startActivity(intent);
    }


    //Xử lý tabhost admin
    private void addControl() {
        admintab = findViewById(R.id.admintab);
        admintab.setup();
        //Khai báo tab con

        TabHost.TabSpec spec4, spec5, spec6, spec7, spec8;

        spec4 = admintab.newTabSpec("t4");
        spec4.setContent(R.id.tab4);
        spec4.setIndicator("", getResources().getDrawable(R.drawable.info));
        admintab.addTab(spec4);

        spec5 = admintab.newTabSpec("t5");
        spec5.setContent(R.id.tab5);
        spec5.setIndicator("", getResources().getDrawable(R.drawable.card));
        admintab.addTab(spec5);

        spec6 = admintab.newTabSpec("t6");
        spec6.setContent(R.id.tab6);
        spec6.setIndicator("", getResources().getDrawable(R.drawable.shop));
        admintab.addTab(spec6);

        spec7 = admintab.newTabSpec("t7");
        spec7.setContent(R.id.tab7);
        spec7.setIndicator("", getResources().getDrawable(R.drawable.empl));
        admintab.addTab(spec7);

        spec8 = admintab.newTabSpec("t8");
        spec8.setContent(R.id.tab8);
        spec8.setIndicator("", getResources().getDrawable(R.drawable.settingacount));
        admintab.addTab(spec8);

    }


//Hiển thị thông tin người duùng lên adapter hiển thị lên recycleview
private void fetchUserData() {
    Retrofit retrofit = RetrofitClient.getRetrofitInstance();
    ApiService apiService = retrofit.create(ApiService.class);
    Call<List<Userinfo>> call = apiService.getUsers();

    call.enqueue(new Callback<List<Userinfo>>() {
        @Override
        public void onResponse(Call<List<Userinfo>> call, Response<List<Userinfo>> response) {
            if (response.isSuccessful() && response.body() != null) {
                List<Userinfo> userinfoList = response.body();  // Lấy dữ liệu từ phản hồi

                // Tạo danh sách mới để lưu người dùng có role là 'user'
                List<Userinfo> filteredUserList = new ArrayList<>();

                // Lọc người dùng có role = "user"
                for (Userinfo user : userinfoList) {
                    if ("User".equals(user.getRole())) {
                        filteredUserList.add(user);
                    }
                }

                // Tạo Adapter và gán vào RecyclerView với danh sách đã lọc
                userAdapter = new UserAdapter(filteredUserList);
                recycleKhach.setAdapter(userAdapter);

            } else {
                Toast.makeText(MainAdmin.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<List<Userinfo>> call, Throwable t) {
            Toast.makeText(MainAdmin.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}


}

