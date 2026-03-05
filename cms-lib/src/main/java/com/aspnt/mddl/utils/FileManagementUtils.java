package com.aspnt.mddl.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

@Slf4j
@Component
public class FileManagementUtils {

    private static final String TAG = "cms_uuid_";

    public File getFile() {
        var tempDir = FileUtils.getTempDirectoryPath();
        var uniqueId = UUID.randomUUID().toString();
        return new File(tempDir + File.separator + TAG + uniqueId);
    }

    public File createFileFromStream(InputStream in, File tmpFile) {
        try {
            FileUtils.copyToFile(in, tmpFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tmpFile;
    }

    public void removeFile(File file) {
        if (file == null) {
            return;
        }
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            log.warn(null, e);
        }
    }
}
