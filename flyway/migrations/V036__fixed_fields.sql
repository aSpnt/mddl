alter table meta.entity_def
    add column id_field_def_id uuid;
alter table meta.entity_def
    add column created_ts_field_def_id uuid;
alter table meta.entity_def
    add column updated_ts_field_def_id uuid;
