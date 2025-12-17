alter table meta.dictionary_external add column method text not null default 'GET';
alter table meta.dictionary_external add column param text not null default 'search';