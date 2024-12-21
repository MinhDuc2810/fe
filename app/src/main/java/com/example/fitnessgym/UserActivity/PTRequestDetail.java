package com.example.fitnessgym.UserActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitnessgym.Available_pts;
import com.example.fitnessgym.PTrequest.GetTime;
import com.example.fitnessgym.PTrequest.SentRequest;
import com.example.fitnessgym.PTrequest.Slot;
import com.example.fitnessgym.R;
import com.example.fitnessgym.Token.SharedPreferencesManager;
import com.example.fitnessgym.api.ApiGetTImeAvailable;
import com.example.fitnessgym.api.ApiResponse;
import com.example.fitnessgym.api.ApiService;
import com.example.fitnessgym.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PTRequestDetail extends AppCompatActivity {
    Spinner spinner;
    Button btnrequestpt, btnconfirmdate, btnexit;
    String selectedDate = "";
    int selectedSlotId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ptrequest_detail);

        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Slot selectedSlot = (Slot) parent.getItemAtPosition(position);
                selectedSlotId = selectedSlot.getId(); // Lưu ID vào biến toàn cục
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không có gì được chọn
            }
        });

        btnexit = findViewById(R.id.btnexitptrequest);
        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        CalendarView calendarView = findViewById(R.id.calendarView);
        //Setting chọn ngày

        calendarView.setDate(System.currentTimeMillis());

        // Giới hạn phạm vi: từ hôm nay đến 7 ngày tới
        calendarView.setMinDate(System.currentTimeMillis()+ (1 * 24 * 60 * 60 * 1000)); // Ngày tối thiểu là hôm nay
        calendarView.setMaxDate(System.currentTimeMillis() + (8 * 24 * 60 * 60 * 1000)); // Ngày tối đa là 7 ngày sau

        // Lắng nghe sự kiện khi người dùng chọn ngày
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Lấy giá trị ngày/tháng/năm
                 selectedDate =  year + "-" + (month + 1) + "-" +dayOfMonth ;
            }
        });

        btnconfirmdate = findViewById(R.id.btnconfirmdate);
        btnconfirmdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSlot();
            }
        });

        btnrequestpt = findViewById(R.id.btnrequestpt);
        btnrequestpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sentRequest();
            }
        });










        // Gửi yêu cầu lấy thời gian khả dụng

    }
//Gửi yêu cầu đi
    private void sentRequest() {
        Available_pts available_pts = (Available_pts) getIntent().getSerializableExtra("ptrequest");
        int pt_id = available_pts.getPtId(); // Lấy ID của PT từ Intent
        String date = selectedDate;
        int user_id = SharedPreferencesManager.getUserInfo(PTRequestDetail.this).getId();
        int slot_id = selectedSlotId;

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

// Tạo đối tượng GetTime với thông tin cần thiết (ví dụ: date và pt_id)
        SentRequest sentRequest = new SentRequest(user_id, pt_id, date, slot_id);

// Gửi yêu cầu POST
        Call<ApiResponse> call = apiService.sendrequest(sentRequest);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy danh sách slots từ phản hồi
                    ApiResponse apiResponse = response.body();

                    if (apiResponse.isSuccess()) {

                        Toast.makeText(PTRequestDetail.this, "Gửi yêu cầu thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PTRequestDetail.this, MainActivity.class);
                        startActivity(intent);
                        finish();



                    } else {
                        Toast.makeText(PTRequestDetail.this, "Không có thời gian khả dụng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PTRequestDetail.this, "Đã xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(PTRequestDetail.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }




    // get slot khả dụng
    private void getSlot() {
        String date = selectedDate;
        Available_pts available_pts = (Available_pts) getIntent().getSerializableExtra("ptrequest");
        int pt_id = available_pts.getPtId(); // Lấy ID của PT từ Intent

            // Ngày được chọn từ giao diện người dùng



// Tạo đối tượng GetTime với thông tin cần thiết (ví dụ: date và pt_id)
        GetTime getTime = new GetTime(date, pt_id);

        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

// Gửi yêu cầu POST
        Call<ApiGetTImeAvailable> call = apiService.getOptions(getTime);

        call.enqueue(new Callback<ApiGetTImeAvailable>() {
            @Override
            public void onResponse(Call<ApiGetTImeAvailable> call, Response<ApiGetTImeAvailable> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy danh sách slots từ phản hồi
                    List<Slot> slots = response.body().getSlots();

                    if (slots != null && !slots.isEmpty()) {
                        // Tạo ArrayAdapter để đổ dữ liệu vào Spinner
                        ArrayAdapter<Slot> adapter = new ArrayAdapter<>(
                                PTRequestDetail.this,
                                android.R.layout.simple_spinner_item,
                                slots
                        );
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                    } else {
                        Toast.makeText(PTRequestDetail.this, "Không có thời gian khả dụng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PTRequestDetail.this, "Đã xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiGetTImeAvailable> call, Throwable t) {
                Toast.makeText(PTRequestDetail.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}