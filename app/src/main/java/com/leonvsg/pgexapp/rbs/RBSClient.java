package com.leonvsg.pgexapp.rbs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import com.leonvsg.pgexapp.activity.WebViewActivity;
import com.leonvsg.pgexapp.rbs.model.GetOrderStatusExtendedRequestModel;
import com.leonvsg.pgexapp.rbs.model.GetOrderStatusExtendedResponseModel;
import com.leonvsg.pgexapp.rbs.model.GooglePaymentRequestModel;
import com.leonvsg.pgexapp.rbs.model.GooglePaymentResponseModel;
import com.leonvsg.pgexapp.rbs.model.PaymentOrderRequestModel;
import com.leonvsg.pgexapp.rbs.model.PaymentOrderResponseModel;
import com.leonvsg.pgexapp.rbs.model.RegisterOrderRequestModel;
import com.leonvsg.pgexapp.rbs.model.RegisterOrderResponseModel;
import com.leonvsg.pgexapp.rbs.model.RequestModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import lombok.Getter;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import ru.rbs.mobile.cardchooser.CardChooserActivity;

public class RBSClient {

    private static final String TAG = "RBSCLIENT";

    private OkHttpClient httpClient;
    @Getter private String paymentGateURI;
    @Getter private String orderNumber;
    @Getter private String merchantLogin;
    @Getter private String apiUserNameLogin;
    @Getter private String password;
    @Getter private String amount;
    @Getter private String mdOrder;
    @Getter private Constants.PaymentGates paymentGate;

    @Getter private String registerOrderUrl;
    @Getter private String paymentOrderUrl;
    @Getter private String googlePaymentUrl;
    @Getter private String getOrderStatusExtendedUrl;
    @Getter private String sePublicKeysUrl;

    @Getter private RegisterOrderRequestModel registerOrderRequest;
    @Getter private RegisterOrderResponseModel registerOrderResponse;
    @Getter private PaymentOrderRequestModel paymentOrderRequest;
    @Getter private PaymentOrderResponseModel paymentOrderResponse;
    @Getter private GooglePaymentRequestModel googlePaymentRequest;
    @Getter private GooglePaymentResponseModel googlePaymentResponse;
    @Getter private GetOrderStatusExtendedRequestModel getOrderStatusExtendedRequest;
    @Getter private GetOrderStatusExtendedResponseModel getOrderStatusExtendedResponse;

    private RBSClient() {
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
        getOrderStatusExtendedUrl = paymentGateURI+Constants.GET_ORDER_STATUS_URL_END;
        sePublicKeysUrl = paymentGateURI+Constants.SE_PUBLIC_KEY_URL_END;
    }

    public String getACSRedirectUrl(){
        return getACSRedirectUrl(mdOrder);
    }

    private String getACSRedirectUrl(String mdOrder){
        return paymentGateURI+Constants.ACS_REDIRECT_URL_END+"?orderId="+mdOrder;
    }

    public void registerOrder(Callback<RegisterOrderResponseModel> callback, ExceptionHandler exceptionHandler) {
        registerOrderRequest = new RegisterOrderRequestModel(amount, apiUserNameLogin, password, orderNumber);
        executeRequest(registerOrderRequest, registerOrderUrl, (response)->{
            registerOrderResponse = JSON.parseObject(response.body().string(), RegisterOrderResponseModel.class);
            mdOrder = registerOrderResponse.getOrderId();
            callback.execute(registerOrderResponse);
        }, exceptionHandler);
    }

    public void paymentOrder(String seToken, Callback<PaymentOrderResponseModel> callback, ExceptionHandler exceptionHandler){
        paymentOrderRequest = new PaymentOrderRequestModel(apiUserNameLogin, password, mdOrder, seToken, Constants.DEFAULT_CARDHOLDER_NAME);
        executeRequest(paymentOrderRequest, paymentOrderUrl, (response)->{
            paymentOrderResponse = JSON.parseObject(response.body().string(), PaymentOrderResponseModel.class);
            callback.execute(paymentOrderResponse);
        }, exceptionHandler);
    }

    public void googlePayment(String paymentToken, Callback<GooglePaymentResponseModel> callback, ExceptionHandler exceptionHandler){
        googlePaymentRequest = new GooglePaymentRequestModel(merchantLogin, orderNumber, paymentToken, amount);
        executeRequest(googlePaymentRequest, googlePaymentUrl, (response)->{
            googlePaymentResponse = JSON.parseObject(response.body().string(), GooglePaymentResponseModel.class);
            if (googlePaymentResponse.getData() != null) mdOrder = googlePaymentResponse.getData().getOrderId();
            callback.execute(googlePaymentResponse);
        }, exceptionHandler);
    }

    public void getOrderStatusExtended(Callback<GetOrderStatusExtendedResponseModel> callback, ExceptionHandler exceptionHandler){
        getOrderStatusExtendedRequest = new GetOrderStatusExtendedRequestModel(apiUserNameLogin, password, mdOrder);
        executeRequest(getOrderStatusExtendedRequest, getOrderStatusExtendedUrl, (response)->{
            getOrderStatusExtendedResponse = JSON.parseObject(response.body().string(), GetOrderStatusExtendedResponseModel.class);
            callback.execute(getOrderStatusExtendedResponse);
        }, exceptionHandler);
    }

    public void redirectToAcs(Activity context, int resultCode){
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

    private void executeRequest(RequestModel requestModel, String url, ResponseHandler responseHandler, ExceptionHandler exceptionHandler){
        Request request = new Request.Builder()
                .url(url)
                .post(requestModel.getRequestBody())
                .build();
        httpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.w(TAG, e);
                exceptionHandler.handle(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    Log.d(TAG, response.toString());
                    responseHandler.handle(response);
                } catch (IOException e) {
                    Log.w(TAG, e);
                }
            }
        });
    }

    private interface ResponseHandler{ void handle(Response response) throws IOException; }
}
