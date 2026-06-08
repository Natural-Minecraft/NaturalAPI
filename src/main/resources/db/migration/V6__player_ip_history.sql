CREATE TABLE IF NOT EXISTS napi_player_ip_history (
    id VARCHAR(36) PRIMARY KEY,
    player_uuid VARCHAR(36) NOT NULL,
    player_name VARCHAR(16) NOT NULL,
    ip_address VARCHAR(45) NOT NULL,
    country VARCHAR(100),
    region VARCHAR(100),
    city VARCHAR(100),
    isp VARCHAR(150),
    asn VARCHAR(150),
    first_seen BIGINT NOT NULL,
    last_seen BIGINT NOT NULL
);

CREATE INDEX idx_ip_history_uuid ON napi_player_ip_history(player_uuid);
CREATE INDEX idx_ip_history_ip ON napi_player_ip_history(ip_address);
