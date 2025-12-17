alter table meta.field_def add column visible_header pg_catalog.bool default false;

alter table meta.field_def drop column for_slug;
