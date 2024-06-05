package com.angarium.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.File;

@Builder
@Data
@RequiredArgsConstructor
public class FileUploadModel {
    private final String credential;
    private final File file;
    private final String filename;
    private final boolean encrypted;
    private final int maxDays;
    private final int maxDownloads;
    private final String sha256;
}
