-- Create table for roles if it doesn't exist
CREATE TABLE IF NOT EXISTS documentation.roles
(
    id           bigserial primary key,
    name         character varying(450) COLLATE pg_catalog."default" NOT NULL,
    del          boolean                     DEFAULT false,
    date_create  timestamp without time zone DEFAULT now(),
    date_update  timestamp without time zone,
    last_user_id bigint
);

-- Create a unique index for role names
CREATE UNIQUE INDEX IF NOT EXISTS roles_name_uniq_idx
    ON documentation.roles USING btree
        (lower(name::text) COLLATE pg_catalog."default" ASC NULLS LAST)
    TABLESPACE pg_default
    WHERE NOT del;

-- Add comments to the roles table
COMMENT ON TABLE documentation.roles IS 'User roles';
COMMENT ON COLUMN documentation.roles.id IS 'Unique identifier of the record';
COMMENT ON COLUMN documentation.roles.name IS 'Unique role name';
COMMENT ON COLUMN documentation.roles.del IS 'Flag for deleted records';
COMMENT ON COLUMN documentation.roles.date_create IS 'Date and time of record creation';
COMMENT ON COLUMN documentation.roles.date_update IS 'Date and time of the last record update';
COMMENT ON COLUMN documentation.roles.last_user_id IS 'Identifier of the user who modified the record';

-- Create table for role_permissions if it doesn't exist
CREATE TABLE IF NOT EXISTS documentation.role_permissions
(
    date_create   timestamp without time zone DEFAULT now(),
    permission_id bigint NOT NULL,
    role_id       bigint NOT NULL,
    CONSTRAINT role_permissions_pkey PRIMARY KEY (permission_id, role_id),
    CONSTRAINT role_permissions_permissions_fk FOREIGN KEY (permission_id)
        REFERENCES documentation.permissions (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT role_permissions_roles_fk FOREIGN KEY (role_id)
        REFERENCES documentation.roles (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Create indexes for role_permissions
CREATE INDEX IF NOT EXISTS role_permissions_permissions_idx
    ON documentation.role_permissions USING btree
        (permission_id ASC NULLS LAST)
    TABLESPACE pg_default;

CREATE INDEX IF NOT EXISTS role_permissions_roles_idx
    ON documentation.role_permissions USING btree
        (role_id ASC NULLS LAST)
    TABLESPACE pg_default;

-- Add comments to the role_permissions table
COMMENT ON TABLE documentation.role_permissions IS 'Many-to-many: roles - permissions';
COMMENT ON COLUMN documentation.role_permissions.date_create IS 'Date and time of record creation';
COMMENT ON COLUMN documentation.role_permissions.permission_id IS 'Identifier of the permission';
COMMENT ON COLUMN documentation.role_permissions.role_id IS 'Identifier of the role';
