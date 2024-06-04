package com.angarium;

import com.angarium.command.AngariumCli;
import com.angarium.service.ConfigService;
import com.typesafe.config.Config;
import picocli.CommandLine;

import java.io.IOException;

public class Main{
    public static void main(String[] args) throws IOException {
        ConfigService configService = new ConfigService();
        Config config = configService.read();

        int exitCode = new CommandLine(new AngariumCli(config)).execute(args);
        System.exit(exitCode);
    }
}