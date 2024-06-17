package com.angarium;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

/**
 * Diese Klasse behandelt Ausnahmen, die während der Ausführung eines picocli-Kommandos auftreten.
 * Sie protokolliert die Fehlermeldungen und gibt einen entsprechenden Exit-Code zurück.
 */
@Slf4j
public class PrintExceptionMessageHandler implements CommandLine.IExecutionExceptionHandler {

    /**
     * Behandelt eine während der Ausführung aufgetretene Ausnahme.
     *
     * @param e Die aufgetretene Ausnahme.
     * @param commandLine Die CommandLine-Instanz.
     * @param parseResult Das Ergebnis des Parsing-Vorgangs.
     * @return Der Exit-Code, der an das Betriebssystem zurückgegeben werden soll.
     * @throws Exception Wenn eine Ausnahme während der Ausnahmebehandlung auftritt.
     */
    @Override
    public int handleExecutionException(Exception e, CommandLine commandLine, CommandLine.ParseResult parseResult) throws Exception {
        log.error(e.getMessage());

        return commandLine.getExitCodeExceptionMapper() != null
                ? commandLine.getExitCodeExceptionMapper().getExitCode(e)
                : commandLine.getCommandSpec().exitCodeOnExecutionException();
    }
}
