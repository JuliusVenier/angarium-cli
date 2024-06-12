package com.angarium.command;

import com.angarium.model.FileDownloadModel;
import com.angarium.model.FileMetaDataModel;
import com.angarium.service.RequestServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.pgpainless.sop.SOPImpl;
import picocli.CommandLine;
import sop.SOP;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "download", mixinStandardHelpOptions = true, version = "0.1",
        description = "")
@Slf4j
public class Download implements Callable<Integer> {
    @CommandLine.ParentCommand
    private AngariumCli parent;

    @CommandLine.Parameters(paramLabel = "ID", description = "The file id")
    String fileId;

    @CommandLine.Option(names = "-p", description = "A password for decrypting the file")
    String password;

    SOP sop =  new SOPImpl();

    @Override
    public Integer call() throws IOException, RequestServiceException {
        FileMetaDataModel fileMetaDataModel = parent.requestService.fileMetaData(fileId);
        log.info("The file was found");
        log.info("File ID:  {}", fileMetaDataModel.getId());
        log.info("Filename: {}", fileMetaDataModel.getName());
        log.info("SHA256:   {}", fileMetaDataModel.getSha256());

        if (fileMetaDataModel.getCurrentDownloads() >= fileMetaDataModel.getMaxDownloads()){
            log.error("File reached it's download limit");
            return 1;

        }

        File processedFile = new File(fileMetaDataModel.getName());
        if(processedFile.exists()){
            log.error("A file with the same name already exist");
            return 1;
        }

        log.info("Downloading file");
        FileDownloadModel fileDownloadModel = parent.requestService.download(fileId);

        if (fileMetaDataModel.isEncrypted()) {
            if(StringUtils.isBlank(password)){
                log.error("The file is encrypted, but no password has been entered");
                return 1;
            }
            log.info("Decrypting File");
            decryptFile(processedFile, fileDownloadModel.getFileStream(), password);
        } else {
            FileUtils.copyInputStreamToFile(fileDownloadModel.getFileStream(), processedFile);
        }

        if(!DigestUtils.sha256Hex(FileUtils.openInputStream(processedFile)).equals(fileMetaDataModel.getSha256())) {
           log.error("The hashes do not match, try again");
           processedFile.delete();
           return 1;
        }

        log.info("The file was saved successfully");

        return 0;
    }

    private void decryptFile(File processedFile, InputStream fileStream, String password) throws IOException {
        sop.decrypt()
                .withPassword(password)
                .ciphertext(fileStream)
                .writeTo(FileUtils.openOutputStream(processedFile));
    }
}
