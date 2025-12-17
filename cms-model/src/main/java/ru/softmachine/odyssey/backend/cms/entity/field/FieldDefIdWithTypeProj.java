package ru.softmachine.odyssey.backend.cms.entity.field;


import ru.softmachine.odyssey.backend.cms.dto.field.FieldDefType;

import java.util.UUID;

public interface FieldDefIdWithTypeProj {
    UUID getId();
    FieldDefType getType();
}
