package com.angarium.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.File;

/**
 * Dieses Modell repräsentiert die Daten, die zum Hochladen einer Datei benötigt werden.
 */
@Builder
@Data
@RequiredArgsConstructor
public class FileUploadModel {

    /**
     * Das Anmelde-Credential.
     */
    private final String credential;

    /**
     * Die Datei, die hochgeladen werden soll.
     */
    private final File file;

    /**
     * Der Name der Datei.
     */
    private final String filename;

    /**
     * Gibt an, ob die Datei verschlüsselt ist.
     */
    private final boolean encrypted;

    /**
     * Die maximale Anzahl von Tagen, die die Datei gespeichert wird.
     */
    private final int maxDays;

    /**
     * Die maximale Anzahl von Downloads, die für die Datei zulässig sind.
     */
    private final int maxDownloads;

    /**
     * Der SHA-256-Hash der Datei.
     */
    private final String sha256;
}
