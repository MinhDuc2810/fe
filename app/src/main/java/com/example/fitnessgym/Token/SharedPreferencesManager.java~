package com.example.fitnessgym.Token;

import android.content.Context;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.fitnessgym.User.Userinfo;
import com.google.gson.Gson;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "secure_app_prefs";
    private static final String TOKEN_KEY = "access_token";
    private static final String USER_INFO_KEY = "user_info";

    private static EncryptedSharedPreferences getEncryptedSharedPreferences(Context context) throws Exception {
        // Tạo MasterKey để mã hóa dữ liệu
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        // Khởi tạo EncryptedSharedPreferences
        return (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                context,
                PREF_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    public static void saveToken(Context context, String token) {
        try {
            EncryptedSharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
            sharedPreferences.edit().putString(TOKEN_KEY, token).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getToken(Context context) {
        try {
            EncryptedSharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
            return sharedPreferences.getString(TOKEN_KEY, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void clearToken(Context context) {
        try {
            EncryptedSharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
            sharedPreferences.edit().remove(TOKEN_KEY).apply();
            sharedPreferences.edit().remove(USER_INFO_KEY).apply();
            sharedPreferences.edit().remove(PREF_NAME).apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveUserInfo(Context context, Userinfo user) {
        try {
            EncryptedSharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
            Gson gson = new Gson();
            String userInfoJson = gson.toJson(user);
            sharedPreferences.edit().putString(USER_INFO_KEY, userInfoJson).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Userinfo getUserInfo(Context context) {
        try {
            EncryptedSharedPreferences sharedPreferences = getEncryptedSharedPreferences(context);
            String userInfoJson = sharedPreferences.getString(USER_INFO_KEY, null);
            if (userInfoJson != null) {
                Gson gson = new Gson();
                return gson.fromJson(userInfoJson, Userinfo.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
