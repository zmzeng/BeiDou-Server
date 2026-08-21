package org.gms.extension.runtime;

import org.gms.extension.api.HostConfig;
import org.springframework.core.env.Environment;

/**
 * Reads extension keys from Spring Environment ({@code application.yml}).
 */
public final class BeiDouHostConfig implements HostConfig {

    public static final String PLUGINS_DIR = "extension.plugins-dir";
    public static final String PLUGINS_ENABLED = "extension.plugins-enabled";

    private final Environment environment;

    public BeiDouHostConfig(Environment environment) {
        this.environment = environment;
    }

    @Override
    public boolean getBool(String key, boolean defaultValue) {
        Boolean value = environment.getProperty(key, Boolean.class);
        return value != null ? value : defaultValue;
    }

    @Override
    public int getInt(String key, int defaultValue) {
        Integer value = environment.getProperty(key, Integer.class);
        return value != null ? value : defaultValue;
    }

    @Override
    public String getString(String key, String defaultValue) {
        String value = environment.getProperty(key);
        return value != null && !value.isBlank() ? value : defaultValue;
    }
}
