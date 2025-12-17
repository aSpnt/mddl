create extension if not exists pg_trgm;

CREATE INDEX trgm_idx ON meta.field_value USING GIN (text_value gin_trgm_ops);
