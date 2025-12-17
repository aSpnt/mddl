drop table meta.field_collection;
create table meta.field_collection
(
    field_value_id     uuid not null,
    entity_id        uuid not null,

    constraint field_collection_pk primary key (field_value_id, entity_id),
    constraint fk_field_collection_ref_field_value
        foreign key (field_value_id) references meta.field_value (id),
    constraint fk_field_collection_ref_entity
        foreign key (entity_id) references meta.entity (id)
);