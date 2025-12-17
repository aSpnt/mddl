alter table meta.entity_def add column success_create_message text;
alter table meta.entity add column slug text;

alter table meta.field_def add column multiple boolean default false;
