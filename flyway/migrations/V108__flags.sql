alter table meta.field_def add column hide_on_create boolean default false;
alter table meta.field_def add column lock_delete boolean default false;
alter table meta.field_value add column lock_delete_override boolean default false;
