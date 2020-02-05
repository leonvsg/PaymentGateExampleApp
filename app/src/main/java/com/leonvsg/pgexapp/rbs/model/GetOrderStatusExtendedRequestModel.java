package com.leonvsg.pgexapp.rbs.model;

import lombok.Getter;
import lombok.ToString;

@ToString
public class GetOrderStatusExtendedRequestModel {

    @Getter private String userName;
    @Getter private String password;
    @Getter private String orderId;

    public GetOrderStatusExtendedRequestModel(String userName, String password, String orderId) {
        this.userName = userName;
        this.password = password;
        this.orderId = orderId;
    }
}
