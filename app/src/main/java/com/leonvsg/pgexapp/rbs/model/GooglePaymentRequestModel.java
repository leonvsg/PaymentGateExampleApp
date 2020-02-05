package com.leonvsg.pgexapp.rbs.model;

import com.leonvsg.pgexapp.rbs.Constants;

import lombok.Getter;
import lombok.ToString;

@ToString
public class GooglePaymentRequestModel {

    @Getter private String merchant;
    @Getter private String orderNumber;
    @Getter private String returnUrl;
    @Getter private String paymentToken;
    @Getter private String amount;
    @Getter private Integer currencyCode;

    public GooglePaymentRequestModel(String merchant, String orderNumber, String paymentToken, String amount) {
        this.merchant = merchant;
        this.orderNumber = orderNumber;
        this.paymentToken = paymentToken;
        this.amount = amount;
        this.returnUrl = Constants.RETURN_URL;
        this.currencyCode = Constants.CURRENCY_CODE;
    }
}
