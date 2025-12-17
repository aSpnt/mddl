ALTER TABLE meta.dictionary_external_value DROP CONSTRAINT dictionary_external_value_id_pk;
ALTER TABLE meta.dictionary_external_value ADD PRIMARY KEY (id, field_value_id);
