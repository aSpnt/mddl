create table meta.entity_comment
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,

    title text not null,
    message text not null,

    entity_id uuid not null,

    constraint entity_comment_id_pk primary key (id),
    constraint fk_entity_comment_entity
        foreign key(entity_id) references meta.entity(id)
);

alter table meta.field_value_transition add column need_comment boolean default false;
alter table meta.field_value_transition add column field_codes text[];
alter table meta.field_value_transition add column comment_note text;
