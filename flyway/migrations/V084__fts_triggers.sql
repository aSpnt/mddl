-- функция вычисляет для поля его вектор для полнотекстового поиска
create or replace function field_value_fts()
    returns trigger
    language plpgsql as
$BODY$
DECLARE
    def     RECORD;
    fts_vec tsvector;
BEGIN
    RAISE LOG 'FV_TRIGGER %, %', NEW.id, txid_current();

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

-- функция вычисляет для поля его вектор для полнотекстового поиска
create or replace function field_value_fts_notify()
    returns trigger
    language plpgsql as
$BODY$
DECLARE
    def     RECORD;
    fts_vec tsvector;
BEGIN
    RAISE LOG 'FV_TRIGGER_NOTIFY %, %, %, %', NEW.id, txid_current(), NEW.fts_vector, OLD.fts_vector;

    -- если были изменения
    IF (((OLD is NULL) and (NEW.fts_vector is not NULL)) or (NEW.fts_vector <> OLD.fts_vector)) THEN
        -- обновление родительской entity
        update meta.entity set id = id where id = NEW.entity_id;
    END IF;

    return NEW;
END ;
$BODY$;

-- триггер для пересчет полнотекстового вектора для данных поля
drop trigger if exists field_value_fts_trigger on meta.field_value;
create trigger field_value_fts_trigger
    before insert or update
    on meta.field_value
    for each row
execute procedure field_value_fts();

-- триггер для пересчет полнотекстового вектора для данных поля
drop trigger if exists field_value_fts_trigger_notify on meta.field_value;
create trigger field_value_fts_trigger_notify
    after insert or update
    on meta.field_value
    for each row
execute procedure field_value_fts_notify();

-- функция для пересчета вектора для полнотекстового поиска на основе входящих в сущность полей
create or replace function entity_fts()
    returns trigger
    language plpgsql as
$BODY$
DECLARE
    def RECORD;
BEGIN
    RAISE LOG 'E_TRIGGER %, %', NEW.id, txid_current();

    SELECT tsvector_agg(fv.fts_vector) as fts_agg_vector
    INTO def
    FROM meta.field_value fv
             JOIN meta.field_def fd on fd.id = fv.field_def_id
    WHERE fd.use_search_filter = true
      and fv.entity_id = NEW.id;

    NEW.fts_vector := def.fts_agg_vector;

    RAISE LOG 'E_TRIGGER VECTOR %, %', NEW.fts_vector, txid_current();

    return NEW;
END;
$BODY$;

create or replace function entity_fts_notify()
    returns trigger
    language plpgsql as
$BODY$
DECLARE
    def RECORD;
BEGIN
    RAISE LOG 'E_TRIGGER_AFTER %, %, %, %', NEW.id, txid_current(), NEW.fts_vector, OLD.fts_vector;

    -- если были изменения
    IF (((OLD is NULL) and (NEW.fts_vector is not NULL)) or (NEW.fts_vector <> OLD.fts_vector)) THEN
        -- форсирует обновление связаныых через коллекции сущностей
        update meta.field_value
        set id = id
        where id in (select rvc.field_value_id
                     from meta.field_collection rvc
                              join meta.entity e on rvc.entity_id = e.id
                     where rvc.entity_id = NEW.id);
    END IF;

    return NEW;
END;
$BODY$;

-- триггер для пересчета поискового вектора для сущности
drop trigger if exists entity_fts_trigger on meta.entity;
create trigger entity_fts_trigger
    before insert or update
    on meta.entity
    for each row
execute procedure entity_fts();

-- триггер для пересчета поискового вектора для сущности
drop trigger if exists entity_fts_trigger_notify on meta.entity;
create trigger entity_fts_trigger_notify
    after insert or update
    on meta.entity
    for each row
execute procedure entity_fts_notify();

create or replace function entity_collection_fts_notify()
    returns trigger
    language plpgsql as
$BODY$
DECLARE
    def RECORD;
BEGIN
    RAISE LOG 'E_COLLECTION_TRIGGER_AFTER %, %', NEW.field_value_id, txid_current();

    -- форсирует обновление связаныых полей
    update meta.field_value set id = id where id = NEW.field_value_id;

    return NULL;
END;
$BODY$;

-- триггер для пересчета поискового вектора для сущности с полем являющимся коллекцией
drop trigger if exists entity_collection_fts_trigger_notify on meta.field_collection;
create trigger entity_collection_fts_trigger_notify
    after insert
    on meta.field_collection
    for each row
execute procedure entity_collection_fts_notify();
