package com.leonvsg.pgexapp.rbs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.alibaba.fastjson.JSON;

import com.leonvsg.pgexapp.activity.WebViewActivity;
import com.leonvsg.pgexapp.rbs.model.GooglePaymentRequestModel;
import com.leonvsg.pgexapp.rbs.model.GooglePaymentResponseModel;
import com.leonvsg.pgexapp.rbs.model.PaymentOrderRequestModel;
import com.leonvsg.pgexapp.rbs.model.PaymentOrderResponseModel;
import com.leonvsg.pgexapp.rbs.model.RegisterOrderRequestModel;
import com.leonvsg.pgexapp.rbs.model.RegisterOrderResponseModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import ru.rbs.mobile.cardchooser.CardChooserActivity;

public class RBSClient {

    private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json");
    private OkHttpClient httpClient;
    private String paymentGateURI;
    private String orderNumber;
    private String merchantLogin;
    private String apiUserNameLogin;
    private String password;
    private String amount;
    private String mdOrder;
    private Constants.PaymentGates paymentGate;

    private String registerOrderUrl;
    private String paymentOrderUrl;
    private String googlePaymentUrl;
    private String getOrderStatusUrl;
    private String sePublickKeysUrl;

    private RegisterOrderRequestModel registerOrderRequest;
    private RegisterOrderResponseModel registerOrderResponse;
    private PaymentOrderRequestModel paymentOrderRequest;
    private PaymentOrderResponseModel paymentOrderResponse;
    private GooglePaymentRequestModel googlePaymentRequestModel;
    private GooglePaymentResponseModel googlePaymentResponse;

    public RBSClient() {
        httpClient = new OkHttpClient();
    }

    public RBSClient(Constants.PaymentGates paymentGate, String orderNumber, String merchantLogin, String password, String amount) {
        this(paymentGate, orderNumber, merchantLogin, password, amount, paymentGate.getURI());
    }

    public RBSClient(Constants.PaymentGates paymentGate, String orderNumber, String merchantLogin, String password, String amount, String paymentGateURI) {
        this();
        this.orderNumber = orderNumber;
        this.merchantLogin = merchantLogin;
        this.password = password;
        this.amount = amount;
        this.apiUserNameLogin = merchantLogin+"-api";
        this.paymentGate = paymentGate;
        this.paymentGateURI = paymentGateURI;
        registerOrderUrl = paymentGateURI+Constants.REGISTER_ORDER_URL_END;
        paymentOrderUrl = paymentGateURI+Constants.CARD_PAYMENT_URL_END;
        googlePaymentUrl = paymentGateURI+Constants.GOOGLE_PAY_PAYMENT_URL_END;
        getOrderStatusUrl = paymentGateURI+Constants.GET_ORDER_STATUS_URL_END;
        sePublickKeysUrl = paymentGateURI+Constants.SE_PUBLIC_KEY_URL_END;
    }

    public String getPaymentGateURI() {
        return paymentGateURI;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getMerchantLogin() {
        return merchantLogin;
    }

    public String getApiUserNameLogin() {
        return apiUserNameLogin;
    }

    public String getPassword() {
        return password;
    }

    public String getAmount() {
        return amount;
    }

    public String getMdOrder() {
        return mdOrder;
    }

    public Constants.PaymentGates getPaymentGate() {
        return paymentGate;
    }

    public String getRegisterOrderUrl() {
        return registerOrderUrl;
    }

    public String getPaymentOrderUrl() {
        return paymentOrderUrl;
    }

    public String getGooglePaymentUrl() {
        return googlePaymentUrl;
    }

    public String getGetOrderStatusUrl() {
        return getOrderStatusUrl;
    }

    public String getSePublickKeysUrl() {
        return sePublickKeysUrl;
    }

    public RegisterOrderRequestModel getRegisterOrderRequest() {
        return registerOrderRequest;
    }

    public RegisterOrderResponseModel getRegisterOrderResponse() {
        return registerOrderResponse;
    }

    public PaymentOrderRequestModel getPaymentOrderRequest() {
        return paymentOrderRequest;
    }

    public PaymentOrderResponseModel getPaymentOrderResponse() {
        return paymentOrderResponse;
    }

    public GooglePaymentRequestModel getGooglePaymentRequestModel() {
        return googlePaymentRequestModel;
    }

    public GooglePaymentResponseModel getGooglePaymentResponse() {
        return googlePaymentResponse;
    }

    public String getACSRedirectUrl(){
        return getACSRedirectUrl(mdOrder);
    }

    public String getACSRedirectUrl(String mdOrder){
        return paymentGateURI+Constants.ACS_REDIRECT_URL_END+"?orderId="+mdOrder;
    }

    public void registerOrder(Callback<RegisterOrderResponseModel> callback) {
        registerOrderRequest = new RegisterOrderRequestModel(amount, apiUserNameLogin, password, orderNumber);
        registerOrder(registerOrderRequest, callback);
    }

    public void registerOrder(RegisterOrderRequestModel requestModel, Callback<RegisterOrderResponseModel> callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("orderNumber", requestModel.getOrderNumber())
                .add("userName", requestModel.getUserName())
                .add("password", requestModel.getPassword())
                .add("returnUrl", requestModel.getReturnUrl())
                .add("amount", requestModel.getAmount())
                .build();
        Request request = new Request.Builder()
                .url(registerOrderUrl)
                .post(requestBody)
                .build();

        httpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.execute(null);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    registerOrderResponse = JSON.parseObject(response.body().string(), RegisterOrderResponseModel.class);
                    mdOrder = registerOrderResponse.getOrderId();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callback.execute(registerOrderResponse);
            }
        });
    }

    public void paymentOrder(String seToken, Callback<PaymentOrderResponseModel> callback){
        paymentOrderRequest = new PaymentOrderRequestModel(apiUserNameLogin, password, mdOrder, seToken, Constants.DEFAULT_CARDHOLDER_NAME);
        paymentOrder(paymentOrderRequest, callback);
    }

    public void paymentOrder(PaymentOrderRequestModel requestModel, Callback<PaymentOrderResponseModel> callback) {
        RequestBody requestBody = new FormBody.Builder()
                .add("MDORDER", requestModel.getMDORDER())
                .add("userName", requestModel.getUserName())
                .add("password", requestModel.getPassword())
                .add("seToken", requestModel.getSeToken())
                .add("TEXT", requestModel.getTEXT())
                .build();
        Request request = new Request.Builder()
                .url(paymentOrderUrl)
                .post(requestBody)
                .build();
        httpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.execute(null);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    paymentOrderResponse = JSON.parseObject(response.body().string(), PaymentOrderResponseModel.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callback.execute(paymentOrderResponse);
            }
        });
    }

    public void googlePayment(String paymentToken, Callback<GooglePaymentResponseModel> callback){
        googlePaymentRequestModel = new GooglePaymentRequestModel(merchantLogin, orderNumber, paymentToken, amount);
        googlePayment(googlePaymentRequestModel, callback);
    }

    public void googlePayment(GooglePaymentRequestModel requestModel, Callback<GooglePaymentResponseModel> callback){
        RequestBody body = RequestBody.create(JSON.toJSONString(requestModel), JSON_MEDIA_TYPE);
        Request request = new Request.Builder()
                .url(googlePaymentUrl)
                .post(body)
                .build();
        httpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callback.execute(null);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    googlePaymentResponse = JSON.parseObject(response.body().string(), GooglePaymentResponseModel.class);
                    mdOrder = googlePaymentResponse.getData().getOrderId();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                callback.execute(googlePaymentResponse);
            }
        });
    }

    public void getOrderStatus(){

    }

    public void redirectToAcs(Activity context, int resultCode){
        redirectToAcs(context, resultCode, mdOrder);
    }

    public void redirectToAcs(Activity context, int resultCode, String mdOrder){
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.setData(Uri.parse(getACSRedirectUrl(mdOrder)));
        context.startActivityForResult(intent, resultCode);
    }

    public void redirectToCardForm(Activity context, int resultCode){
        Intent intent = new Intent(context, CardChooserActivity.class);
        intent.putExtra(CardChooserActivity.EXTRA_PUBLIC_KEY, paymentGateURI+Constants.SE_PUBLIC_KEY_URL_END);
        intent.putExtra(CardChooserActivity.EXTRA_MD_ORDER, mdOrder);
        intent.putExtra(CardChooserActivity.EXTRA_FINISH_BTN_TEXT, "Оплатить");
        context.startActivityForResult(intent, resultCode);
    }
}
