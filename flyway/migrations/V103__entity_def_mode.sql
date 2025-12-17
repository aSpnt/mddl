create table meta.entity_def_mode
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,

    name text not null,
    code text not null,

    entity_def_id uuid not null,

    constraint entity_def_mode_id_pk primary key (id),
    constraint fk_entity_def_mode_entity_def
        foreign key(entity_def_id) references meta.entity_def(id)
);

alter table meta.field_def add column entity_def_mode_id uuid;
alter table meta.field_def add constraint fk_field_def_ref_entity_def_mode
    foreign key(entity_def_mode_id) references meta.entity_def_mode(id);
