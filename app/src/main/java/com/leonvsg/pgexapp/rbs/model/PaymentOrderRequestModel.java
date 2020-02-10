package com.leonvsg.pgexapp.rbs.model;

import lombok.Getter;
import lombok.ToString;
import okhttp3.FormBody;
import okhttp3.RequestBody;

@ToString
public class PaymentOrderRequestModel implements RequestModel {

    @Getter private String userName;
    @Getter private String password;
    @Getter private String MDORDER;
    @Getter private String seToken;
    @Getter private String TEXT;

    public PaymentOrderRequestModel(String userName, String password, String MDORDER, String seToken, String TEXT) {
        this.userName = userName;
        this.password = password;
        this.MDORDER = MDORDER;
        this.seToken = seToken;
        this.TEXT = TEXT;
    }

    @Override
    public RequestBody getRequestBody() {
        return new FormBody.Builder()
                .add("MDORDER", MDORDER)
                .add("userName", userName)
                .add("password", password)
                .add("seToken", seToken)
                .add("TEXT", TEXT)
                .build();
    }
}
