package com.leonvsg.pgexapp.rbs.model;

import com.leonvsg.pgexapp.rbs.Constants;

/*
Model

{
    "merchant": "sup_test",
    "orderNumber": "orderNumber",
    "returnUrl": "https://google.com",
    "paymentToken": "eyJzaWduYXR1cmUiOiJNRVlUk9",
    "amount" : "24000",
    "currencyCode" : 643
}
 */

public class GooglePaymentRequestModel {

    private String merchant;
    private String orderNumber;
    private String returnUrl;
    private String paymentToken;
    private String amount;
    private Integer currencyCode;

    public GooglePaymentRequestModel(String merchant, String orderNumber, String paymentToken, String amount) {
        this.merchant = merchant;
        this.orderNumber = orderNumber;
        this.paymentToken = paymentToken;
        this.amount = amount;
        this.returnUrl = Constants.RETURN_URL;
        this.currencyCode = Constants.CURRENCY_CODE;
    }

    public String getMerchant() {
        return merchant;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public String getAmount() {
        return amount;
    }

    public Integer getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public String toString() {
        return "GooglePaymentRequestModel{" +
                "merchant='" + merchant + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", paymentToken='" + paymentToken + '\'' +
                ", amount='" + amount + '\'' +
                ", cueencyCode=" + currencyCode +
                '}';
    }
}
