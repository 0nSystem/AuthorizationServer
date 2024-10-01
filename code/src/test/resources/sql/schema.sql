--CREATE DATABASE management;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA IF NOT EXISTS public;
CREATE SCHEMA IF NOT EXISTS users;
CREATE SCHEMA IF NOT EXISTS applications;
CREATE SCHEMA IF NOT EXISTS enterprise;


CREATE TABLE IF NOT EXISTS users."user"
(
    id_user        SERIAL,
    name           character varying(50) COLLATE pg_catalog."default"  NOT NULL,
    surname        character varying(50) COLLATE pg_catalog."default"  NOT NULL,
    email          character varying(100) COLLATE pg_catalog."default" NOT NULL UNIQUE,
    login          character varying(30) COLLATE pg_catalog."default"  NOT NULL UNIQUE,
    password       character varying(255) COLLATE pg_catalog."default" NOT NULL,
    high_date      timestamp                                           NOT NULL,
    high_id_user   integer references users.user (id_user),
    delete_date    timestamp,
    delete_id_user integer references users.user (id_user),
    CONSTRAINT user_pk PRIMARY KEY (id_user)
);

CREATE TABLE IF NOT EXISTS applications.application
(
    id_application SERIAL,
    name           character varying(100) COLLATE pg_catalog."default" NOT NULL,
    description    character varying(255) COLLATE pg_catalog."default",
    high_date      timestamp                                           NOT NULL,
    high_id_user   integer                                             NOT NULL references users.user (id_user),
    delete_date    timestamp,
    delete_id_user integer references users.user (id_user),
    CONSTRAINT applications_pk PRIMARY KEY (id_application)
);


--SCHEME AUTHORIZATION
CREATE SCHEMA IF NOT EXISTS "authorization";

CREATE TABLE IF NOT EXISTS "authorization".authorization_server_settings
(
    id                                serial,
    issuer                            varchar(255),
    authorization_endpoint            varchar(255) NOT NULL,
    device_authorization_endpoint     varchar(255) NOT NULL,
    device_verification_endpoint      varchar(255) NOT NULL,
    token_endpoint                    varchar(255) NOT NULL,
    jwk_set_endpoint                  varchar(255) NOT NULL,
    token_revocation_endpoint         varchar(255) NOT NULL,
    token_introspection_endpoint      varchar(255) NOT NULL,
    oidc_client_registration_endpoint varchar(255) NOT NULL,
    oidc_user_info_endpoint           varchar(255) NOT NULL,
    oidc_logout_endpoint              varchar(255) NOT NULL,
    constraint authorization_server_settings_pk PRIMARY KEY (id)
);

CREATE TABLE "authorization".oauth2_registered_client
(
    id                            UUID          NOT NULL DEFAULT uuid_generate_v4(),
    client_id                     varchar(100)  NOT NULL,
    client_id_issued_at           timestamp              DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_secret                 varchar(200)           DEFAULT NULL,
    client_secret_expires_at      timestamp              DEFAULT NULL,
    client_name                   varchar(200)  NOT NULL,
    client_authentication_methods varchar(50)[] NOT NULL,
    authorization_grant_types     varchar(50)[] NOT NULL,
    redirect_uris                 varchar(1000)[]        DEFAULT NULL,
    post_logout_redirect_uris     varchar(1000)[]        DEFAULT NULL,
    scopes                        varchar(50)[] NOT NULL,
    client_settings               json          NOT NULL,
    token_settings                json          NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "authorization".oauth2_authorization
(
    id                            UUID         NOT NULL DEFAULT uuid_generate_v4(),
    registered_client_id          UUID         NOT NULL,
    principal_name                varchar(200) NOT NULL,
    authorization_grant_type      varchar(100) NOT NULL,
    authorized_scopes             varchar(100)[]        DEFAULT NULL,
    attributes                    json                  DEFAULT NULL,
    state                         varchar(500)          DEFAULT NULL,
    authorization_code_value      text                  DEFAULT NULL,
    authorization_code_issued_at  timestamp             DEFAULT NULL,
    authorization_code_expires_at timestamp             DEFAULT NULL,
    authorization_code_metadata   json                  DEFAULT NULL,
    access_token_value            text                  DEFAULT NULL,
    access_token_issued_at        timestamp             DEFAULT NULL,
    access_token_expires_at       timestamp             DEFAULT NULL,
    access_token_metadata         json                  DEFAULT NULL,
    access_token_type             varchar(100)          DEFAULT NULL,
    access_token_scopes           varchar(50)[]         DEFAULT NULL,
    oidc_id_token_value           text                  DEFAULT NULL,
    oidc_id_token_issued_at       timestamp             DEFAULT NULL,
    oidc_id_token_expires_at      timestamp             DEFAULT NULL,
    oidc_id_token_metadata        json                  DEFAULT NULL,
    refresh_token_value           text                  DEFAULT NULL,
    refresh_token_issued_at       timestamp             DEFAULT NULL,
    refresh_token_expires_at      timestamp             DEFAULT NULL,
    refresh_token_metadata        json                  DEFAULT NULL,
    user_code_value               text                  DEFAULT NULL,
    user_code_issued_at           timestamp             DEFAULT NULL,
    user_code_expires_at          timestamp             DEFAULT NULL,
    user_code_metadata            json                  DEFAULT NULL,
    device_code_value             text                  DEFAULT NULL,
    device_code_issued_at         timestamp             DEFAULT NULL,
    device_code_expires_at        timestamp             DEFAULT NULL,
    device_code_metadata          json                  DEFAULT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE "authorization".oauth2_authorization_consent
(
    registered_client_id UUID           NOT NULL,
    principal_name       varchar(200)   NOT NULL,
    authorities          varchar(200)[] NOT NULL,
    PRIMARY KEY (registered_client_id, principal_name)
);


-- DATA

INSERT INTO "authorization".authorization_server_settings
(issuer, authorization_endpoint, device_authorization_endpoint,
 device_verification_endpoint, token_endpoint, jwk_set_endpoint,
 token_revocation_endpoint, token_introspection_endpoint,
 oidc_client_registration_endpoint, oidc_user_info_endpoint,
 oidc_logout_endpoint)
VALUES (null, '/oauth2/authorize',
        '/oauth2/device_authorization', '/oauth2/device_verification',
        '/oauth2/token', '/oauth2/jwks',
        '/oauth2/revoke', '/oauth2/introspect',
        '/connect/register', '/userinfo',
        '/connect/logout');