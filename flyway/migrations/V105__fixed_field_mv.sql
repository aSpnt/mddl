update meta.field_def fd set container_id =
    (select ed.container_id from meta.entity_def ed where fd.id in (ed.id_field_def_id, ed.created_ts_field_def_id, ed.updated_ts_field_def_id))
where fd.container_id is null and fd.field_def_type = 'FIXED';
