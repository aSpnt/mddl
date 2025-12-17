CREATE UNIQUE INDEX field_value_field_def_unq
    ON meta.field_value (field_def_id, entity_id);