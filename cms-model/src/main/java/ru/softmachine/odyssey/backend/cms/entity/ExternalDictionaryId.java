package ru.softmachine.odyssey.backend.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalDictionaryId implements Serializable {

    private String id;

    private FieldValue fieldValue;

    private Integer seq; // часть ключа, так как может быть например несколько городов в рамках одной коллекции
}
