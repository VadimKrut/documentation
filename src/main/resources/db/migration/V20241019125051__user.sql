create table if not exists documentation.user
(
    id            bigserial primary key,
    del           boolean                     DEFAULT false,
    date_create   timestamp without time zone DEFAULT now(),
    date_update   timestamp without time zone,
    last_user_id  bigint,
    login         varchar unique not null,
    password      varchar        not null,
    balance double precision,
    role_id bigint,
    constraint users_roles_fk foreign key (role_id)
        references documentation.roles (id) match simple
        on update cascade on delete set null
);

create index if not exists users_roles_idx
    on documentation.user using btree
        (role_id asc nulls last)
    tablespace pg_default
    where not del;