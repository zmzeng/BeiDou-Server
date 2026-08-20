package org.gms.extension.api;

import java.sql.Connection;

/**
 * Persists extension-owned metadata inside the host transaction. Implementors
 * must use the supplied connection and must not commit, roll back, or close it.
 */
@FunctionalInterface
public interface HostCharacterMetadataCallback {

    void persist(Connection connection, HostCharacterProvisionMetadata metadata) throws Exception;
}
