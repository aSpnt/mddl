package ru.softmachine.odyssey.backend.cms.service.storage.provider;


import ru.softmachine.odyssey.backend.cms.service.storage.provider.dto.FileInfo;

import java.io.InputStream;

/**
 * Контракт компонента, обеспечивающего хранение файлов (картинок, документов и тд).
 * Вероятно, добавится доступ по идентификатору, а не только проверка.
 */
public interface UploadProvider {

    boolean exists(String id);

    FileInfo download(String id);

    void upload(String id, InputStream inputStream, long length, String contentType);

    void deleteImage(String id) ;
}
