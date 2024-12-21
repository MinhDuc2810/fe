package com.example.fitnessgym;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String formatDate(String inputDate) {
        // Định dạng ngày từ API (giả sử là yyyy-MM-dd)
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Định dạng ngày muốn hiển thị (dd/MM/yyyy)
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = inputFormat.parse(inputDate); // Chuyển chuỗi thành Date
            return outputFormat.format(date);         // Định dạng lại và trả về chuỗi
        } catch (ParseException e) {
            e.printStackTrace();
            return inputDate; // Nếu lỗi, trả về chuỗi gốc
        }
    }
}
