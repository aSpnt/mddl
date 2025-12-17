alter table meta.field_def
    add column disable_condition text;

alter table meta.field_def
    add column disabled boolean default false;
