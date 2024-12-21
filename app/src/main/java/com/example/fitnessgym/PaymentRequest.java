package com.example.fitnessgym;

public class PaymentRequest {
    int user_id;
    int package_id;
    String payment_method ;
    String type = "package" ;

    public PaymentRequest(int user_id, int package_id, String payment_method) {
        this.user_id = user_id;
        this.package_id = package_id;
        this.payment_method = payment_method;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPackage_id() {
        return package_id;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }


}
