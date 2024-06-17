package com.angarium.service;

import com.angarium.model.*;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Diese Klasse stellt Methoden zum Senden von HTTP-Anfragen und Verarbeiten von Antworten zur Verfügung.
 * Sie nutzt den RequestBuilderService für den Aufbau der Anfragen und Gson für die JSON-Verarbeitung.
 */
@RequiredArgsConstructor
public class RequestService {
    private final RequestBuilderService requestBuilderService;
    private final Gson gson;

    /**
     * Führt eine Login-Anfrage aus und gibt das Anmelde-Cookie zurück.
     *
     * @param username Der Benutzername.
     * @param password Das Passwort.
     * @return Das Anmelde-Cookie.
     * @throws IOException Wenn ein Fehler beim Senden der Anfrage auftritt.
     * @throws RequestServiceException Wenn die Antwort der Anfrage nicht erfolgreich ist.
     */
    public String login(String username, String password) throws IOException, RequestServiceException {
        Response response = requestBuilderService.loginRequest(username, password).execute();
        checkResponse(response, "Unable to Login, check Username or password");
        return response.header("set-cookie");
    }

    /**
     * Führt eine Anfrage zur Abfrage der aktuellen Benutzerinformationen aus.
     *
     * @param credential Das Anmelde-Credential.
     * @return Das Benutzer-Modell.
     * @throws IOException Wenn ein Fehler beim Senden der Anfrage auftritt.
     * @throws RequestServiceException Wenn die Antwort der Anfrage nicht erfolgreich ist.
     */
    public UserModel whoami(String credential) throws IOException, RequestServiceException {
        Response response = requestBuilderService.whoamiRequest(credential).execute();
        checkResponse(response, "Unable to call whoami, request failed");
        return gson.fromJson(response.body().string(), UserModel.class);
    }

    /**
     * Führt eine Datei-Upload-Anfrage aus.
     *
     * @param fileUploadModel Das Modell mit den Upload-Daten.
     * @return Das Modell mit der Datei-ID.
     * @throws IOException Wenn ein Fehler beim Senden der Anfrage auftritt.
     * @throws RequestServiceException Wenn die Antwort der Anfrage nicht erfolgreich ist.
     */
    public FileIdModel upload(FileUploadModel fileUploadModel) throws IOException, RequestServiceException {
        Response response = requestBuilderService.uploadRequest(fileUploadModel).execute();
        checkResponse(response, "Unable to upload File, request failed");
        return gson.fromJson(response.body().string(), FileIdModel.class);
    }

    /**
     * Führt eine Datei-Download-Anfrage aus.
     *
     * @param id Die ID der Datei.
     * @return Das Modell mit dem Dateinamen und dem Datei-Stream.
     * @throws IOException Wenn ein Fehler beim Senden der Anfrage auftritt.
     * @throws RequestServiceException Wenn die Antwort der Anfrage nicht erfolgreich ist.
     */
    public FileDownloadModel download(String id) throws IOException, RequestServiceException {
        Response response = requestBuilderService.downloadRequest(id).execute();
        checkResponse(response, "Unable to download File, request failed");

        return new FileDownloadModel(response.header("Content-Disposition").substring(20), response.body().byteStream());
    }

    /**
     * Führt eine Anfrage zur Abfrage der Dateimetadaten aus.
     *
     * @param id Die ID der Datei.
     * @return Das Modell mit den Dateimetadaten.
     * @throws IOException Wenn ein Fehler beim Senden der Anfrage auftritt.
     * @throws RequestServiceException Wenn die Antwort der Anfrage nicht erfolgreich ist.
     */
    public FileMetaDataModel fileMetaData(String id) throws IOException, RequestServiceException {
        Response response = requestBuilderService.metaDataRequest(id).execute();
        checkResponse(response, "Unable to download File, request failed");
        return gson.fromJson(response.body().string(), FileMetaDataModel.class);
    }

    /**
     * Überprüft die Antwort der Anfrage auf ihren Erfolg und wirft eine Ausnahme bei Fehlern.
     *
     * @param response Die Antwort der Anfrage.
     * @param errorMsg Die Fehlermeldung, die im Falle eines Fehlers geworfen wird.
     * @throws RequestServiceException Wenn die Antwort der Anfrage nicht erfolgreich ist.
     */
    private void checkResponse(Response response, String errorMsg) throws RequestServiceException {
        if (!response.isSuccessful()) {
            throw new RequestServiceException(errorMsg);
        }
    }
}
