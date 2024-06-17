package com.angarium.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

/**
 * Dieses Modell repräsentiert die heruntergeladene Datei mit ihrem Namen und dem InputStream.
 */
@RequiredArgsConstructor
@Data
public class FileDownloadModel {

    /**
     * Der Name der heruntergeladenen Datei.
     */
    public final String filename;

    /**
     * Der InputStream der heruntergeladenen Datei.
     */
    public final InputStream fileStream;
}
