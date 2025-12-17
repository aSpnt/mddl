create table meta.field_validation
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,

    type text not null,
    text_value text,
    int_value text,
    double_value float,
    field_def_id uuid not null,

    constraint field_validation_id_pk primary key (id),
    constraint fk_field_validation_field_def
        foreign key(field_def_id) references meta.field_def(id)
);

