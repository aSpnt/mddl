alter table meta.dictionary_external add column entity_def_id uuid;
alter table meta.dictionary_external add constraint fk_dictionary_external_ref_entity_def
    foreign key (entity_def_id) references meta.entity_def (id);

alter table meta.field_def add column visible_template boolean default true;
alter table meta.field_def add column allow_collection_remove boolean default true;
