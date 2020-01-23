package com.leonvsg.pgexapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.leonvsg.pgexapp.google.GPayClient;
import com.leonvsg.pgexapp.rbs.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ru.rbs.mobile.cardchooser.CardChooserActivity;

public class MainActivity extends Activity {

    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 0;
    private static final int CARD_CHOOSER_RESULT_CODE = 1;

    private GPayClient gPayClient;

    private List<String> pgs;
    private Constants.PaymentGateURI[] paymentgates = Constants.PaymentGateURI.values();
    private Spinner mPaymentgateSpinner;
    private View mGooglePayButton;
    private TextView mGooglePayStatusText;
    private EditText mMerchantInput;
    private EditText mPasswordInput;
    private EditText mAmountInput;
    private EditText mPaymentGateURI;
    private Button mCardPayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gPayClient = new GPayClient(this);

        initUi();
        initSpinner();
        possiblyShowGooglePayButton();

        mGooglePayButton.setOnClickListener(this::requestPayment);
        mCardPayButton.setOnClickListener(this::requestPayment);
    }

    private void initUi() {
        mMerchantInput = findViewById(R.id.merchant_login);
        mPasswordInput = findViewById(R.id.api_password);
        mAmountInput = findViewById(R.id.amount);
        mPaymentGateURI = findViewById(R.id.new_paymentgate_input);
        mPaymentgateSpinner = findViewById(R.id.paymentgates);
        mGooglePayButton = findViewById(R.id.googlepay_button);
        mGooglePayStatusText = findViewById(R.id.googlepay_status);
        mCardPayButton = findViewById(R.id.cardpay_button);
    }

    private void initSpinner() {
        pgs = new ArrayList<>();
        Arrays.stream(Constants.PaymentGateURI.values()).forEach(value->pgs.add(value.getName()));
        pgs.add("Другой");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pgs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPaymentgateSpinner.setAdapter(adapter);

        mPaymentgateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= paymentgates.length) {
                    mPaymentGateURI.setVisibility(View.VISIBLE);
                } else {
                    mPaymentGateURI.setText(paymentgates[position].getURI());
                    mPaymentGateURI.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void possiblyShowGooglePayButton() {
        gPayClient.checkGPayAvailability(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                setGooglePayAvailable(task.getResult());
            } else {
                Log.w("isReadyToPay failed", task.getException());
            }
        });
    }

    private void setGooglePayAvailable(boolean available) {
        if (available) {
            mGooglePayStatusText.setVisibility(View.GONE);
            mGooglePayButton.setVisibility(View.VISIBLE);
        } else {
            mGooglePayStatusText.setText(R.string.googlepay_status_unavailable);
        }
    }

    private boolean paramValidate() {
        return checkText(mMerchantInput, "Указан некорректный логин мерчанта") &&
                checkText(mPasswordInput, "Указан некорректный API пароль") &&
                checkText(mAmountInput, "Указана некорректная сумма") &&
                checkText(mPaymentGateURI, "Указан некорректный адрес платежного шлюза");
    }

    private boolean checkText(EditText view, String errorMessage) {
        String text = view.getText().toString();
        if (text.equals("") || text.contains(" ")){
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void requestPayment(View view) {
        mCardPayButton.setClickable(false);
        mGooglePayButton.setClickable(false);

        if (!paramValidate()) {
            mCardPayButton.setClickable(true);
            mGooglePayButton.setClickable(true);
            return;
        }

        String price = mAmountInput.getText().toString();
        String pgUri = mPaymentGateURI.getText().toString();
        String merchantLogin = mMerchantInput.getText().toString();

        switch (view.getId()) {
            case R.id.googlepay_button:
                runGPay(price, merchantLogin);
                break;
            case R.id.cardpay_button:
                runCardChooserActivity("https://web.rbsuat.com/ab/se/keys.do", Long.toString(new Date().getTime()),"Finish him!");
        }
    }

    private void runGPay(String price, String merchantLogin) {
        JSONObject paymentDataRequest = gPayClient.loadPaymentData(price, LOAD_PAYMENT_DATA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);
                        break;
                    case Activity.RESULT_CANCELED:
                        // Nothing to here normally - the user simply cancelled without selecting a
                        // payment method.
                        break;
                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status.getStatusCode());
                        break;
                    default:
                        // Do nothing.
                }

                // Re-enables the Google Pay payment button.
                mGooglePayButton.setClickable(true);
                break;
            case CARD_CHOOSER_RESULT_CODE:
                if(data != null && data.hasExtra(CardChooserActivity.EXTRA_RESULT))  {
                    byte [] cryptogram =  data.getByteArrayExtra(CardChooserActivity.EXTRA_RESULT);
                    Toast.makeText(this, new String(cryptogram), Toast.LENGTH_LONG)
                            .show();
                }
        }
        mCardPayButton.setClickable(true);
        mGooglePayButton.setClickable(true);
    }

    private void runCardChooserActivity(String publicKey, String mdOrder, String finishBtnText) {

        // Создание интента CardChooserActivity
        Intent intent = new Intent(getApplicationContext(), CardChooserActivity.class);

        // Установка параметров
        intent.putExtra(CardChooserActivity.EXTRA_PUBLIC_KEY, publicKey);
        intent.putExtra(CardChooserActivity.EXTRA_MD_ORDER, mdOrder);
        intent.putExtra(CardChooserActivity.EXTRA_FINISH_BTN_TEXT, finishBtnText);

        // Запуск CardChooserActivity
        startActivityForResult(intent, CARD_CHOOSER_RESULT_CODE);
    }

    private void handlePaymentSuccess(PaymentData paymentData) {
        String paymentInformation = paymentData.toJson();

        // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
        if (paymentInformation == null) {
            return;
        }
        JSONObject paymentMethodData;

        try {
            paymentMethodData = new JSONObject(paymentInformation).getJSONObject("paymentMethodData");
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".
            if (paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("type")
                    .equals("PAYMENT_GATEWAY")
                    && paymentMethodData
                    .getJSONObject("tokenizationData")
                    .getString("token")
                    .equals("examplePaymentMethodToken")) {
                AlertDialog alertDialog =
                        new AlertDialog.Builder(this)
                                .setTitle("Warning")
                                .setMessage(
                                        "Gateway name set to \"example\" - please modify "
                                                + "Constants.java and replace it with your own gateway.")
                                .setPositiveButton("OK", null)
                                .create();
                alertDialog.show();
            }

            Log.d("PaymentInfo", paymentInformation);
            Toast.makeText(this, paymentInformation, Toast.LENGTH_LONG)
                    .show();

            Log.d("All payment method data", paymentMethodData.toString());
            // Logging token string.
            Log.d("GooglePaymentToken", paymentMethodData.getJSONObject("tokenizationData").getString("token"));
        } catch (JSONException e) {
            Log.e("handlePaymentSuccess", "Error: " + e.toString());
            return;
        }
    }

    private void handleError(int statusCode) {
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }
}
