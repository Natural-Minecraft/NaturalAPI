CREATE TABLE napi_api_keys (
  id          VARCHAR(36) PRIMARY KEY,   -- UUID
  name        VARCHAR(100) NOT NULL,
  key_hash    VARCHAR(128) NOT NULL,     -- SHA-256 hex
  key_salt    VARCHAR(64)  NOT NULL,
  scopes      TEXT         NOT NULL,     -- comma-separated
  created_at  BIGINT       NOT NULL,
  expires_at  BIGINT,                    -- NULL = never
  last_used   BIGINT,
  enabled     BOOLEAN      DEFAULT TRUE
);

CREATE TABLE napi_player_snapshots (
  id              VARCHAR(36) PRIMARY KEY,
  player_uuid     VARCHAR(36) NOT NULL,
  player_name     VARCHAR(64) NOT NULL,
  snapshot_time   BIGINT      NOT NULL,
  world           VARCHAR(64),
  x               DOUBLE,
  y               DOUBLE,
  z               DOUBLE,
  yaw             FLOAT,
  pitch           FLOAT,
  health          DOUBLE,
  max_health      DOUBLE,
  food_level      INT,
  saturation      FLOAT,
  exp_level       INT,
  exp_progress    FLOAT,
  total_exp       INT,
  gamemode        VARCHAR(16),
  inventory_json  MEDIUMTEXT,            -- full inventory JSON
  armor_json      TEXT,
  effects_json    TEXT,
  skin_texture    TEXT,
  skin_signature  TEXT,
  vault_group     VARCHAR(64),
  vault_prefix    VARCHAR(128),
  vault_suffix    VARCHAR(128),
  lp_group        VARCHAR(64)
);
CREATE INDEX idx_uuid ON napi_player_snapshots (player_uuid);
CREATE INDEX idx_time ON napi_player_snapshots (snapshot_time);

CREATE TABLE napi_skin_cache (
  player_uuid   VARCHAR(36) PRIMARY KEY,
  texture_url   TEXT,
  texture_value TEXT,
  signature     TEXT,
  cached_at     BIGINT NOT NULL,
  expires_at    BIGINT NOT NULL
);

CREATE TABLE napi_rate_limits (
  ip            VARCHAR(45) PRIMARY KEY,
  tokens        INT         NOT NULL,
  last_refill   BIGINT      NOT NULL
);
