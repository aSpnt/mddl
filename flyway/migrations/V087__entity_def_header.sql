create table meta.entity_def_external_header
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,

    name text not null,
    value text,
    entity_def_external_id uuid not null,

    constraint entity_def_external_header_id_pk primary key (id),
    constraint fk_entity_def_external_header_entity_def
        foreign key(entity_def_external_id) references meta.entity_def(id)
);
