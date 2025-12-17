create extension if not exists postgis;

create table meta.geometry_value
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,

    title text not null,
    message text not null,

    geom geometry(Point, 4326),

    field_value_id uuid not null,

    constraint geometry_value_id_pk primary key (id),
    constraint fk_geometry_value_field_value
        foreign key(field_value_id) references meta.field_value(id)
);
