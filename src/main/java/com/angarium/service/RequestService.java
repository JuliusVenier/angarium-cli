package com.angarium.service;

import com.angarium.model.*;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class RequestService {
    private final RequestBuilderService requestBuilderService;
    private final Gson gson;

    public String login(String username, String password) throws IOException, RequestServiceException {
        Response response = requestBuilderService.loginRequest(username, password).execute();
        checkResponse(response, "Unable to Login, check Username or password");
        return response.header("set-cookie");
    }
    public UserModel whoami(String credential) throws IOException, RequestServiceException {
        Response response = requestBuilderService.whoamiRequest(credential).execute();
        checkResponse(response, "Unable to call whoami, request failed");
        return gson.fromJson(response.body().string(), UserModel.class);
    }

    public FileIdModel upload(FileUploadModel fileUploadModel) throws IOException, RequestServiceException {
        Response response = requestBuilderService.uploadRequest(fileUploadModel).execute();
        checkResponse(response, "Unable to upload File, request failed");
        return gson.fromJson(response.body().string(), FileIdModel.class);
    }

    public FileDownloadModel download(String id) throws IOException, RequestServiceException {
        Response response = requestBuilderService.downloadRequest(id).execute();
        checkResponse(response, "Unable to download File, request failed");

        return new FileDownloadModel(response.header("Content-Disposition").substring(20), response.body().byteStream());
    }

    public FileMetaDataModel fileMetaData(String id) throws IOException, RequestServiceException {
        Response response = requestBuilderService.metaDataRequest(id).execute();
        checkResponse(response, "Unable to download File, request failed");
        return gson.fromJson(response.body().string(), FileMetaDataModel.class);
    }

    private void checkResponse(Response response, String errorMsg) throws RequestServiceException {
        if (!response.isSuccessful()) {
            throw new RequestServiceException(errorMsg);
        }
    }
}
