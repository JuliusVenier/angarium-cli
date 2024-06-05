package com.angarium.command;

import com.angarium.service.RequestBuilderService;
import com.angarium.service.RequestService;
import com.typesafe.config.Config;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;

@CommandLine.Command(name = "angarium-cli", mixinStandardHelpOptions = true, description = "", subcommands = {Upload.class})
@RequiredArgsConstructor
public class AngariumCli  implements Runnable {
    final Config config;
    final RequestService requestService;

    @Override
    public void run() {
        System.out.println("Hello World");
    }
}
