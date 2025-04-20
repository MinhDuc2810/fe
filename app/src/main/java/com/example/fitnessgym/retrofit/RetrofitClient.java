package com.example.fitnessgym.retrofit;

import android.content.Context;

import com.example.fitnessgym.Token.AuthInterceptor;
import com.example.fitnessgym.Token.SharedPreferencesManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://192.168.39.130:80/api/backend/";
    private static Context appContext;

    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            if (appContext == null) {
                throw new IllegalStateException("RetrofitClient not initialized. Call init(Context) first.");
            }

            // Lấy token từ SharedPreferencesManager
            String token = SharedPreferencesManager.getToken(appContext);

            // Tạo HttpLoggingInterceptor
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Log toàn bộ body của request/response

            // Tạo OkHttpClient
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(token)) // Thêm interceptor cho token
                    .addInterceptor(loggingInterceptor)         // Thêm logging interceptor
                    .build();

            // Khởi tạo Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}

