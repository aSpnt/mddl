alter table meta.field_def
    alter column updated_ts drop not null;

alter table meta.field_def_container
    alter column updated_ts drop not null;

alter table meta.entity
    alter column updated_ts drop not null;

alter table meta.entity_def
    alter column updated_ts drop not null;

alter table meta.field_value
    alter column updated_ts drop not null;