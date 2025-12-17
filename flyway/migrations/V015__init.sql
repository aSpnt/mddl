create table meta.dictionary_external_value
(
    id text not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,

    name text not null, -- отображаемое название
    field_value_id uuid not null,

    constraint dictionary_external_value_id_pk primary key (id),
    constraint fk_dictionary_external_value_field_value
        foreign key(field_value_id) references meta.field_value(id)
);