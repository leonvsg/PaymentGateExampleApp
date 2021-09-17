package com.leonvsg.pgexapp.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.AutoResolveHelper;

import com.leonvsg.pgexapp.R;
import com.leonvsg.pgexapp.adapter.LogAdapter;
import com.leonvsg.pgexapp.google.GPayClient;
import com.leonvsg.pgexapp.google.model.PaymentData;
import com.leonvsg.pgexapp.google.model.PaymentToken;
import com.leonvsg.pgexapp.model.LogEntry;
import com.leonvsg.pgexapp.rbs.Constants;
import com.leonvsg.pgexapp.rbs.RBSClient;
import com.leonvsg.pgexapp.rbs.model.GetOrderStatusExtendedResponseModel;
import com.leonvsg.pgexapp.rbs.model.GooglePaymentResponseModel;
import com.leonvsg.pgexapp.rbs.model.PaymentOrderResponseModel;
import com.leonvsg.pgexapp.rbs.model.RegisterOrderResponseModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;

import ru.rbs.mobile.cardchooser.CardChooserActivity;

public class MainActivity extends Activity {

    private static final int LOAD_PAYMENT_DATA_RESULT_CODE = 0;
    private static final int CARD_FORM_RESULT_CODE = 1;
    private static final int WEB_VIEW_RESULT_CODE = 2;

    private LogAdapter logAdapter;
    private GPayClient gPayClient;
    private RBSClient rbsClient;
    private List<String> paymentGateNames;
    private List<String> currencyCharCodes;
    private final Constants.PaymentGates[] paymentGates = Constants.PaymentGates.values();
    private final Constants.Currencies[] currencies = Constants.Currencies.values();
    @BindView(R.id.paymentgates) Spinner mPaymentGateSpinner;
    @BindView(R.id.googlepay_button) View mGooglePayButton;
    @BindView(R.id.googlepay_status) TextView mGooglePayStatusText;
    @BindView(R.id.merchant_login) EditText mMerchantInput;
    @BindView(R.id.api_password) EditText mPasswordInput;
    @BindView(R.id.amount) EditText mAmountInput;
    @BindView(R.id.new_paymentgate_input) EditText mPaymentGateURIInput;
    @BindView(R.id.se_token_pay_button) Button mSeTokenPayButton;
    @BindView(R.id.log_recycler) RecyclerView mLogRecyclerView;
    @BindView(R.id.card_pay_button) Button mCardPayButton;
    @BindView(R.id.currencies_layout) View mCurrenciesLayout;
    @BindView(R.id.currencies) Spinner mCurrenciesSpinner;
    @BindView(R.id.paymentgate_settings) View mPaymentgateSettingsLayout;
    @BindView(R.id.new_gpay_gateway_input) EditText mGooglePayGatewayInput;
    @BindView(R.id.redirect_to_payment_page_button) Button mRedirectToPaymentPageButton;
    @BindView(R.id.payment_page_uri) EditText mPaymentPageUri;
    private Dialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLoadDialog();
        loadDialog.show();
        gPayClient = new GPayClient(this);
        logAdapter = new LogAdapter();
        ButterKnife.bind(this);
        initSpinner();
        mLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLogRecyclerView.setAdapter(logAdapter);
        addLogEntry("Логи взаимодействия систем", null);
        possiblyShowGooglePayButton();
        loadDialog.dismiss();
    }

    private void initLoadDialog(){
        loadDialog = new Dialog(this);
        loadDialog.setCanceledOnTouchOutside(false);
        loadDialog.setCancelable(false);
        loadDialog.setContentView(R.layout.loader);
    }

    private void initSpinner() {
        paymentGateNames = new ArrayList<>();
        currencyCharCodes = new ArrayList<>();
        Arrays.stream(paymentGates).forEach(value-> paymentGateNames.add(value.getName()));
        Arrays.stream(currencies).forEach(currency->currencyCharCodes.add(currency.name()));

        ArrayAdapter<String> paymentGateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paymentGateNames);
        paymentGateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPaymentGateSpinner.setAdapter(paymentGateAdapter);

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencyCharCodes);
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCurrenciesSpinner.setAdapter(currencyAdapter);
    }

    @OnItemSelected(R.id.paymentgates)
    void onSpinnerItemSelected(int position){
        if (position == paymentGates.length-1) {
            mPaymentgateSettingsLayout.setVisibility(View.VISIBLE);
            mCurrenciesLayout.setVisibility(View.VISIBLE);
        } else {
            mPaymentGateURIInput.setText(paymentGates[position].getURI());
            mGooglePayGatewayInput.setText(paymentGates[position].getGooglePayGatewayId());
            mPaymentgateSettingsLayout.setVisibility(View.GONE);
            mCurrenciesLayout.setVisibility(View.GONE);
        }
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
        mSeTokenPayButton.setClickable(clickable);
        mGooglePayButton.setClickable(clickable);
        mCardPayButton.setClickable(clickable);
    }

    private void addLogEntry(String header, String text) {
        logAdapter.setItem(new LogEntry(new Date(), header, text));
    }

    @OnClick({R.id.se_token_pay_button, R.id.googlepay_button, R.id.card_pay_button})
    void requestPayment(View view) {
        setButtonClickable(false);
        clearFocus();

        if (!paramValidate()) {
            setButtonClickable(true);
            return;
        }

        loadDialog.show();

        Constants.PaymentGates paymentGate = Constants.PaymentGates.values()[mPaymentGateSpinner.getSelectedItemPosition()];
        String orderNumber = Long.toString(new Date().getTime());
        String merchantLogin = mMerchantInput.getText().toString();
        String password = mPasswordInput.getText().toString();
        String amount = mAmountInput.getText().toString();
        Integer currency = currencies[mCurrenciesSpinner.getSelectedItemPosition()].getCode();

        if (mPaymentGateSpinner.getSelectedItemPosition() == paymentGates.length-1) {
            rbsClient = new RBSClient(paymentGate, orderNumber, merchantLogin, password, amount,
                    mPaymentGateURIInput.getText().toString(), currency);
        } else {
            rbsClient = new RBSClient(paymentGate, orderNumber, merchantLogin, password, amount, currency);
        }

        switch (view.getId()) {
            case R.id.googlepay_button:
                runGooglePayment();
                break;
            case R.id.se_token_pay_button:
                rbsClient.setSeTokenPayment(true);
                runCardPayment();
                break;
            case R.id.card_pay_button:
                rbsClient.setSeTokenPayment(false);
                runCardPayment();
                break;
        }
    }

    private void runGooglePayment() {
        JSONObject paymentDataRequest = gPayClient.loadPaymentData(mGooglePayGatewayInput.getText().toString(),
                rbsClient.getMerchantLogin(), rbsClient.getAmount(), currencies[mCurrenciesSpinner.getSelectedItemPosition()].name(), LOAD_PAYMENT_DATA_RESULT_CODE);
        addLogEntry("Запрос в Google на получение токена (paymentDataRequest)", paymentDataRequest.toString());
    }

    private void runCardPayment(){
        try {
            rbsClient.registerOrder(this::handleRegisterOrder, this::handleException);
            addLogEntry("Регистрируем заказ в платежном шлюзе", rbsClient.getRegisterOrderRequest() + "; URL: " + rbsClient.getRegisterOrderUrl());
        } catch (Exception e) {
            handleException(e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOAD_PAYMENT_DATA_RESULT_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        com.google.android.gms.wallet.PaymentData gPaymentData = com.google.android.gms.wallet.PaymentData.getFromIntent(data);
                        addLogEntry("От Google получены данные (paymentData)", gPaymentData.toJson());
                        PaymentData paymentData = JSON.parseObject(gPaymentData.toJson(), PaymentData.class);
                        PaymentToken paymentToken = JSON.parseObject(paymentData.getPaymentMethodData().getTokenizationData().getToken(), PaymentToken.class);
                        addLogEntry("Платежный токен (paymentToken)", paymentToken.toString());
                        String base64EncodedToken = Base64.encodeToString(paymentToken.toJson().getBytes(), Base64.NO_WRAP);
                        addLogEntry("Платежный токен в base64", base64EncodedToken);
                        rbsClient.googlePayment(base64EncodedToken, this::handleGooglePayment, this::handleException);
                        addLogEntry("Оплачиваем заказ в платежном шлюзе",
                                rbsClient.getGooglePaymentRequest() + "; URL: " + rbsClient.getGooglePaymentUrl());
                        break;
                    case Activity.RESULT_CANCELED:
                        setButtonClickable(true);
                        loadDialog.dismiss();
                        break;
                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        Log.w("loadPaymentData failed", String.format("Error code: %d", status.getStatusCode()));
                        addLogEntry("Ошибка получения данных от Google", status.getStatusCode()+". "+status.getStatusMessage());
                        break;
                }
                break;
            case CARD_FORM_RESULT_CODE:
                if(data != null && data.hasExtra(CardChooserActivity.EXTRA_RESULT))  {
                    byte[] cryptogram = data.getByteArrayExtra(CardChooserActivity.EXTRA_RESULT);
                    String seToken = new String(cryptogram);
                    seToken = seToken.replace("\n", "");
                    addLogEntry("От SDK получена криптограмма", seToken);
                    rbsClient.paymentOrder(seToken, this::handlePaymentOrder, this::handleException);
                    addLogEntry("Оплачиваем заказ в платежном шлюзе",
                            rbsClient.getPaymentOrderRequest() + "; URL: " + rbsClient.getPaymentOrderUrl());
                } else {
                    Log.w("rbs sdk error", "Not recieved cryptogram from RBS SDK");
                    addLogEntry("От SDK не получена криптограмма", "");
                    loadDialog.dismiss();
                }
                break;
            case WEB_VIEW_RESULT_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        addLogEntry("Вернулись с ACS", "");
                        getOrderStatusExtended();
                        break;
                    case Activity.RESULT_CANCELED:
                        setButtonClickable(true);
                        loadDialog.dismiss();
                        break;
                    case AutoResolveHelper.RESULT_ERROR:

                        break;
                }
                setButtonClickable(true);
                loadDialog.dismiss();
                break;
        }
    }

    private void handleRegisterOrder(RegisterOrderResponseModel response){
        if (response == null) {
            mLogRecyclerView.post(()->addLogEntry("Нет ответа от платежного шлюза, или код HTTP ответа не 200.",
                    "Проверьте корректность адреса шлюза, его доступность или наличие интернета на устройстве"));
            setButtonClickable(true);
            loadDialog.dismiss();
            return;
        }
        mLogRecyclerView.post(()->addLogEntry( "Ответ метода регистрации заказа в платежном шлюзе", response.toString()));
        if (response.getOrderId() == null) {
            mLogRecyclerView.post(()->Toast.makeText(getApplicationContext(), "Ошибка регистрации заказа: "+response.getErrorMessage(), Toast.LENGTH_LONG).show());
            setButtonClickable(true);
            loadDialog.dismiss();
        } else {
            if (rbsClient.isSeTokenPayment()) {
                mLogRecyclerView.post(()->addLogEntry( "Производим переадресацию на форму ввода карточных данных",
                        "Адрес публичного ключа для формирования seToken: "+rbsClient.getSePublicKeysUrl()));
                rbsClient.redirectToCardForm(this, CARD_FORM_RESULT_CODE);
            }
            else {
                mLogRecyclerView.post(()->addLogEntry( "Производим переадресацию на платежную страницу",
                        "Адрес ПС: "+rbsClient.getRegisterOrderResponse().getFormUrl()));
                rbsClient.redirectToPaymentPage(this, WEB_VIEW_RESULT_CODE);
            }
        }
    }

    private void getOrderStatusExtended(){
        rbsClient.getOrderStatusExtended(this::handleGetOrderStatusExtended, this::handleException);
        addLogEntry("Запрашиваем статус платежа",
                rbsClient.getGetOrderStatusExtendedRequest() + "; URL: " + rbsClient.getGetOrderStatusExtendedUrl());
    }

    private void handleGetOrderStatusExtended(GetOrderStatusExtendedResponseModel response){
        if (response == null) {
            mLogRecyclerView.post(()->addLogEntry("Нет ответа от платежного шлюза, или код HTTP ответа не 200.",
                    "Проверьте корректность адреса шлюза, его доступность или наличие интернета на устройстве"));
            return;
        }
        mLogRecyclerView.post(()->addLogEntry( "Ответ метода getOrderStatusExtended", response.toString()));
        setButtonClickable(true);
        loadDialog.dismiss();
    }

    private void handlePaymentOrder(PaymentOrderResponseModel response){
        if (response == null) {
            mLogRecyclerView.post(()->addLogEntry("Нет ответа от платежного шлюза, или код HTTP ответа не 200.",
                    "Проверьте корректность адреса шлюза, его доступность или наличие интернета на устройстве"));
            setButtonClickable(true);
            loadDialog.dismiss();
            return;
        }
        mLogRecyclerView.post(()->addLogEntry( "Ответ метода оплаты заказа в платежном шлюзе", response.toString()));

        if (response.getErrorCode() != 0) {
            mLogRecyclerView.post(()->Toast.makeText(getApplicationContext(), "Ошибка оплаты заказа: "+response.getErrorMessage(), Toast.LENGTH_LONG).show());
            setButtonClickable(true);
            loadDialog.dismiss();
            return;
        }
        if (response.getRedirect() != null) {
            mLogRecyclerView.post(()->Toast.makeText(getApplicationContext(), "Заказ оплачен", Toast.LENGTH_LONG).show());
            getOrderStatusExtended();
        } else {
            mLogRecyclerView.post(()->addLogEntry( "Перенаправляем на ACS: ", rbsClient.getACSRedirectUrl()));
            rbsClient.redirectToAcs(this, WEB_VIEW_RESULT_CODE);
        }
    }

    private void handleGooglePayment(GooglePaymentResponseModel response){
        if (response == null) {
            mLogRecyclerView.post(()->addLogEntry("Нет ответа от платежного шлюза, или код HTTP ответа не 200.",
                    "Проверьте корректность адреса шлюза, его доступность или наличие интернета на устройстве"));
            setButtonClickable(true);
            loadDialog.dismiss();
            return;
        }
        mLogRecyclerView.post(()->addLogEntry( "Ответ метода оплаты заказа через GooglePay в платежном шлюзе", response.toString()));
        if (response.getError() != null) {
            mLogRecyclerView.post(()->Toast.makeText(getApplicationContext(), "Ошибка оплаты заказа: "+response.getError().getDescription(), Toast.LENGTH_LONG).show());
            setButtonClickable(true);
            loadDialog.dismiss();
            return;
        }
        if (response.getData().getAcsUrl() == null) {
            mLogRecyclerView.post(()->Toast.makeText(getApplicationContext(), "Заказ оплачен", Toast.LENGTH_LONG).show());
            getOrderStatusExtended();
        } else {
            mLogRecyclerView.post(()->addLogEntry( "Перенаправляем на ACS: ", rbsClient.getACSRedirectUrl()));
            rbsClient.redirectToAcs(this, WEB_VIEW_RESULT_CODE);
        }
    }

    private void handleException(Exception e){
        mLogRecyclerView.post(()->addLogEntry("Произошла ошибка", e.toString()));
        loadDialog.dismiss();
        setButtonClickable(true);
    }

    @OnClick(R.id.share)
    void shareLog(){
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        Intent chooser = Intent.createChooser(sendIntent, "Отправить лог");
        sendIntent.putExtra(Intent.EXTRA_TEXT, TextUtils.join("\r\n", logAdapter.getAll()));
        sendIntent.setType("text/plain");
        if (sendIntent.resolveActivity(getPackageManager()) != null) startActivity(chooser);
    }

    @OnClick(R.id.redirect_to_payment_page_button)
    void redirectToPaymentPage(){
        String uri = mPaymentPageUri.getText().toString();
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.setData(Uri.parse(uri));
        startActivityForResult(intent, 999);
    }
}
