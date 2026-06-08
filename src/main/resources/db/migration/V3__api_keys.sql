CREATE TABLE IF NOT EXISTS napi_api_keys (
  id          VARCHAR(36) PRIMARY KEY,
  name        VARCHAR(100) NOT NULL,
  key_hash    VARCHAR(128) NOT NULL,
  key_salt    VARCHAR(64)  NOT NULL,
  scopes      TEXT         NOT NULL,
  created_at  BIGINT       NOT NULL,
  expires_at  BIGINT,
  last_used   BIGINT,
  enabled     BOOLEAN      DEFAULT TRUE
);
