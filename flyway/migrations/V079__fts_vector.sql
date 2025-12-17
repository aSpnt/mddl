alter table meta.entity add column fts_vector tsvector;
alter table meta.field_value add column fts_vector tsvector;

alter table meta.field_def add column fts_language regconfig default 'russian';
