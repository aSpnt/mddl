alter table meta.entity_def
    drop column default_page_filter;

alter table meta.entity_def
    add column page_filter_name text;

alter table meta.entity_def
    add column page_filter_size_name text;

alter table meta.entity_def
    add column page_filter_number_name text;
