alter table meta.field_value
    add column entity_value uuid;
alter table meta.field_value add constraint fk_field_value_entity_value_entity_ref
    foreign key(entity_value) references meta.entity(id);
