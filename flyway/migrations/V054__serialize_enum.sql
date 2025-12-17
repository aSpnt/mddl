alter table meta.entity_def
    add column url_list text;

alter table meta.field_def
    add column serialize_enum boolean default false;
