package com.example.fitnessgym.retrofit;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fitnessgym.MyApp;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;


    // Hàm tạo OkHttpClient với các Interceptors tùy chỉnh
    public class OkHttpClientProvider {

        public static OkHttpClient getOkHttpClient() {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            Interceptor authInterceptor = chain -> {
                Request original = chain.request();

                SharedPreferences sharedPreferences = MyApp.getContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("auth_token", null);

                Request.Builder requestBuilder = original.newBuilder();
                if (token != null) {
                    requestBuilder.header("Authorization", "Bearer " + token);
                }

                Request request = requestBuilder.build();
                return chain.proceed(request);
            };

            return new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor(authInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
    }


