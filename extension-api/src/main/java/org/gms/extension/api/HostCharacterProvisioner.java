package org.gms.extension.api;

/**
 * Atomic host-owned creation of an account, a native character and
 * extension-owned metadata.
 */
@FunctionalInterface
public interface HostCharacterProvisioner {

    HostCharacterProvisionResult provision(
            HostCharacterProvisionRequest request,
            HostCharacterMetadataCallback metadataCallback
    ) throws Exception;
}
