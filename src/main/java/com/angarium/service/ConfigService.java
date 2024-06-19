package com.angarium.service;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Dienstklasse zur Verwaltung der Konfiguration von Angarium.
 * Diese Klasse liest die Konfiguration aus einer JSON-Datei im Benutzerverzeichnis.
 */
public class ConfigService {


    public static final String ANGARIUM_CONF_NAME = "angarium.json";
    public static final String DEFAULT_CONF =
"""
{
    "username": "",
    "password": "",
    "host": "127.0.0.1",
    "port": "8080",
    "scheme": "http"
}
""";

    /**
     * Liest die Konfigurationsdatei aus dem Benutzerverzeichnis.
     * Wenn die Datei nicht existiert, wird sie mit der Standardkonfiguration erstellt.
     *
     * @return die gelesene Konfiguration
     * @throws IOException wenn beim Lesen oder Schreiben der Datei ein Fehler auftritt
     */
    public Config read() throws IOException {
        File homeConfig = new File(Paths.get(System.getProperty("user.home"), ANGARIUM_CONF_NAME).toUri());
        if(!homeConfig.exists()) {
            homeConfig.createNewFile();
            Files.writeString(homeConfig.toPath(), DEFAULT_CONF);
        }
        return ConfigFactory.load(ConfigFactory.parseFile(homeConfig));
    }
}
