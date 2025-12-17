alter table meta.entity_def
    add column url text;
alter table meta.entity_def
    add column method text;
alter table meta.entity_def
    add column response_param text;
alter table meta.entity_def
    add column default_body jsonb;
