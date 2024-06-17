package com.angarium.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Dieses Modell repräsentiert die ID einer Datei.
 */
@RequiredArgsConstructor
@Data
public class FileIdModel {

    /**
     * Die ID der Datei.
     */
    private final String fileId;
}
