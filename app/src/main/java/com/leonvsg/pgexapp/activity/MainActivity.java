package com.leonvsg.pgexapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.leonvsg.pgexapp.R;
import com.leonvsg.pgexapp.adapter.LogAdapter;
import com.leonvsg.pgexapp.google.GPayClient;
import com.leonvsg.pgexapp.model.LogEntry;
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

    private LogAdapter logAdapter;
    private GPayClient gPayClient;
    private List<String> pgs;
    private Constants.PaymentGateURI[] paymentgates = Constants.PaymentGateURI.values();
    private Spinner mPaymentgateSpinner;
    private View mGooglePayButton;
    private TextView mGooglePayStatusText;
    private EditText mMerchantInput;
    private EditText mPasswordInput;
    private EditText mAmountInput;
    private EditText mPaymentGateURIInput;
    private Button mCardPayButton;
    private RecyclerView mLogRecyclerView;
    private ExtendedFloatingActionButton mShareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gPayClient = new GPayClient(this);
        logAdapter = new LogAdapter();

        initUi();
        possiblyShowGooglePayButton();
    }

    private void initUi() {
        mMerchantInput = findViewById(R.id.merchant_login);
        mPasswordInput = findViewById(R.id.api_password);
        mAmountInput = findViewById(R.id.amount);
        mPaymentGateURIInput = findViewById(R.id.new_paymentgate_input);
        mPaymentgateSpinner = findViewById(R.id.paymentgates);
        mGooglePayButton = findViewById(R.id.googlepay_button);
        mGooglePayStatusText = findViewById(R.id.googlepay_status);
        mCardPayButton = findViewById(R.id.cardpay_button);
        mLogRecyclerView = findViewById(R.id.log_recycler);
        mShareButton = findViewById(R.id.share);

        initSpinner();
        mGooglePayButton.setOnClickListener(this::requestPayment);
        mCardPayButton.setOnClickListener(this::requestPayment);
        mShareButton.setOnClickListener(this::shareLog);
        mLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLogRecyclerView.setAdapter(logAdapter);
    }

    private void initSpinner() {
        pgs = new ArrayList<>();
        Arrays.stream(paymentgates).forEach(value->pgs.add(value.getName()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, pgs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPaymentgateSpinner.setAdapter(adapter);

        mPaymentgateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= paymentgates.length) {
                    mPaymentGateURIInput.setVisibility(View.VISIBLE);
                } else {
                    mPaymentGateURIInput.setText(paymentgates[position].getURI());
                    mPaymentGateURIInput.setVisibility(View.GONE);
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
                checkText(mPaymentGateURIInput, "Указан некорректный адрес платежного шлюза");
    }

    private boolean checkText(EditText view, String errorMessage) {
        String text = view.getText().toString();
        if (text.equals("") || text.contains(" ")){
            Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void changeButtonClickable(boolean clickable) {
        mCardPayButton.setClickable(clickable);
        mGooglePayButton.setClickable(clickable);
    }

    private void requestPayment(View view) {
        changeButtonClickable(false);

        if (!paramValidate()) {
            changeButtonClickable(true);
            return;
        }

        String price = mAmountInput.getText().toString();
        String merchantLogin = mMerchantInput.getText().toString();
        String gatewayId = Constants.PaymentGateURI.values()[mPaymentgateSpinner.getSelectedItemPosition()].getGPayGatewayId();
        String pgUri = mPaymentGateURIInput.getText().toString();

        switch (view.getId()) {
            case R.id.googlepay_button:
                runGPay(gatewayId, merchantLogin, price);
                break;
            case R.id.cardpay_button:
                runCardChooserActivity(pgUri+Constants.SE_PUBLIC_KEY_URL_END, Long.toString(new Date().getTime()),"Оплатить");
        }
    }

    private void runGPay(String gatewayId, String merchantLogin, String price) {
        JSONObject paymentDataRequest = gPayClient.loadPaymentData(gatewayId, merchantLogin, price, LOAD_PAYMENT_DATA_REQUEST_CODE);
        logAdapter.setItem(new LogEntry(new Date(), "Запрос в Google на получение токена (paymentDataRequest)", paymentDataRequest.toString()));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        logAdapter.setItem(new LogEntry(new Date(), "От Google получены данные (paymentData)", paymentData.toJson()));
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
                break;
            case CARD_CHOOSER_RESULT_CODE:
                if(data != null && data.hasExtra(CardChooserActivity.EXTRA_RESULT))  {
                    byte [] cryptogram =  data.getByteArrayExtra(CardChooserActivity.EXTRA_RESULT);
                    logAdapter.setItem(new LogEntry(new Date(), "От SDK получена криптограмма", new String(cryptogram)));
                }
        }
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

    private void shareLog(View view){
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        Intent chooser = Intent.createChooser(sendIntent, "Отправить лог");
        sendIntent.putExtra(Intent.EXTRA_TEXT, TextUtils.join("\r\n", logAdapter.getAll()));
        sendIntent.setType("text/plain");
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        }
    }
}
