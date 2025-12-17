create table meta.external_connection
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,

    url text,
    method text,
    param text[],
    response_param text[],

    constraint external_connection_id_pk primary key (id)
);

alter table meta.field_def add column external_connection_id uuid references meta.external_connection(id);
alter table meta.field_def add column external_connection_batch_id uuid references meta.external_connection(id);
