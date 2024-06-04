package com.angarium.command;

import com.angarium.RequestBuilderService;
import okhttp3.OkHttpClient;
import picocli.CommandLine;

import java.io.File;

@CommandLine.Command(name = "upload", mixinStandardHelpOptions = true, version = "0.1",
        description = "")
public class Upload implements Runnable {

    private final OkHttpClient client = new OkHttpClient();

    @CommandLine.ParentCommand
    private AngariumCli parent;

    @CommandLine.Parameters(paramLabel = "FILE", description = "The file to be uploaded")
    File file;

    private final RequestBuilderService requestBuilderService = new RequestBuilderService(parent.)

    @Override
    public void run() {

    }
}
