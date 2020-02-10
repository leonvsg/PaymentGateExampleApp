package com.leonvsg.pgexapp.rbs.model;

import com.alibaba.fastjson.JSON;
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
    @Getter private Integer currencyCode;

    public GooglePaymentRequestModel(String merchant, String orderNumber, String paymentToken, String amount) {
        this.merchant = merchant;
        this.orderNumber = orderNumber;
        this.paymentToken = paymentToken;
        this.amount = amount;
        this.returnUrl = Constants.RETURN_URL;
        this.currencyCode = Constants.CURRENCY_CODE;
    }

    @Override
    public RequestBody getRequestBody() {
        return RequestBody.create(JSON.toJSONString(this), JSON_MEDIA_TYPE);
    }
}
