alter table meta.field_def drop column collection_ref;

alter table meta.field_def add column collection_ref uuid;

alter table meta.field_def add constraint fk_field_def_ref_collection_ref
    foreign key (collection_ref) references meta.entity_def (id);