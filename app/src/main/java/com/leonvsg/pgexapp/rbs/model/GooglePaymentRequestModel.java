package com.leonvsg.pgexapp.rbs.model;

import com.leonvsg.pgexapp.rbs.Constants;

import lombok.Getter;
import lombok.ToString;
import okhttp3.MediaType;
import okhttp3.RequestBody;

@ToString
public class GooglePaymentRequestModel implements RequestModel {

    private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json");

    @Getter private String merchant;
    @Getter private String orderNumber;
    @Getter private String returnUrl;
    @Getter private String paymentToken;
    @Getter private String amount;
    @Getter private String currencyCode;

    public GooglePaymentRequestModel(String merchant, String orderNumber, String paymentToken, String amount, Integer currency) {
        this.merchant = merchant;
        this.orderNumber = orderNumber;
        this.paymentToken = paymentToken;
        this.amount = amount;
        this.returnUrl = Constants.RETURN_URL;
        this.currencyCode = currency.toString();
    }

    @Override
    public RequestBody getRequestBody() {
        String json = String.format(
                "{\"merchant\":\"%s\",\"orderNumber\":\"%s\",\"returnUrl\":\"%s\",\"paymentToken\":\"%s\",\"amount\":\"%s\",\"currencyCode\":%s}",
                merchant, orderNumber, returnUrl, paymentToken, amount, currencyCode);
        return RequestBody.create(json, JSON_MEDIA_TYPE);
    }
}
