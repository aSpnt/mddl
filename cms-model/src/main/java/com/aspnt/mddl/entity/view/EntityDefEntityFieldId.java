package com.aspnt.mddl.entity.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityDefEntityFieldId implements Serializable {

    private UUID entityDefId;

    private UUID fieldDefId;
}
