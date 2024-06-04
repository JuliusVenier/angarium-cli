package com.angarium;

import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.Request;

@RequiredArgsConstructor
public class RequestBuilderService {

    private final String host;
    private final int port;

    public Request loginRequest(String username, String password){
        MultipartBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("j_username", username)
                .addFormDataPart("j_password", password)
                .build();
        return new Request.Builder()
                .url(basicUrl().addPathSegment("j_security_check").build())
                .build();
    }

    private HttpUrl.Builder basicUrl(){
        return new HttpUrl.Builder().host(host).port(port);
    }
}
