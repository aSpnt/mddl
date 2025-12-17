alter table meta.field_value
    add column visible_override boolean;

alter table meta.field_value
    add column disabled_override boolean;

alter table meta.field_value
    add column seq_override boolean;

create table meta.entity_template
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,
    name text not null, -- отображаемое название шаблона
    code text not null, -- код шаблона

    constraint entity_template_id_pk primary key (id)
);

alter table meta.field_value alter column entity_id drop not null;

alter table meta.field_value add column entity_template_id uuid;

alter table meta.field_value add constraint fk_field_value_ref_entity_template
    foreign key (entity_template_id) references meta.entity_template (id);

-- что-то одно должно быть заполнено
alter table meta.field_value add constraint chk_entity_entity_template_ref
    check (entity_template_id is not null or entity_id is not null);
