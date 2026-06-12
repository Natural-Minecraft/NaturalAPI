ALTER TABLE napi_player_snapshots ADD COLUMN mob_kills INT DEFAULT 0;
CREATE INDEX idx_snapshots_mob_kills ON napi_player_snapshots(mob_kills);
