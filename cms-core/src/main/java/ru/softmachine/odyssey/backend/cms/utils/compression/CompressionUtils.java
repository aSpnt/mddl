package ru.softmachine.odyssey.backend.cms.utils.compression;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public interface CompressionUtils {

    void compressImage(InputStream in, OutputStream out);

    void compressVideo(File in, File out, String currentVideoFormat);
}

