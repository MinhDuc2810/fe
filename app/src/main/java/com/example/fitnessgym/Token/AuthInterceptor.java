package com.example.fitnessgym.Token;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String token;

    public AuthInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Nếu không có token, thực hiện yêu cầu gốc
        if (token == null || token.isEmpty()) {
            return chain.proceed(originalRequest);
        }

        // Thêm token vào header Authorization
        Request.Builder builder = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + token);

        Request modifiedRequest = builder.build();
        return chain.proceed(modifiedRequest);
    }
}
