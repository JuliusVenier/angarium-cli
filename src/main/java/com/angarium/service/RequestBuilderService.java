package com.angarium.service;

import com.angarium.model.FileUploadModel;
import lombok.RequiredArgsConstructor;
import okhttp3.*;

/**
 * Diese Klasse stellt Methoden zum Erstellen von HTTP-Anfragen bereit.
 * Sie verwendet OkHttpClient für den Aufbau der Anfragen und das Senden der HTTP-Requests.
 */
@RequiredArgsConstructor
public class RequestBuilderService {

    private final String host;
    private final int port;
    private final String scheme;
    private final OkHttpClient client;

    /**
     * Erstellt eine Login-Anfrage.
     *
     * @param username Der Benutzername.
     * @param password Das Passwort.
     * @return Der Call für die Login-Anfrage.
     */
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

    /**
     * Erstellt eine Anfrage zur Abfrage der aktuellen Benutzerinformationen.
     *
     * @param credential Das Anmelde-Credential.
     * @return Der Call für die whoami-Anfrage.
     */
    public Call whoamiRequest(String credential){
        return client.newCall(
                new Request.Builder()
                        .addHeader("Cookie", credential)
                        .url(basicUrl().addEncodedPathSegments("api/user/whoami").build())
                        .build());
    }

    /**
     * Erstellt eine Datei-Upload-Anfrage.
     *
     * @param fileUploadModel Das Modell mit den Upload-Daten.
     * @return Der Call für die Datei-Upload-Anfrage.
     */
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

    /**
     * Erstellt eine Anfrage zur Abfrage der Dateimetadaten.
     *
     * @param id Die ID der Datei.
     * @return Der Call für die Anfrage der Dateimetadaten.
     */
    public  Call metaDataRequest(String id) {
        return client.newCall(
                new Request.Builder()
                        .url(basicUrl().addPathSegments("api/meta-data/" + id).build())
                        .build()
        );
    }

    /**
     * Erstellt eine Datei-Download-Anfrage.
     *
     * @param id Die ID der Datei.
     * @return Der Call für die Datei-Download-Anfrage.
     */
    public Call downloadRequest(String id) {
        return client.newCall(
                new Request.Builder()
                        .url(basicUrl().addPathSegments("api/download/" + id).build())
                        .build()
        );
    }

    /**
     * Erstellt eine Basis-URL für die Anfragen.
     *
     * @return Der Builder für die Basis-URL.
     */
    private HttpUrl.Builder basicUrl(){
        return new HttpUrl.Builder().scheme(scheme).host(host).port(port);
    }
}
