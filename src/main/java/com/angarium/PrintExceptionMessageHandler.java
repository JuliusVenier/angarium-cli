package com.angarium;

import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
public class PrintExceptionMessageHandler implements CommandLine.IExecutionExceptionHandler {
    @Override
    public int handleExecutionException(Exception e, CommandLine commandLine, CommandLine.ParseResult parseResult) throws Exception {
        log.error(e.getMessage());

        return commandLine.getExitCodeExceptionMapper() != null
                ? commandLine.getExitCodeExceptionMapper().getExitCode(e)
                : commandLine.getCommandSpec().exitCodeOnExecutionException();
    }
}
