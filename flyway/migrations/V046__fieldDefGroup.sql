alter table meta.entity_def_group add column parent_id UUID;
alter table meta.entity_def_group add constraint fk_entity_def_group_ref_entity_def_group
        foreign key (parent_id) references meta.entity_def_group (id);
