package com.angarium.service;

import com.angarium.model.FileUploadModel;
import lombok.RequiredArgsConstructor;
import okhttp3.*;

@RequiredArgsConstructor
public class RequestBuilderService {

    private final String host;
    private final int port;
    private final String scheme;
    private final OkHttpClient client;

    public Call loginRequest(String username, String password){
        MultipartBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("j_username", username)
                .addFormDataPart("j_password", password)
                .build();
        return client.newCall(
                new Request.Builder()
                        .post(requestBody)
                        .url(basicUrl().addPathSegment("j_security_check").build())
                .build());
    }

    public Call whoamiRequest(String credential){
        return client.newCall(
                new Request.Builder()
                        .addHeader("Cookie", credential)
                        .url(basicUrl().addEncodedPathSegments("api/user/whoami").build())
                        .build());
    }

    public Call uploadRequest(FileUploadModel fileUploadModel) {
        RequestBody requestBody = RequestBody.create(fileUploadModel.getFile(), MediaType.parse("application/octet-stream"));
        return client.newCall(
                new Request.Builder()
                        .addHeader("Cookie", fileUploadModel.getCredential())
                        .addHeader("encrypted", fileUploadModel.isEncrypted() ? "true" : "false")
                        .addHeader("max-days", String.valueOf(fileUploadModel.getMaxDays()))
                        .addHeader("max-Downloads", String.valueOf(fileUploadModel.getMaxDownloads()))
                        .addHeader("sha256", fileUploadModel.getSha256())
                        .put(requestBody)
                        .url(basicUrl().addPathSegments("api/upload/" + fileUploadModel.getFilename()).build())
                        .build());
    }

    public  Call metaDataRequest(String id) {
        return client.newCall(
                new Request.Builder()
                        .url(basicUrl().addPathSegments("api/meta-data/" + id).build())
                        .build()
        );
    }

    public Call downloadRequest(String id) {
        return client.newCall(
                new Request.Builder()
                        .url(basicUrl().addPathSegments("api/download/" + id).build())
                        .build()
        );
    }

    private HttpUrl.Builder basicUrl(){
        return new HttpUrl.Builder().scheme(scheme).host(host).port(port);
    }
}
