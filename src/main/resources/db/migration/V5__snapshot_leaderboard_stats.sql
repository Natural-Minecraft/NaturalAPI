ALTER TABLE napi_player_snapshots ADD COLUMN playtime BIGINT DEFAULT 0;
ALTER TABLE napi_player_snapshots ADD COLUMN balance DOUBLE DEFAULT 0.0;
ALTER TABLE napi_player_snapshots ADD COLUMN kills INT DEFAULT 0;
ALTER TABLE napi_player_snapshots ADD COLUMN deaths INT DEFAULT 0;
ALTER TABLE napi_player_snapshots ADD COLUMN votes INT DEFAULT 0;

CREATE INDEX idx_snapshots_playtime ON napi_player_snapshots(playtime);
CREATE INDEX idx_snapshots_balance ON napi_player_snapshots(balance);
CREATE INDEX idx_snapshots_kills ON napi_player_snapshots(kills);
CREATE INDEX idx_snapshots_deaths ON napi_player_snapshots(deaths);
CREATE INDEX idx_snapshots_votes ON napi_player_snapshots(votes);
