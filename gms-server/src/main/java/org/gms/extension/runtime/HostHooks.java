package org.gms.extension.runtime;

import org.gms.client.Character;
import org.gms.extension.api.ArtificialCharacters;
import org.gms.extension.api.HostEvent;
import org.gms.extension.api.HostRuntime;

/**
 * Convenience accessors for engine code: artificial-character checks and host event publish.
 * Keeps {@code org.gms} free of plugin package imports.
 */
public final class HostHooks {

    private HostHooks() {
    }

    public static boolean isArtificial(Character character) {
        return character != null && ArtificialCharacters.isArtificial(character.getId());
    }

    public static boolean isArtificial(int characterId) {
        return ArtificialCharacters.isArtificial(characterId);
    }

    public static void publish(HostEvent event) {
        if (event == null) {
            return;
        }
        HostRuntime runtime = ExtensionLoader.getInstance().getRuntime();
        if (runtime != null) {
            runtime.events().publish(event);
        }
    }
}
