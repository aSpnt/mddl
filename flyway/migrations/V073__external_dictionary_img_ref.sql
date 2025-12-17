alter table meta.dictionary_external add column img_param text;
alter table meta.dictionary_external add column img_expression text;
alter table meta.dictionary_external add column ref_expression text;

alter table meta.dictionary_external rename column param to param_old;
alter table meta.dictionary_external add column param text[];

update meta.dictionary_external set param = array[param_old];

alter table meta.dictionary_external drop column param_old;
