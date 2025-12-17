CREATE OR REPLACE VIEW meta.entity_def_ref_field_def
AS
WITH RECURSIVE r AS (SELECT c.*,
                            ARRAY [c.id] AS path
                     FROM meta.field_def_container c
                     WHERE c.parent_id IS NULL
                     UNION
                     SELECT c.*,
                            array_append(r.path, c.id) AS path
                     FROM meta.field_def_container c
                              JOIN r ON r.id = c.parent_id)
SELECT ed.id as entity_def_id, ed.code as entity_def_code, fd.id as field_def_id, fd.code as field_def_code
FROM meta.entity_def ed
         JOIN r ON r.path[1] = ed.container_id
         JOIN meta.field_def fd on fd.container_id = r.path[array_length(r.path, 1)]
ORDER BY ed.code;
