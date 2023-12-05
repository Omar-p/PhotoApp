CREATE SEQUENCE users_id_seq START 1;

CREATE TABLE users
(
    id         BIGINT    DEFAULT nextval('users_id_seq') PRIMARY KEY,
    username   VARCHAR(255) NOT NULL UNIQUE CHECK (char_length(username) > 0),
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP
);

CREATE TABLE user_security
(
    user_id        BIGINT PRIMARY KEY REFERENCES users (id) ON DELETE CASCADE,
    email          VARCHAR(255) NOT NULL UNIQUE CHECK (char_length(email) > 0),
    password       VARCHAR(255) NOT NULL CHECK (char_length(password) > 0),
    account_locked BOOLEAN      NOT NULL,
    enabled        BOOLEAN      NOT NULL
);

CREATE SEQUENCE authorities_id_seq START 1;

CREATE TABLE authorities
(
    id   BIGINT DEFAULT nextval('authorities_id_seq') PRIMARY KEY,
    role VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE user_authorities
(
    user_id      BIGINT REFERENCES user_security (user_id) ON DELETE CASCADE,
    authority_id BIGINT REFERENCES authorities (id),
    PRIMARY KEY (user_id, authority_id)
);
