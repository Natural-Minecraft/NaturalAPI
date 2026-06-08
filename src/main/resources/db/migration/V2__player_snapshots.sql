CREATE TABLE IF NOT EXISTS napi_player_snapshots (
    id VARCHAR(36) PRIMARY KEY,
    player_uuid VARCHAR(36) NOT NULL,
    player_name VARCHAR(16) NOT NULL,
    snapshot_time BIGINT NOT NULL,
    world VARCHAR(255),
    x DOUBLE,
    y DOUBLE,
    z DOUBLE,
    yaw FLOAT,
    pitch FLOAT,
    health DOUBLE,
    max_health DOUBLE,
    food_level INT,
    saturation FLOAT,
    exp_level INT,
    exp_progress FLOAT,
    total_exp INT,
    gamemode VARCHAR(20),
    inventory_json TEXT,
    armor_json TEXT,
    effects_json TEXT,
    skin_texture TEXT,
    skin_signature TEXT,
    vault_group VARCHAR(255),
    vault_prefix VARCHAR(255),
    vault_suffix VARCHAR(255),
    lp_group VARCHAR(255)
);

CREATE INDEX idx_snapshots_uuid ON napi_player_snapshots(player_uuid);
CREATE INDEX idx_snapshots_name ON napi_player_snapshots(player_name);
