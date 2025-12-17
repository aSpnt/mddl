alter table meta.entity_def
    add column singleton boolean default false;
alter table meta.entity_def
    add column singleton_entity_id uuid;

alter table meta.entity_def add constraint fk_entity_def_ref_entity
    foreign key (singleton_entity_id) references meta.entity (id);
