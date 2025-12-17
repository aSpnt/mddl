alter table meta.dictionary_external_value add column seq int default 0;

alter table meta.dictionary_external_value add column description text;
