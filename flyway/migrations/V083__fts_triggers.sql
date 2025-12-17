-- функция вычисляет для поля его вектор для полнотекстового поиска
create or replace function field_value_fts()
    returns trigger
    language plpgsql as
$BODY$
DECLARE
    def     RECORD;
    fts_vec tsvector;
BEGIN
    SELECT fd.fts_priority, fd.fts_language, fd.use_search_filter, fd.type
    INTO def
    FROM meta.field_def fd
    where fd.id = NEW.field_def_id;

    if (def.type = 'ENTITY' and NEW.entity_value is not null) THEN
        fts_vec := (select fts_vector from meta.entity e where e.id = NEW.entity_value);
    end if;

    if ((def.type = 'DICTIONARY' or def.type = 'STATUS') and NEW.ref_value is not null) THEN
        fts_vec := (select fts_vector from meta.entity e where e.id = NEW.ref_value);
    end if;

    if (def.type = 'COLLECTION') THEN
        fts_vec := (select tsvector_agg(e.fts_vector)
                    from meta.field_collection rvc
                             join meta.entity e on rvc.entity_id = e.id
                    where rvc.field_value_id = NEW.id);
    end if;

    NEW.fts_vector := setweight(
            coalesce(
                    fts_vec,
                    to_tsvector(
                            coalesce(def.fts_language, 'russian'),
                            coalesce(
                                    NEW.text_value,
                                    array_to_string(NEW.array_text, ' '),
                                    '')
                    )
            ),
            def.fts_priority::"char");
    return NEW;
END ;
$BODY$;
