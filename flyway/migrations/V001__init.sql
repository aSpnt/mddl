create schema if not exists meta;

drop table if exists meta.entity_def cascade;
create table meta.entity_def
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,
    name text not null, -- отображаемое название
    code text not null, -- код привязки

    constraint entity_def_id_pk primary key (id)
);

drop table if exists meta.field_def cascade;
create table meta.field_def
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,
    name text not null, -- отображаемое название
    code text not null, -- код привязки
    type text not null, -- тип
    collection_ref text null, -- код entity дочерней коллекции
    entity_def_id uuid not null,

    constraint field_def_id_pk primary key (id),
    constraint fk_field_def_entity_def
        foreign key(entity_def_id) references meta.entity_def(id)
);

drop table if exists meta.entity cascade;
create table meta.entity
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,
    entity_def_id uuid not null,

    constraint entity_id_pk primary key (id),
    constraint fk_entity_entity_def
        foreign key(entity_def_id) references meta.entity_def(id)
);

drop table if exists meta.field_value cascade;
create table meta.field_value
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,
    field_def_id uuid not null,
    double_value float,
    int_value bigint,
    text_value text,
    date_value date,
    ref_value uuid,
    entity_id uuid not null,

    constraint field_value_id_pk primary key (id),
    constraint fk_field_value_field_def
        foreign key(field_def_id) references meta.field_def(id),
    constraint fk_field_value_entity
        foreign key(entity_id) references meta.entity(id),
    constraint fk_field_value_entity_ref
        foreign key(ref_value) references meta.entity(id)
);

drop table if exists meta.entity_field_value_ref cascade;
create table meta.entity_field_value_ref
(
    entity_id uuid not null,
    field_value_id uuid not null,

    constraint entity_field_value_ref_pk primary key (entity_id, field_value_id),
    constraint fk_entity_field_value_ref_entity
        foreign key(entity_id) references meta.entity(id),
    constraint fk_entity_field_value_ref_field_value
        foreign key(field_value_id) references meta.field_value(id)
);

CREATE UNIQUE INDEX entity_def_code_unq
    ON meta.entity_def (code);

alter table meta.field_def add column visible_table boolean default true;
alter table meta.field_def add column visible_form boolean default true;

