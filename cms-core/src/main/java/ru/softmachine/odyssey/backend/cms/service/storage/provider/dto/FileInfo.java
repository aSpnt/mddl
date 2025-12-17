package ru.softmachine.odyssey.backend.cms.service.storage.provider.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.InputStream;

@Data
@AllArgsConstructor
@Accessors(chain = true)
public class FileInfo {
    private InputStream stream;
    private long length;
    private String contentType;
}
