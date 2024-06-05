package com.angarium.service;

import com.angarium.model.FileUploadModel;
import com.angarium.model.UserModel;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;

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
        checkResponse(response, "Unable to call whoami");
        return gson.fromJson(response.body().string(), UserModel.class);
    }

    public void upload(FileUploadModel fileUploadModel) throws IOException, RequestServiceException {
        Response response = requestBuilderService.uploadRequest(fileUploadModel).execute();
        checkResponse(response, "Unable to upload File, request failed");
    }

    private void checkResponse(Response response, String errorMsg) throws RequestServiceException {
        if (!response.isSuccessful()) {
            throw new RequestServiceException(errorMsg);
        }
    }
}
