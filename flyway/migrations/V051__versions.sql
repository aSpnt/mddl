alter table meta.entity_def
    add column version bigint default 0;

alter table meta.entity
    add column version bigint default 0;
