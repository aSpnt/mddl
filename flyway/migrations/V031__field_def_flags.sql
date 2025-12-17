alter table meta.field_def
    add column required boolean default false;
alter table meta.field_def
    add column can_change_order boolean default false;
