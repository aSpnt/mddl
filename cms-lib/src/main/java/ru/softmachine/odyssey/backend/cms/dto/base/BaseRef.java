package ru.softmachine.odyssey.backend.cms.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * Базовая ссылка для сущностей
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseRef {

    private UUID id;
    private String name;
}
