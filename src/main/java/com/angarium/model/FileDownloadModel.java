package com.angarium.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@RequiredArgsConstructor
@Data
public class FileDownloadModel {
    public final String filename;
    public final InputStream fileStream;
}
