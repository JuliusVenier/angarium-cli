package com.angarium;

import com.angarium.command.Upload;
import picocli.CommandLine;

import java.util.concurrent.Callable;

public class Main{
    public static void main(String[] args) {
        int exitCode = new CommandLine(new AngariumCli()).execute(args);
        System.exit(exitCode);
    }
}
