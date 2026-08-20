package org.gms.extension.runtime;

import org.gms.extension.api.HostCommandRegistry;
import org.gms.extension.api.HostCharacterProvisioner;
import org.gms.extension.api.HostConfig;
import org.gms.extension.api.HostEventBus;
import org.gms.extension.api.HostRuntime;

import java.util.Optional;

/**
 * BeiDou implementation of {@link HostRuntime}. Constructed once per server process.
 */
public final class BeiDouHostRuntime implements HostRuntime {

    private final HostConfig config;
    private final HostEventBus events;
    private final HostCommandRegistry commands;
    private final HostCharacterProvisioner characterProvisioner;

    public BeiDouHostRuntime(HostConfig config, HostEventBus events, HostCommandRegistry commands) {
        this(config, events, commands, null);
    }

    public BeiDouHostRuntime(
            HostConfig config,
            HostEventBus events,
            HostCommandRegistry commands,
            HostCharacterProvisioner characterProvisioner
    ) {
        this.config = config;
        this.events = events;
        this.commands = commands;
        this.characterProvisioner = characterProvisioner;
    }

    @Override
    public HostConfig config() {
        return config;
    }

    @Override
    public HostEventBus events() {
        return events;
    }

    @Override
    public HostCommandRegistry commands() {
        return commands;
    }

    @Override
    public Optional<HostCharacterProvisioner> characterProvisioner() {
        return Optional.ofNullable(characterProvisioner);
    }

    @Override
    public String hostId() {
        return "beidou";
    }
}
