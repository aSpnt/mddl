alter table meta.field_value add column allow_templates_override text[];

alter table meta.entity_def
    add column default_page_filter jsonb;
