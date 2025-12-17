package ru.softmachine.odyssey.backend.cms.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * Базовый DTO для сущностей
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseStringDto extends StringIdentDto {

    private ZonedDateTime createdTs;
    private ZonedDateTime updatedTs;
}
