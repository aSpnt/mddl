alter table meta.field_def add column span int default 24;

alter table meta.field_def add column default_text_value text null;

alter table meta.field_def add column default_int_value bigint null;

alter table meta.field_def add column default_double_value float null;