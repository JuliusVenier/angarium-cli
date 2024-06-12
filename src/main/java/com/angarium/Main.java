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

@Slf4j
public class Main{
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