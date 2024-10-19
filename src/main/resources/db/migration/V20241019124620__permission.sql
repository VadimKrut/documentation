create table if not exists documentation.permissions
(
    id           bigserial primary key,
    perm         varchar not null,
    description  varchar,
    del          boolean                     default false,
    date_create  timestamp without time zone default now(),
    date_update  timestamp without time zone,
    last_user_id bigint
);

CREATE UNIQUE INDEX IF NOT EXISTS permissions_uniq_idx
    ON documentation.permissions USING btree
        (perm COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default
    WHERE NOT del;

COMMENT ON TABLE documentation.permissions IS 'System permissions';
COMMENT ON COLUMN documentation.permissions.id IS 'Unique identifier of the record';
COMMENT ON COLUMN documentation.permissions.perm IS 'Unique system permission';
COMMENT ON COLUMN documentation.permissions.description IS 'Permission description';
COMMENT ON COLUMN documentation.permissions.del IS 'Flag for deleted records';
COMMENT ON COLUMN documentation.permissions.date_create IS 'Date and time of record creation';
COMMENT ON COLUMN documentation.permissions.date_update IS 'Date and time of the last record update';
COMMENT ON COLUMN documentation.permissions.last_user_id IS 'Identifier of the user who modified the record';