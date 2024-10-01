\c management;

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