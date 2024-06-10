package com.angarium.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Diese Klasse repräsentiert die Metadaten einer hochgeladenen Datei.
 */
@Data
@RequiredArgsConstructor
public class FileMetaDataModel {

    /**
     * Eindeutige ID der Datei.
     */
    private UUID id;

    /**
     * Der Name der Datei (z.B. example.pdf).
     */
    private String name;

    /**
     * Die maximalen Downloads einer Datei
     */
    private int maxDownloads;

    /**
     * Die momentanen Downloads einer Datei
     */
    private int currentDownloads;

    /**
     * Der Zeitpunkt des Datei-Uploads
     */
    private LocalDate creationDate;

    /**
     * Zeitpunkt der Löschung der Datei
     */
    private LocalDate deletionDate;

    /**
     * TODO
     */
    private String sha256;

    /**
     * TODO
     */
    private boolean encrypted;

    /**
     * Der User der die Datei hochgeladen hat
     */
    private UserModel userModel;
}