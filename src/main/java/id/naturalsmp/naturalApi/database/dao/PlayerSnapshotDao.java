package id.naturalsmp.naturalApi.database.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Map;
import java.util.Optional;

public interface PlayerSnapshotDao {

    @SqlUpdate("DELETE FROM napi_player_snapshots WHERE player_uuid = :uuid")
    void deleteByUuid(@Bind("uuid") String uuid);

    @SqlUpdate("INSERT INTO napi_player_snapshots (id, player_uuid, player_name, snapshot_time, world, x, y, z, yaw, pitch, health, max_health, food_level, saturation, exp_level, exp_progress, total_exp, gamemode, inventory_json, armor_json, effects_json, skin_texture, skin_signature, vault_group, vault_prefix, vault_suffix, lp_group, playtime, balance, kills, deaths, mob_kills, votes, ip_address, country, region, city, isp, asn, locale, client_brand, ping) VALUES (:id, :player_uuid, :player_name, :snapshot_time, :world, :x, :y, :z, :yaw, :pitch, :health, :max_health, :food_level, :saturation, :exp_level, :exp_progress, :total_exp, :gamemode, :inventory_json, :armor_json, :effects_json, :skin_texture, :skin_signature, :vault_group, :vault_prefix, :vault_suffix, :lp_group, :playtime, :balance, :kills, :deaths, :mob_kills, :votes, :ip_address, :country, :region, :city, :isp, :asn, :locale, :client_brand, :ping)")
    void insertSnapshot(
            @Bind("id") String id,
            @Bind("player_uuid") String playerUuid,
            @Bind("player_name") String playerName,
            @Bind("snapshot_time") long snapshotTime,
            @Bind("world") String world,
            @Bind("x") double x,
            @Bind("y") double y,
            @Bind("z") double z,
            @Bind("yaw") float yaw,
            @Bind("pitch") float pitch,
            @Bind("health") double health,
            @Bind("max_health") double maxHealth,
            @Bind("food_level") int foodLevel,
            @Bind("saturation") float saturation,
            @Bind("exp_level") int expLevel,
            @Bind("exp_progress") float expProgress,
            @Bind("total_exp") int totalExp,
            @Bind("gamemode") String gamemode,
            @Bind("inventory_json") String inventoryJson,
            @Bind("armor_json") String armorJson,
            @Bind("effects_json") String effectsJson,
            @Bind("skin_texture") String skinTexture,
            @Bind("skin_signature") String skinSignature,
            @Bind("vault_group") String vaultGroup,
            @Bind("vault_prefix") String vaultPrefix,
            @Bind("vault_suffix") String vaultSuffix,
            @Bind("lp_group") String lpGroup,
            @Bind("playtime") long playtime,
            @Bind("balance") double balance,
            @Bind("kills") int kills,
            @Bind("deaths") int deaths,
            @Bind("mob_kills") int mobKills,
            @Bind("votes") int votes,
            @Bind("ip_address") String ipAddress,
            @Bind("country") String country,
            @Bind("region") String region,
            @Bind("city") String city,
            @Bind("isp") String isp,
            @Bind("asn") String asn,
            @Bind("locale") String locale,
            @Bind("client_brand") String clientBrand,
            @Bind("ping") int ping
    );

    @SqlQuery("SELECT * FROM napi_player_snapshots WHERE player_uuid = :uuid ORDER BY snapshot_time DESC LIMIT 1")
    Optional<Map<String, Object>> getLatestSnapshotByUuid(@Bind("uuid") String uuid);

    @SqlQuery("SELECT * FROM napi_player_snapshots WHERE player_name = :name ORDER BY snapshot_time DESC LIMIT 1")
    Optional<Map<String, Object>> getLatestSnapshotByName(@Bind("name") String name);
}
