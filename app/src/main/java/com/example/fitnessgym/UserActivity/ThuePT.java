package com.example.fitnessgym.UserActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitnessgym.AdminActivity.MainAdmin;
import com.example.fitnessgym.PtActivity.ClickPTListener;
import com.example.fitnessgym.PtActivity.PTadmindetail;
import com.example.fitnessgym.R;
import com.example.fitnessgym.User.Ptinfo;
import com.example.fitnessgym.adapter.PtAdapter;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ThuePT extends AppCompatActivity {
    private RecyclerView recycleThuept;
    private TextView tvexitthuept;
    private SearchView searchthuept;
    private PtAdapter ptAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thue_pt);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recycleThuept = findViewById(R.id.recycleThuept);
        LinearLayoutManager linearLayoutManagerpt = new LinearLayoutManager(this);
        recycleThuept.setLayoutManager(linearLayoutManagerpt);  // Sửa lại ở đây
        fetchPtData();


        tvexitthuept = findViewById(R.id.tvexitchonpt);
        tvexitthuept.setOnClickListener(v -> {
            Intent intent = new Intent(ThuePT.this, MainActivity.class);
            finish();
        });


        searchthuept = findViewById(R.id.searchthuept);
        searchthuept.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    }

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
                            Intent intent = new Intent(ThuePT.this,ThuePTdetail.class);
                            intent.putExtra("ptinfo", ptinfo);
                            startActivity(intent);
                        }
                    };
                    ptAdapter = new PtAdapter(filteredPtList, clickPTListener);
                    recycleThuept.setAdapter(ptAdapter);

                } else {
                    Toast.makeText(ThuePT.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ptinfo>> call, Throwable t) {
                Toast.makeText(ThuePT.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}