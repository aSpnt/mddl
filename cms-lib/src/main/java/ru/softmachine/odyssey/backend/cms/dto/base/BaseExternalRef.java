package ru.softmachine.odyssey.backend.cms.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Базовая ссылка для справочников
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BaseExternalRef {

    private String id;
    private String name;
    private int seq = 0;
    private String description;
    private String img;
    private String ref;
}
