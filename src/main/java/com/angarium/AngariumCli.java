package com.angarium;

import com.angarium.command.Download;
import com.angarium.command.Upload;
import picocli.CommandLine;

@CommandLine.Command(name = "angarium-cli", mixinStandardHelpOptions = true,
        description = "", subcommands = {Upload.class, Download.class} )
public class AngariumCli  implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello World");
    }
}
