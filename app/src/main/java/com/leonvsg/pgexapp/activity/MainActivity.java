package com.leonvsg.pgexapp.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
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
import com.leonvsg.pgexapp.rbs.RBSClient;
import com.leonvsg.pgexapp.rbs.model.PaymentOrderRequestModel;
import com.leonvsg.pgexapp.rbs.model.PaymentOrderResponseModel;
import com.leonvsg.pgexapp.rbs.model.RegisterOrderRequestModel;
import com.leonvsg.pgexapp.rbs.model.RegisterOrderResponseModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import ru.rbs.mobile.cardchooser.CardChooserActivity;

public class MainActivity extends Activity {

    private static final int LOAD_PAYMENT_DATA_RESULT_CODE = 0;
    private static final int CARD_CHOOSER_RESULT_CODE = 1;
    private static final int WEB_VIEW_RESULT_CODE = 2;

    private LogAdapter logAdapter;
    private GPayClient gPayClient;
    private RBSClient rbsClient;
    private List<String> pgs;
    private Constants.PaymentGates[] paymentgates = Constants.PaymentGates.values();
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
    private Dialog loadDialog;

    private String mdOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadDialog = new Dialog(this);
        loadDialog.setCanceledOnTouchOutside(false);
        loadDialog.setContentView(R.layout.loader);
        loadDialog.show();

        gPayClient = new GPayClient(this);
        logAdapter = new LogAdapter();
        rbsClient = new RBSClient();

        initUi();
        possiblyShowGooglePayButton();

        loadDialog.dismiss();
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
        addLogEntry("Логи взаимодействия систем", null);
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
                if (position == paymentgates.length-1) {
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

    private void clearFocus(){
        mAmountInput.clearFocus();
        mMerchantInput.clearFocus();
        mPasswordInput.clearFocus();
        mPaymentGateURIInput.clearFocus();
    }

    private void setButtonClickable(boolean clickable) {
        mCardPayButton.setClickable(clickable);
        mGooglePayButton.setClickable(clickable);
    }

    private void addLogEntry(String header, String text) {
        logAdapter.setItem(new LogEntry(new Date(), header, text));
    }

    private void requestPayment(View view) {
        setButtonClickable(false);
        clearFocus();

        if (!paramValidate()) {
            setButtonClickable(true);
            return;
        }

        loadDialog.show();

        String price = mAmountInput.getText().toString();
        String merchantLogin = mMerchantInput.getText().toString();
        String gatewayId = Constants.PaymentGates.values()[mPaymentgateSpinner.getSelectedItemPosition()].getGPayGatewayId();
        String pgUri = mPaymentGateURIInput.getText().toString();
        String password = mPasswordInput.getText().toString();

        switch (view.getId()) {
            case R.id.googlepay_button:
                runGPay(gatewayId, merchantLogin, price);
                break;
            case R.id.cardpay_button:
                registerOrder(pgUri, price, merchantLogin+"-api", password);
        }
    }

    private void runGPay(String gatewayId, String merchantLogin, String price) {
        JSONObject paymentDataRequest = gPayClient.loadPaymentData(gatewayId, merchantLogin, price, LOAD_PAYMENT_DATA_RESULT_CODE);
        addLogEntry("Запрос в Google на получение токена (paymentDataRequest)", paymentDataRequest.toString());
    }

    private void registerOrder(String pgUri, String amount, String userName, String password) {
        try {
            RegisterOrderRequestModel request = new RegisterOrderRequestModel(amount, userName, password, Long.toString(new Date().getTime()));
            addLogEntry("Регистрируем заказ в платежном шлюзе", request.toString() + "; URL: " + pgUri+Constants.REGISTER_ORDER_URL_END);
            rbsClient.registerOrder(request, pgUri, response->runCardChooserActivity(response, pgUri));
        } catch (Exception e) {
            addLogEntry( "Что-то пошло не так", e.toString());
        }
    }

    private void runCardChooserActivity(RegisterOrderResponseModel response, String pgUri){
        if (response == null) {
            mLogRecyclerView.post(()->addLogEntry("Нет ответа от платежного шлюза, или код HTTP ответа не 200.", "Проверьте корректность адреса шлюза, его доступность или наличие интернета на устройстве"));
            setButtonClickable(true);
            loadDialog.dismiss();
            return;
        }
        mLogRecyclerView.post(()->addLogEntry( "Ответ метода регистрации заказа в платежном шлюзе", response.toString()));

        mdOrder = response.getOrderId();

        // Создание интента CardChooserActivity
        Intent intent = new Intent(getApplicationContext(), CardChooserActivity.class);

        // Установка параметров
        intent.putExtra(CardChooserActivity.EXTRA_PUBLIC_KEY, pgUri+Constants.SE_PUBLIC_KEY_URL_END);
        intent.putExtra(CardChooserActivity.EXTRA_MD_ORDER, mdOrder);
        intent.putExtra(CardChooserActivity.EXTRA_FINISH_BTN_TEXT, "Оплатить");

        // Запуск CardChooserActivity
        startActivityForResult(intent, CARD_CHOOSER_RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_RESULT_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        addLogEntry("От Google получены данные (paymentData)", paymentData.toJson());
                        handleGooglePayment(paymentData);
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
                    String cryptogram = new String(data.getByteArrayExtra(CardChooserActivity.EXTRA_RESULT));
                    cryptogram = cryptogram.replace("\n", "");
                    addLogEntry("От SDK получена криптограмма", cryptogram);
                    String merchantLogin = mMerchantInput.getText().toString();
                    String pgUri = mPaymentGateURIInput.getText().toString();
                    String password = mPasswordInput.getText().toString();
                    PaymentOrderRequestModel request = new PaymentOrderRequestModel(merchantLogin+"-api", password, mdOrder, cryptogram, "SUPPORT TEST");
                    addLogEntry("Оплачиваем заказ в платежном шлюзе", request.toString() + "; URL: " + pgUri+Constants.CARD_PAYMENT_URL_END);
                    rbsClient.paymentOrder(request, pgUri, (response)->handleCardPayment(response, pgUri));
                }
                break;
            case WEB_VIEW_RESULT_CODE:
                //handle webView result
                break;
        }
    }

    private void handleCardPayment(PaymentOrderResponseModel response, String pgUri){
        if (response == null) {
            mLogRecyclerView.post(()->addLogEntry("Нет ответа от платежного шлюза, или код HTTP ответа не 200.", "Проверьте корректность адреса шлюза, его доступность или наличие интернета на устройстве"));
            setButtonClickable(true);
            loadDialog.dismiss();
            return;
        }
        mLogRecyclerView.post(()->addLogEntry( "Ответ метода оплаты заказа в платежном шлюзе", response.toString()));
        String acsRedirectUrl = rbsClient.getACSRedirectUrl(pgUri, mdOrder);
        mLogRecyclerView.post(()->addLogEntry( "Перенаправоляем на ACS: ", acsRedirectUrl));
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.setData(Uri.parse(acsRedirectUrl));
        startActivityForResult(intent, WEB_VIEW_RESULT_CODE);
        setButtonClickable(true);
        loadDialog.dismiss();
    }

    private void handleGooglePayment(PaymentData paymentData) {
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
