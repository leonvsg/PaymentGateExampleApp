package com.leonvsg.pgexapp.rbs.model;

import com.leonvsg.pgexapp.rbs.Constants;

public class RegisterOrderRequestModel {

    private int amount;
    private String userName;
    private String password;
    private String orderNumber;
    private String returnUrl;

    public RegisterOrderRequestModel(int amount, String userName, String password, String orderNumber) {
        this.amount = amount;
        this.userName = userName;
        this.password = password;
        this.orderNumber = orderNumber;
        this.returnUrl = Constants.RETURN_URL;
    }

    public int getAmount() {
        return amount;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    @Override
    public String toString() {
        return "RegisterOrderRequestModel{" +
                "amount=" + amount +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                '}';
    }
}
