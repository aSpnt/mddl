create table meta.dictionary_external
(
    id uuid not null,
    created_ts timestamp not null,
    updated_ts timestamp not null,

    name text not null, -- отображаемое название
    code text not null, -- код привязки
    url text not null
);