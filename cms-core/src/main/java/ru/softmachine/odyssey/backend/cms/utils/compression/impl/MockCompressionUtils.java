package ru.softmachine.odyssey.backend.cms.utils.compression.impl;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import ru.softmachine.odyssey.backend.cms.utils.compression.CompressionUtils;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Для сборки без включения библиотек сжатия
 */
@Component
public class MockCompressionUtils implements CompressionUtils {

    @SneakyThrows
    public void compressImage(InputStream in, OutputStream out) {
        IOUtils.copy(in, out);
    }

    @SneakyThrows
    public void compressVideo(File in, File out, String currentVideoFormat) {
        IOUtils.copy(in.toURI().toURL(), out);
    }
}

