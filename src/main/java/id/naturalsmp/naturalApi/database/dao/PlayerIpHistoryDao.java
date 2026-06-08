package id.naturalsmp.naturalApi.database.dao;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PlayerIpHistoryDao {

    @SqlUpdate("INSERT INTO napi_player_ip_history (id, player_uuid, player_name, ip_address, country, region, city, isp, asn, first_seen, last_seen) " +
               "VALUES (:id, :player_uuid, :player_name, :ip_address, :country, :region, :city, :isp, :asn, :first_seen, :last_seen)")
    void insertRecord(
            @Bind("id") String id,
            @Bind("player_uuid") String playerUuid,
            @Bind("player_name") String playerName,
            @Bind("ip_address") String ipAddress,
            @Bind("country") String country,
            @Bind("region") String region,
            @Bind("city") String city,
            @Bind("isp") String isp,
            @Bind("asn") String asn,
            @Bind("first_seen") long firstSeen,
            @Bind("last_seen") long lastSeen
    );

    @SqlUpdate("UPDATE napi_player_ip_history SET last_seen = :last_seen, player_name = :player_name WHERE id = :id")
    void updateLastSeen(
            @Bind("id") String id,
            @Bind("player_name") String playerName,
            @Bind("last_seen") long lastSeen
    );
}
