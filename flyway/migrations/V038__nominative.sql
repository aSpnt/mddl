alter table meta.entity_def add column nominative text;
alter table meta.entity_def add column genitive text;

alter table meta.field_def add column order_in_table boolean default false;
