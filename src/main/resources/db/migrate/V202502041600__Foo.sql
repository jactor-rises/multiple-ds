CREATE TABLE IF NOT EXISTS FOO
(
    foo_id  UUID PRIMARY KEY UNIQUE,
    bar_id  UUID      NULL,
    created TIMESTAMP NOT NULL,
    updated TIMESTAMP NOT NULL
);
