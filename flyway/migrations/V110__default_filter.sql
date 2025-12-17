alter table meta.field_def add column default_ref_filter_values text[];
alter table meta.entity_def add column global_search_type text default 'DEFAULT';
