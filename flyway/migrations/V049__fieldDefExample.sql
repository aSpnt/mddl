alter table meta.field_def
    add column example_text text;

alter table meta.field_def
    add column default_ref_value uuid;
alter table meta.field_def add constraint fk_field_def_ref_value_entity_ref
        foreign key(default_ref_value) references meta.entity(id);

alter table meta.field_def
    add column default_boolean_value boolean;

alter table meta.field_value
    add column boolean_value boolean;

alter table meta.field_value
    add column array_date date[];

alter table meta.field_value
    add column array_datetime timestamptz[];
