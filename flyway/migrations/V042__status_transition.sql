create table meta.field_value_transition
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp,

    -- ссылка на определение поля статуса
    field_def_id    uuid not null,
    -- значение статуса из которого допустим переход
    ref_from_id     uuid not null,
    -- значение статуса в которое допустим переход
    ref_to_id       uuid not null,

    constraint field_value_transition_pk primary key (id),
    constraint fk_field_value_transition_ref_field_def
        foreign key (field_def_id) references meta.field_def (id),
    constraint fk_field_value_transition_from_ref_entity
        foreign key (ref_from_id) references meta.entity (id),
    constraint fk_field_value_transition_to_ref_entity
        foreign key (ref_to_id) references meta.entity (id)
);
