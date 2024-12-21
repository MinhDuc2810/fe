package com.example.fitnessgym.AdminActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitnessgym.PtSpinner;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Statistical.Allstatistical;
import com.example.fitnessgym.Statistical.Daystatistical;
import com.example.fitnessgym.Statistical.PTstatistical;
import com.example.fitnessgym.api.ApiGetstatistical;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoanhthuActivity extends AppCompatActivity {
    EditText edtmonthpt , edtyearpt,edtday, edtmonthday ,edtyearday ,edtmonthall ,edtyearall;
    Button btntotalpt, btntotalday, btntotalall;
    TextView totalday, totalall, totalpt, btnexit ;
    Spinner spinner2;
    int selectedPtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doanhthu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtday = findViewById(R.id.edtday);
        edtyearday = findViewById(R.id.edtyearday);
        edtyearall = findViewById(R.id.edtyearall);
        edtyearpt = findViewById(R.id.edtyearpt);
        edtmonthday = findViewById(R.id.edtmonthday);
        edtmonthall = findViewById(R.id.edtmonthall);
        edtmonthpt = findViewById(R.id.edtmonthpt);
        btntotalday = findViewById(R.id.btntotalday);
        btntotalall = findViewById(R.id.btntotalall);
        btntotalpt = findViewById(R.id.btntotalpt);
        totalday = findViewById(R.id.totalday);
        totalall = findViewById(R.id.totalall);
        totalpt = findViewById(R.id.totalpt);


        spinner2 = findViewById(R.id.spinner2);
        getPtSpinner();

        btntotalpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getptsatis();
            }
        });

        btntotalday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdaysatis();
            }
        });

        btntotalall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getallsatis();
            }
        });

        btnexit = findViewById(R.id.tvexitdoanhthu);
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    // doanh thu theo tháng
    private void getallsatis() {
        int month = Integer.parseInt(edtmonthall.getText().toString());
        int year = Integer.parseInt(edtyearall.getText().toString());
        Allstatistical allstatistical = new Allstatistical(month, year);
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiGetstatistical> call = apiService.getAllStatistical(allstatistical);
        call.enqueue(new Callback<ApiGetstatistical>() {
            @Override
            public void onResponse(Call<ApiGetstatistical> call, Response<ApiGetstatistical> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiGetstatistical apiGetstatistical = response.body();
                    totalall.setText("Doanh thu : " + apiGetstatistical.getTotal()+" đ");
                    Toast.makeText(DoanhthuActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DoanhthuActivity.this, "That bai", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiGetstatistical> call, Throwable t) {
                Toast.makeText(DoanhthuActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    };





    // doanh thu theo ngày
    private void getdaysatis() {

        int day = Integer.parseInt(edtday.getText().toString());
        int month = Integer.parseInt(edtmonthday.getText().toString());
        int year = Integer.parseInt(edtyearday.getText().toString());
        Daystatistical daystatistical = new Daystatistical(day, month, year);
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiGetstatistical> call = apiService.getDayStatistical(daystatistical);
        call.enqueue(new Callback<ApiGetstatistical>() {
            @Override
            public void onResponse(Call<ApiGetstatistical> call, Response<ApiGetstatistical> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiGetstatistical apiGetstatistical = response.body();
                    totalday.setText("Doanh thu : " + apiGetstatistical.getTotal()+" đ");
                    Toast.makeText(DoanhthuActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DoanhthuActivity.this, "That bai", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ApiGetstatistical> call, Throwable t) {
                Toast.makeText(DoanhthuActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
    });
    };






    // doanh thu theo PT
    private void getptsatis() {

        int month = Integer.parseInt(edtmonthpt.getText().toString());
        int year = Integer.parseInt(edtyearpt.getText().toString());
        int pt_id = selectedPtId;
        PTstatistical ptstatistical = new PTstatistical(pt_id, month, year);
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiGetstatistical> call = apiService.getPtStatistical(ptstatistical);

        call .enqueue(new Callback<ApiGetstatistical>() {
            @Override
            public void onResponse(Call<ApiGetstatistical> call, Response<ApiGetstatistical> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiGetstatistical apiGetstatistical = response.body();
                    totalpt.setText("Doanh thu : " + apiGetstatistical.getTotal()+" đ");
                    Toast.makeText(DoanhthuActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DoanhthuActivity.this, "That bai", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiGetstatistical> call, Throwable t) {
                Toast.makeText(DoanhthuActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }









    private void getPtSpinner() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<PtSpinner>> call = apiService.getPtSpinner();

        call.enqueue(new Callback<List<PtSpinner>>() {
            @Override
            public void onResponse(Call<List<PtSpinner>> call, Response<List<PtSpinner>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PtSpinner> ptList = response.body();

                    // Tạo adapter cho Spinner
                    ArrayAdapter<PtSpinner> adapter = new ArrayAdapter<>(
                            DoanhthuActivity.this,
                            android.R.layout.simple_spinner_item,
                            ptList
                    );

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Gắn adapter vào Spinner
                    spinner2.setAdapter(adapter);

                    // Thêm sự kiện chọn item
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            PtSpinner selectedPt = (PtSpinner) parent.getItemAtPosition(position);
                            String ptName = selectedPt.getPtName();
                            selectedPtId = selectedPt.getPtId(); // Lưu ptId vào biến toàn cục

                            // Hiển thị thông báo
                            Toast.makeText(DoanhthuActivity.this, "Chọn PT: " + ptName, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Không chọn gì
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<PtSpinner>> call, Throwable t) {
                Toast.makeText(DoanhthuActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Ví dụ sử dụng ptId trong một phương thức khác
    private void useSelectedPtId() {
        if (selectedPtId > 0) {
            Toast.makeText(this, "Đang sử dụng PT ID: " + selectedPtId, Toast.LENGTH_SHORT).show();
            // Gọi API khác hoặc xử lý với ptId
        } else {
            Toast.makeText(this, "Chưa chọn PT", Toast.LENGTH_SHORT).show();
        }
    }

}
