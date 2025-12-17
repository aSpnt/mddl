alter table meta.field_def add column seq int default 0;
alter table meta.field_def add column visible_short pg_catalog.bool default false;

create table meta.field_collection
(
    field_def_id     uuid not null,
    entity_parent_id uuid not null,
    entity_id        uuid not null,

    constraint field_collection_pk primary key (field_def_id, entity_parent_id, entity_id),
    constraint fk_field_collection_ref_entity_parent
        foreign key (entity_parent_id) references meta.entity (id),
    constraint fk_field_collection_ref_field_value
        foreign key (entity_id) references meta.entity (id)
);

create table meta.entity_def_group
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,
    name text not null, -- отображаемое название
    code text not null, -- код привязки

    constraint entity_def_group_id_pk primary key (id)
);

alter table meta.entity_def add column entity_def_group_id uuid;
alter table meta.entity_def add constraint fk_entity_def_ref_entity_def_group
    foreign key (entity_def_group_id) references meta.entity_def_group (id);