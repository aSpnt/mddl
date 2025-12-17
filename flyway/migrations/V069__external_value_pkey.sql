alter table meta.dictionary_external_value
    drop constraint dictionary_external_value_pkey;

alter table meta.dictionary_external_value
    add primary key (id, field_value_id, seq);
