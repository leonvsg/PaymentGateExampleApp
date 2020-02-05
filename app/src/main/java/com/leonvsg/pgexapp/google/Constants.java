package com.leonvsg.pgexapp.google;

import com.google.android.gms.wallet.WalletConstants;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Constants {

    public static final int PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST;
    public static final List<String> SUPPORTED_NETWORKS = Arrays.asList(
            "MASTERCARD",
            "VISA");
    public static final List<String> SUPPORTED_METHODS =
            Arrays.asList(
                    "PAN_ONLY",
                    "CRYPTOGRAM_3DS");
    public static final String COUNTRY_CODE = "RU";
    public static final String CURRENCY_CODE = "RUB";
    public static final String DEFAULT_PAYMENT_GATEWAY_TOKENIZATION_NAME = "alfabank";
    public static final String DEFAULT_PAYMENT_GATEWAY_TOKENIZATION_MERCHANT_ID = "sup_test";

    public static final HashMap<String, String> DEFAULT_PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS =
            new HashMap<String, String>() {
                {
                    put("gateway", DEFAULT_PAYMENT_GATEWAY_TOKENIZATION_NAME);
                    put("gatewayMerchantId", DEFAULT_PAYMENT_GATEWAY_TOKENIZATION_MERCHANT_ID);
                    // Your processor may require additional parameters.
                }
            };

    private Constants() {}
}
