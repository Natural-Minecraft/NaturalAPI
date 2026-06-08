CREATE TABLE IF NOT EXISTS napi_skin_cache (
  player_uuid   VARCHAR(36) PRIMARY KEY,
  texture_url   TEXT,
  texture_value TEXT,
  signature     TEXT,
  cached_at     BIGINT NOT NULL,
  expires_at    BIGINT NOT NULL
);
