alter table meta.field_def add column dictionary_external_ref uuid;

alter table meta.dictionary_external add constraint dictionary_external_id_pk primary key (id);

alter table meta.field_def add constraint fk_field_def_ref_dictionary_external_ref
    foreign key (dictionary_external_ref) references meta.dictionary_external (id);