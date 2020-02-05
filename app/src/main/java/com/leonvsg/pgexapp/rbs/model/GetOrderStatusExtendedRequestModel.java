package com.leonvsg.pgexapp.rbs.model;

public class GetOrderStatusExtendedRequestModel {

    private String userName;
    private String password;
    private String orderId;

    public GetOrderStatusExtendedRequestModel(String userName, String password, String orderId) {
        this.userName = userName;
        this.password = password;
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "GetOrderStatusRequestModel{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", orderId='" + orderId + '\'' +
                '}';
    }
}
