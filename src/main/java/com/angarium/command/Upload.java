package com.angarium.command;

import com.angarium.model.FileIdModel;
import com.angarium.model.FileUploadModel;
import com.angarium.service.RequestServiceException;
import lombok.CustomLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.pgpainless.sop.SOPImpl;
import picocli.CommandLine;
import sop.SOP;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;

@Slf4j
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

    SOP sop =  new SOPImpl();

    @Override
    public Integer call() throws IOException, RequestServiceException {
        String credential = login();
        File file = new File(filePath);

        if (!(file.exists() && file.canRead() && file.isFile())) {
            throw new IOException("No valid file provided, check if it exists and is readable");
        }

        String hash = DigestUtils.sha256Hex(FileUtils.openInputStream(file));

        File processedFile = file;
        if(!StringUtils.isEmpty(password)) {
            processedFile = encryptFile(password, file);
        }

        FileUploadModel fileUploadModel = FileUploadModel.builder()
                .credential(credential)
                .filename(filename == null ? file.getName() : filename)
                .file(processedFile)
                .maxDownloads(maxDownloads)
                .maxDays(maxDays)
                .encrypted(!StringUtils.isEmpty(password))
                .sha256(hash).build();

        FileIdModel fileIdModel = parent.requestService.upload(fileUploadModel);

        System.out.println("Upload successful");
        System.out.println("File ID: " + fileIdModel.getFileId());
        return 0;
    }


    private String login() throws RequestServiceException, IOException {
        String username = parent.config.getString("username");
        String password = parent.config.getString("password");

        return parent.requestService.login(username, password);
    }

    private File encryptFile(String password, File file) throws IOException {
        File encryptedFile = File.createTempFile("angarium", "enc");
        sop.encrypt()
                .withPassword(password)
                .plaintext(FileUtils.openInputStream(file))
                .writeTo(FileUtils.openOutputStream(encryptedFile));
        return encryptedFile;
    }
}
