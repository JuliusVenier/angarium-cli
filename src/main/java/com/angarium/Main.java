package com.angarium;

import com.angarium.command.AngariumCli;
import com.angarium.service.ConfigService;
import com.angarium.service.RequestBuilderService;
import com.angarium.service.RequestService;
import com.google.gson.Gson;
import com.typesafe.config.Config;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import picocli.CommandLine;

import java.io.IOException;

/**
 * Hauptklasse für die Angarium CLI-Anwendung.
 * Diese Klasse initialisiert die notwendigen Dienste und startet die Kommandozeilen-Anwendung.
 */
@Slf4j
public class Main{

    /**
     * Der Einstiegspunkt der Anwendung.
     * Liest die Konfiguration, initialisiert die Dienste und führt die CLI-Befehle aus.
     *
     * @param args die Befehlszeilenargumente
     * @throws IOException wenn beim Lesen der Konfiguration ein Fehler auftritt
     */
    public static void main(String[] args) throws IOException {
        ConfigService configService = new ConfigService();
        Config config = configService.read();

        RequestBuilderService requestBuilderService = new RequestBuilderService(
                config.getString("host"),
                config.getInt("port"),
                config.getString("scheme"),
                new OkHttpClient());

        int exitCode = new CommandLine(new AngariumCli(
                config,
                new RequestService(requestBuilderService, new Gson())))
                .setExecutionExceptionHandler(new PrintExceptionMessageHandler())
                .execute(args);
        System.exit(exitCode);
    }
}