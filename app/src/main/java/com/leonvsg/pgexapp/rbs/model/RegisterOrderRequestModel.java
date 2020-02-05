package com.leonvsg.pgexapp.rbs.model;

import com.leonvsg.pgexapp.rbs.Constants;

import lombok.Getter;
import lombok.ToString;

@ToString
public class RegisterOrderRequestModel {

    @Getter private String amount;
    @Getter private String userName;
    @Getter private String password;
    @Getter private String orderNumber;
    @Getter private String returnUrl;

    public RegisterOrderRequestModel(String amount, String userName, String password, String orderNumber) {
        this.amount = amount;
        this.userName = userName;
        this.password = password;
        this.orderNumber = orderNumber;
        this.returnUrl = Constants.RETURN_URL;
    }
}
