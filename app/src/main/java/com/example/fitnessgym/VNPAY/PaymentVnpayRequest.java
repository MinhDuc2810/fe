package com.example.fitnessgym.VNPAY;

import com.google.gson.annotations.SerializedName;

public class PaymentVnpayRequest {
    @SerializedName("amount")
    private int amount; // Amount in VND

    @SerializedName("vnp_TmnCode")
    private String vnpTmnCode;

    @SerializedName("vnp_HashSecret")
    private String vnpHashSecret;

    @SerializedName("vnp_ReturnUrl")
    private String vnpReturnUrl;

    // Constructor
    public PaymentVnpayRequest(int amount, String vnpTmnCode, String vnpHashSecret, String vnpReturnUrl) {
        this.amount = amount;
        this.vnpTmnCode = vnpTmnCode;
        this.vnpHashSecret = vnpHashSecret;
        this.vnpReturnUrl = vnpReturnUrl;
    }
}
