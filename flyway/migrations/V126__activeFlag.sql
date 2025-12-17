-- указывается статус который необходимо проставить сущности после перехода по transition
alter table meta.field_value_transition add column entity_active_status boolean default true;

alter table meta.entity add column active boolean default true;
