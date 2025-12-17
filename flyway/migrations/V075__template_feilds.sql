alter table meta.entity_template add column description text;
alter table meta.entity_template add column status text;
alter table meta.entity_template add column group_name text;

alter table meta.entity_def add column template_note text;

alter table meta.field_value add column required_override boolean;
