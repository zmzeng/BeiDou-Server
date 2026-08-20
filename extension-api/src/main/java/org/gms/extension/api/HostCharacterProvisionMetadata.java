package org.gms.extension.api;

/**
 * Native identity available to extension metadata persistence before the host
 * transaction commits.
 */
public record HostCharacterProvisionMetadata(
        int characterId,
        int accountId,
        String accountName,
        String characterName,
        int worldId
) {
}
