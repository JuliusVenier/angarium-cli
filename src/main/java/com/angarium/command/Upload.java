package com.angarium.command;

import com.angarium.model.FileUploadModel;
import com.angarium.model.UserModel;
import com.angarium.service.RequestServiceException;
import okhttp3.Call;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "upload", mixinStandardHelpOptions = true, version = "0.1", description = "")
public class Upload implements Callable<Integer> {

    @CommandLine.ParentCommand
    private AngariumCli parent;

    @CommandLine.Option(names = "-d", description = "Max Days", defaultValue = "2")
    int maxDays = 2;

    @CommandLine.Option(names = "-D", description = "Max Downloads", defaultValue = "10")
    int maxDownloads = 10;

    @CommandLine.Option(names = "-p", description = "A password for the encryption of the file")
    String password;

    @CommandLine.Option(names = "-f", description = "The filename")
    String filename;

    @CommandLine.Parameters(paramLabel = "FILE-PATH", description = "The file to be uploaded")
    String filePath;

    @CommandLine.Spec
    CommandLine.Model.CommandSpec commandSpec;

    @Override
    public Integer call() throws IOException, RequestServiceException {
        String credential = login();
        File file = new File(filePath);

        if (!(file.exists() && file.canRead() && file.isFile())) {
            throw new IOException("No valid file provided, check if it exists and is readable");
        }

        if(StringUtils.isEmpty(filename)){
            filename = file.getName();
        }

        if(!StringUtils.isEmpty(password)) {

        }

        FileUploadModel fileUploadModel = FileUploadModel.builder()
                .credential(credential)
                .filename(filename)
                .file(file)
                .maxDownloads(maxDownloads)
                .maxDays(maxDays)
                .encrypted(false)
                .sha256("").build();

        parent.requestService.upload(fileUploadModel);

        return 0;
    }


    private String login() throws RequestServiceException, IOException {
        String username = parent.config.getString("username");
        String password = parent.config.getString("password");

        return parent.requestService.login("default", "password");
    }
}
