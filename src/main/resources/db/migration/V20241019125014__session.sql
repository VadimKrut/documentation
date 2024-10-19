CREATE TABLE IF NOT EXISTS documentation.sessions
(
    id            bigserial primary key,
    del           boolean                     DEFAULT false,
    date_create   timestamp without time zone DEFAULT now(),
    date_update   timestamp without time zone,
    last_user_id  bigint,
    user_id       bigint            NOT NULL,
    access_token  character varying NOT NULL,
    refresh_token character varying NOT NULL,
    last_login    timestamp without time zone,
    useragent     character varying,
    user_ip       character varying
);

COMMENT ON TABLE documentation.sessions IS 'User sessions';
COMMENT ON COLUMN documentation.sessions.id IS 'Unique identifier of the record';
COMMENT ON COLUMN documentation.sessions.del IS 'Flag for deleted records';
COMMENT ON COLUMN documentation.sessions.date_create IS 'Date and time of record creation';
COMMENT ON COLUMN documentation.sessions.date_update IS 'Date and time of the last record update';
COMMENT ON COLUMN documentation.sessions.last_user_id IS 'Identifier of the user who modified the record';
COMMENT ON COLUMN documentation.sessions.user_id IS 'Reference to the user';
COMMENT ON COLUMN documentation.sessions.access_token IS 'Short-lived token (JWT)';
COMMENT ON COLUMN documentation.sessions.refresh_token IS 'Long-lived one-time token';
COMMENT ON COLUMN documentation.sessions.last_login IS 'Date and time of the last login';
COMMENT ON COLUMN documentation.sessions.useragent IS 'Useragent string from the request header';
COMMENT ON COLUMN documentation.sessions.user_ip IS 'IP address from which the login was performed';