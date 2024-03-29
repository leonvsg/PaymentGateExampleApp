package com.leonvsg.pgexapp.rbs.model;

import com.leonvsg.pgexapp.rbs.Constants;

import lombok.Getter;
import lombok.ToString;
import okhttp3.FormBody;
import okhttp3.RequestBody;

@ToString
public class RegisterOrderRequestModel implements RequestModel {

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

    @Override
    public RequestBody getRequestBody() {
        return new FormBody.Builder()
                .add("orderNumber", orderNumber)
                .add("userName", userName)
                .add("password", password)
                .add("returnUrl", returnUrl)
                .add("amount", amount)
                .build();
    }
}
