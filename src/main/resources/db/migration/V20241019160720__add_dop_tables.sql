create table if not exists documentation.deposit
(
    id           bigserial primary key,
    del          boolean                     DEFAULT false,
    date_create  timestamp without time zone DEFAULT now(),
    date_update  timestamp without time zone,
    last_user_id bigint,
    order_id     varchar,
    status       varchar,
    money        double precision,
    user_id      bigint,
    constraint deposit_user_id_fk foreign key (user_id) references documentation.user on update cascade on delete cascade
);

create index if not exists deposit_user_idx
    on documentation.deposit using btree
        (user_id asc nulls last)
    tablespace pg_default;

create table if not exists documentation.withdraw
(
    id           bigserial primary key,
    del          boolean                     DEFAULT false,
    date_create  timestamp without time zone DEFAULT now(),
    date_update  timestamp without time zone,
    last_user_id bigint,
    order_id     varchar,
    status       varchar,
    money        double precision,
    user_id      bigint,
    constraint withdraw_user_id_fk foreign key (user_id) references documentation.user on update cascade on delete cascade
);

create index if not exists withdraw_user_idx
    on documentation.withdraw using btree
        (user_id asc nulls last)
    tablespace pg_default;

create table if not exists documentation.appeal
(
    id           bigserial primary key,
    del          boolean                     DEFAULT false,
    date_create  timestamp without time zone DEFAULT now(),
    date_update  timestamp without time zone,
    last_user_id bigint,
    order_id     varchar,
    status       varchar,
    money        double precision,
    user_id      bigint,
    constraint appeal_user_id_fk foreign key (user_id) references documentation.user on update cascade on delete cascade
);

create index if not exists appeal_user_idx
    on documentation.appeal using btree
        (user_id asc nulls last)
    tablespace pg_default;