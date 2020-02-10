package com.leonvsg.pgexapp.rbs.model;

import lombok.Getter;
import lombok.ToString;
import okhttp3.FormBody;
import okhttp3.RequestBody;

@ToString
public class GetOrderStatusExtendedRequestModel implements RequestModel {

    @Getter private String userName;
    @Getter private String password;
    @Getter private String orderId;

    public GetOrderStatusExtendedRequestModel(String userName, String password, String orderId) {
        this.userName = userName;
        this.password = password;
        this.orderId = orderId;
    }

    @Override
    public RequestBody getRequestBody() {
        return new FormBody.Builder()
                .add("orderId", orderId)
                .add("userName", userName)
                .add("password", password)
                .build();
    }
}
