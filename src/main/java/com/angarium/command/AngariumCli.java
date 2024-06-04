package com.angarium.command;

import com.typesafe.config.Config;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;

@CommandLine.Command(name = "angarium-cli", mixinStandardHelpOptions = true,
        description = "", subcommands = {Upload.class, Download.class} )
@RequiredArgsConstructor
public class AngariumCli  implements Runnable {
    final Config config;

    @Override
    public void run() {
        System.out.println("Hello World");
    }
}
