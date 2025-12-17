alter table meta.entity add column slug_lock boolean default false;

alter table meta.field_def add column for_slug boolean default false;
