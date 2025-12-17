create index if not exists field_value_entity_id_index
    on meta.field_value (entity_id);

create index if not exists field_value_ref_value_index
    on meta.field_value (ref_value);

create index if not exists field_value_entity_value_index
    on meta.field_value (entity_value);
