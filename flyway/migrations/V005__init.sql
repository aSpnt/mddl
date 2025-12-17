create table meta.field_def_container
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,
    name text not null, -- отображаемое название
    code text not null, -- код привязки
    type text not null, -- тип контейнера

    parent_id        uuid, -- ссылка на родительский контейнер

    constraint field_def_container_id_pk primary key (id),
    constraint fk_field_def_container_field_def_container
        foreign key(parent_id) references meta.field_def_container(id)
);

alter table meta.entity_def add column container_id uuid;
alter table meta.entity_def add constraint fk_entity_def_field_def_container
        foreign key(container_id) references meta.field_def_container(id);

alter table meta.field_def add column container_id uuid;
alter table meta.field_def drop column entity_def_id;