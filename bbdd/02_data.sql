\c management;

DO
$$
    DECLARE
        oauth2_registered_client_UUID_Generated uuid;
    BEGIN

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

        INSERT INTO "authorization".oauth2_authorization_methods
            (authorization_methods_value)
        VALUES ('client_secret_basic'),
               ('client_secret_post'),
               ('client_secret_jwt'),
               ('private_key_jwt'),
               ('none');

        INSERT INTO "authorization".oauth2_authorization_grant_types
            (authorization_grant_type_value)
        VALUES ('authorization_code'),
               ('refresh_token'),
               ('client_credentials'),
               ('password'),
               ('urn:ietf:params:oauth:grant-type:jwt-bearer'),
               ('urn:ietf:params:oauth:grant-type:device_code');

        INSERT INTO users."user"
            (name, surname, email, login, password, high_date, high_id_user)
        VALUES ('srvauthorizationserver', 'srvauthorizationserver', 'srvauthorizationserver@protom.me',
                'srvauthorizationserver', '{noop}password', CURRENT_TIMESTAMP, null);

        INSERT INTO users."user"
            (name, surname, email, login, password, high_date, high_id_user)
        VALUES ('onsystem_name', 'onsystem_surname', 'onsystem@protom.me',
                'onsystem', '{noop}password', CURRENT_TIMESTAMP, null);

        INSERT INTO applications.application
            (name, description, high_date, high_id_user)
        VALUES ('authorization-server', 'AuthorizationServer', CURRENT_TIMESTAMP, 1);

        INSERT INTO "authorization".oauth2_registered_client
            (application_id, user_id, client_name)
        VALUES (1, 1, 'oauthauthorizationserver')
        RETURNING id INTO oauth2_registered_client_UUID_Generated;

        INSERT INTO "authorization".oauth2_registered_client_authorization_client_settings
        (registered_client_id, require_proof_key, require_authorization_consent, jwt_set_url,token_endpoint_authentication_signing_algorithm)
        VALUES (oauth2_registered_client_UUID_Generated, true, true, null, 'RS256');

        INSERT INTO "authorization".oauth2_registered_client_token_settings
        (registered_client_id, authorization_code_time_to_live, access_token_time_to_live,
         access_token_format, device_code_time_to_live, reuse_refresh_tokens,
         refresh_token_time_to_live, id_token_signature_algorithm)
        VALUES (oauth2_registered_client_UUID_Generated, 100000000, 100000000,
                'self-contained', 100000000, false,
                100000000, 'RS256');

        RAISE NOTICE 'assing uuid: %', oauth2_registered_client_UUID_Generated;

        insert into "authorization".oauth2_authorization_redirect_uris
            (id, url)
        VALUES (oauth2_registered_client_UUID_Generated, 'http://localhost:9090');

        -- Insertar datos en oauth2_registered_client_authorization_grant_types
        INSERT INTO "authorization".oauth2_registered_client_authorization_grant_types
            (oauth2_registered_client_id, authorization_grant_types_id)
        SELECT oauth2_registered_client_UUID_Generated, gt.id
        FROM "authorization".oauth2_authorization_grant_types gt;

        RAISE NOTICE 'set oauth2_authorization_grant_types';

        INSERT INTO management."authorization".oauth2_registered_client_authorization_methods
            (oauth2_registered_client_id, authorization_methods_id)
        SELECT oauth2_registered_client_UUID_Generated, am.id
        FROM "authorization".oauth2_authorization_methods am;

        RAISE NOTICE 'set oauth2_registered_client_authorization_methods';

        INSERT INTO "authorization".oauth2_scope (name)
        VALUES ('Example'),
               ('Test'),
               ('Management');

        INSERT INTO "authorization".oauth2_registered_client_scopes
            (id_registered_client, id_scope)
        SELECT oauth2_registered_client_UUID_Generated, s.id
        FROM "authorization".oauth2_scope s;

    END
$$;
