create index if not exists ref_value_collection_field_value_id_index
    on meta.ref_value_collection (field_value_id);

create index if not exists ref_value_collection_entity_id_index
    on meta.ref_value_collection (entity_id);

create index if not exists geometry_value_field_value_id_index
    on meta.geometry_value (field_value_id);

create index if not exists field_def_collection_ref_index
    on meta.field_def (collection_ref);

create index if not exists field_def_dictionary_external_ref_index
    on meta.field_def (dictionary_external_ref);

create index if not exists field_def_external_connection_id_index
    on meta.field_def (external_connection_id);

create index if not exists field_def_external_connection_batch_id_index
    on meta.field_def (external_connection_batch_id);

create index if not exists entity_def_mode_entity_def_id_index
    on meta.entity_def_mode (entity_def_id);
