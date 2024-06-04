package com.angarium.command;

import com.angarium.AngariumCli;
import com.angarium.Main;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "upload", mixinStandardHelpOptions = true, version = "0.1",
        description = "")
public class Upload implements Runnable {
    @CommandLine.ParentCommand
    private AngariumCli parent;

    @Override
    public void run() {

    }
}
