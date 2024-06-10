package com.angarium.command;

import com.angarium.model.FileDownloadModel;
import com.angarium.model.FileMetaDataModel;
import com.angarium.service.RequestServiceException;
import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "download", mixinStandardHelpOptions = true, version = "0.1",
        description = "")
public class Download implements Callable<Integer> {
    @CommandLine.ParentCommand
    private AngariumCli parent;

    @CommandLine.Parameters(paramLabel = "ID", description = "The file id")
    String fileId;

    @Override
    public Integer call() throws IOException, RequestServiceException {
        FileMetaDataModel fileMetaDataModel = parent.requestService.fileMetaData(fileId);

        if (fileMetaDataModel.getCurrentDownloads() >= fileMetaDataModel.getMaxDownloads()){


        }
        FileDownloadModel fileDownloadModel = parent.requestService.download(fileId);
        System.out.println(fileDownloadModel);

        return 0;
    }
}
