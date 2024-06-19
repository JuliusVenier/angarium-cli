package com.angarium.command;

import com.angarium.service.RequestService;
import com.typesafe.config.Config;
import lombok.RequiredArgsConstructor;
import picocli.CommandLine;

/**
 * Hauptkommandozeilen-Interface (CLI) für Angarium.
 * Diese Klasse definiert die CLI-Anwendung und ihre Unterbefehle.
 */
@CommandLine.Command(name = "angarium-cli", mixinStandardHelpOptions = true, description = "", subcommands = {Upload.class, Download.class})
@RequiredArgsConstructor
public class AngariumCli  implements Runnable {
    final Config config;
    final RequestService requestService;

    /**
     * Hauptmethode, die beim Ausführen des Hauptbefehls aufgerufen wird.
     * Gibt eine einfache Begrüßungsnachricht aus.
     */
    @Override
    public void run() {
        System.out.println("Hello World");
    }
}
