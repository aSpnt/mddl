create table meta.dictionary_external_header
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,

    name text not null,
    value text,
    dictionary_external_id uuid not null,

    constraint dictionary_external_header_id_pk primary key (id),
    constraint fk_dictionary_external_header_dictionary_external
        foreign key(dictionary_external_id) references meta.dictionary_external(id)
);
