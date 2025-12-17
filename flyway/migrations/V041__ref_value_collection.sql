create table meta.ref_value_collection
(
    field_value_id     uuid not null,
    entity_id        uuid not null,

    constraint ref_value_collection_pk primary key (field_value_id, entity_id),
    constraint fk_ref_value_collection_ref_field_value
        foreign key (field_value_id) references meta.field_value (id),
    constraint fk_ref_value_collection_ref_entity
        foreign key (entity_id) references meta.entity (id)
);
