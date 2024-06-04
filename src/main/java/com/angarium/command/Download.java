package com.angarium.command;

import picocli.CommandLine;

@CommandLine.Command(name = "download", mixinStandardHelpOptions = true, version = "0.1",
        description = "")
public class Download implements Runnable {
    @CommandLine.ParentCommand
    private AngariumCli parent;

    @Override
    public void run() {

    }
}
