package com.example.fitnessgym;

import com.google.gson.annotations.SerializedName;

public class PaymentRequetsPT {
    int user_id;
    int pt_id;
    int sessions;
    String payment_method ;
    String type = "pt" ;

    public PaymentRequetsPT(int user_id, int pt_id, int sessions, String payment_method) {
        this.user_id = user_id;
        this.pt_id = pt_id;
        this.sessions = sessions;
        this.payment_method = payment_method;
    }
}
