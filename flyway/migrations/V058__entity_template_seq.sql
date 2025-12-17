alter table meta.field_value drop column seq_override;

alter table meta.field_value add column seq_override int;
