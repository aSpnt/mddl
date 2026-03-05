package com.aspnt.mddl.entity.field;


import com.aspnt.mddl.dto.field.FieldDefType;

import java.util.UUID;

public interface FieldDefIdWithTypeProj {
    UUID getId();
    FieldDefType getType();
}
