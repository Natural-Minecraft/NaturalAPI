package id.naturalsmp.naturalApi.database.dao;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ApiKeyDao {

    @SqlUpdate("INSERT INTO napi_api_keys (id, name, key_hash, key_salt, scopes, created_at, expires_at, enabled) VALUES (:id, :name, :key_hash, :key_salt, :scopes, :created_at, :expires_at, :enabled)")
    void insertKey(
            @Bind("id") String id,
            @Bind("name") String name,
            @Bind("key_hash") String keyHash,
            @Bind("key_salt") String keySalt,
            @Bind("scopes") String scopes,
            @Bind("created_at") long createdAt,
            @Bind("expires_at") Long expiresAt,
            @Bind("enabled") boolean enabled
    );



    @SqlUpdate("UPDATE napi_api_keys SET enabled = :enabled WHERE id = :id")
    void setKeyEnabled(@Bind("id") String id, @Bind("enabled") boolean enabled);

    @SqlUpdate("DELETE FROM napi_api_keys WHERE id = :id")
    void deleteKey(@Bind("id") String id);

    @SqlUpdate("UPDATE napi_api_keys SET last_used = :last_used WHERE id = :id")
    void updateLastUsed(@Bind("id") String id, @Bind("last_used") long lastUsed);
}
