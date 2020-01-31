package com.leonvsg.pgexapp.rbs;

import com.alibaba.fastjson.JSON;
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

public class RBSClient {

    private OkHttpClient httpClient;
    private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json");

    public RBSClient() {
        httpClient = new OkHttpClient();
    }

    public void registerOrder(RegisterOrderRequestModel requestModel, String endpoint, Callback<RegisterOrderResponseModel> callback) throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("orderNumber", requestModel.getOrderNumber())
                .add("userName", requestModel.getUserName())
                .add("password", requestModel.getPassword())
                .add("returnUrl", requestModel.getReturnUrl())
                .add("amount", requestModel.getAmount())
                .build();
        Request request = new Request.Builder()
                .url(endpoint+Constants.REGISTER_ORDER_URL_END)
                .post(requestBody)
                .build();

        httpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                RegisterOrderResponseModel responseModel = JSON.parseObject(response.body().string(), RegisterOrderResponseModel.class);
                callback.execute(responseModel);
            }
        });
    }
}
