package com.example.fitnessgym;

public class PaymentHistory {
    int id;
    String payment_method;
    String price;
    String transaction_date;

    public PaymentHistory(int id, String payment_method, String price, String transaction_date) {
        this.id = id;
        this.payment_method = payment_method;
        this.price = price;
        this.transaction_date = transaction_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }
}
