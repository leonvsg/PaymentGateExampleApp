package com.leonvsg.pgexapp.google;

import android.app.Activity;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;

import org.json.JSONObject;

import java.util.Optional;

public class GPayClient {

    private PaymentsClient mPaymentsClient;
    private Activity activity;

    public GPayClient(Activity activity) {
        this.activity = activity;
        mPaymentsClient = PaymentsUtil.createPaymentsClient(activity);
    }

    public void checkGPayAvailability(OnCompleteListener<Boolean> listener) {
        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (!isReadyToPayJson.isPresent()) {
            return;
        }
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        if (request == null) {
            return;
        }

        Task<Boolean> task = mPaymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(activity, listener);
    }

    public JSONObject loadPaymentData(String gateway, String gatewayMerchantId, String price, int resultCode) {
        Optional<JSONObject> paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(price, gateway, gatewayMerchantId);
        if (!paymentDataRequestJson.isPresent()) {
            return paymentDataRequestJson.get();
        }
        Log.d("paymentDataRequestJson", paymentDataRequestJson.get().toString());
        PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());
        if (request != null) {
            AutoResolveHelper.resolveTask(
                    mPaymentsClient.loadPaymentData(request), activity, resultCode);
        }
        return paymentDataRequestJson.get();
    }

}
