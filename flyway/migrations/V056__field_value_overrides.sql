alter table meta.entity_template add column entity_def_id uuid;

alter table meta.entity_template add constraint fk_entity_template_ref_entity_def
    foreign key (entity_def_id) references meta.entity_def (id);
