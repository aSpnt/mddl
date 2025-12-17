drop trigger if exists field_value_fts_trigger_notify on meta.field_value;
drop trigger if exists entity_fts_trigger on meta.entity;
drop trigger if exists entity_fts_trigger_notify on meta.entity;
drop trigger if exists entity_collection_fts_trigger_notify on meta.field_collection;

-- функция вычисляет для поля его вектор для полнотекстового поиска
create or replace function field_value_fts()
    returns trigger
    language plpgsql as
$BODY$
DECLARE
    def     RECORD;
BEGIN
    RAISE LOG 'FV_TRIGGER %, %', NEW.id, txid_current();

    SELECT fd.fts_priority, fd.fts_language, fd.use_search_filter, fd.type
    INTO def
    FROM meta.field_def fd
    where fd.id = NEW.field_def_id;

    NEW.fts_vector := setweight(
            to_tsvector(
                    coalesce(def.fts_language::regconfig, 'russian'),
                    coalesce(
                            NEW.text_value,
                            array_to_string(NEW.array_text, ' '),
                            '')
            ),
            def.fts_priority::"char");

    return NEW;
END ;
$BODY$;

-- функция вычисляет для поля его вектор для полнотекстового поиска
create or replace function calculate_entity_fts(
    entityId uuid
) RETURNS tsvector
    language plpgsql as
$BODY$
DECLARE
    fts_vec_simple     tsvector;
    fts_vec_entity     tsvector;
    fts_vec_collection tsvector;
    fts_vec_ref        tsvector;
    fts_vec_result     tsvector;
BEGIN
    RAISE INFO 'Calculate fts %, %', entityId, txid_current();

    -- сначала собираем готовые поля с текстовыми векторами вычисленными триггером
    SELECT tsvector_agg(fv.fts_vector)
    INTO fts_vec_simple
    FROM meta.field_value fv
             JOIN meta.field_def fd on fv.field_def_id = fd.id
    WHERE fv.entity_id = entityId
      AND fd.use_search_filter = true
      AND fd.type in ('STRING', 'TEXT', 'SLUG', 'TAGS', 'HTML');

    -- собираем каскадные поля
    SELECT tsvector_agg(calculate_entity_fts(fv.ref_value))
    INTO fts_vec_entity
    FROM meta.field_value fv
             JOIN meta.field_def fd on fv.field_def_id = fd.id
    WHERE fv.entity_id = entityId
      AND fd.type in ('ENTITY');

    SELECT tsvector_agg(calculate_entity_fts(fc_e.id))
    INTO fts_vec_collection
    FROM meta.field_value fv
             JOIN meta.field_def fd on fv.field_def_id = fd.id
             JOIN meta.field_collection fc on fc.field_value_id = fv.id
             JOIN meta.entity fc_e on fc.entity_id = fc_e.id
    WHERE fv.entity_id = entityId
      AND fd.type in ('COLLECTION');

    -- собираем поля внешних суностей (они не сохраняются каскадно и мы исходим из того, что вектора для них уже актуальны)
    SELECT tsvector_agg(fc_e.fts_vector)
    INTO fts_vec_ref
    FROM meta.field_value fv
             JOIN meta.field_def fd on fv.field_def_id = fd.id
             JOIN meta.ref_value_collection rvc on rvc.field_value_id = fv.id
             JOIN meta.entity fc_e on rvc.entity_id = fc_e.id
    WHERE fv.entity_id = entityId
      AND fd.type in ('DICTIONARY', 'STATUS');

    SELECT fts_vec_collection || fts_vec_entity || fts_vec_ref || fts_vec_simple
    INTO fts_vec_result;

    UPDATE meta.entity SET fts_vector = fts_vec_result WHERE id = entityId;

    RAISE INFO 'Calculated fts %, %, %', entityId, txid_current(), fts_vec_result;

    return fts_vec_result;
END ;
$BODY$;
