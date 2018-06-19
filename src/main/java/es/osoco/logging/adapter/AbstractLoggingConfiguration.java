package es.osoco.logging.adapter;

import es.osoco.logging.config.LoggingConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base class for {@link LoggingConfiguration} with the registry key.
 */
@EqualsAndHashCode()
@ToString
public abstract class AbstractLoggingConfiguration
    implements LoggingConfiguration {

    /**
     * The registry key.
     */
    @NonNull
    private String registryKey;

    /**
     * Creates a new instance.
     * @param key the registry key.
     */
    protected AbstractLoggingConfiguration(@NonNull final String key) {
        this.registryKey = key;
    }

    /**
     * Specifies the registry key.
     * @param key such key.
     */
    protected final void immutableSetRegistryKey(@NonNull final String key) {
        this.registryKey = key;
    }

    /**
     * Specifies the registry key. Override me if necessary.
     * @param key such key.
     */
    @SuppressWarnings("unused")
    protected void setRegistryKey(@NonNull final String key) {
        immutableSetRegistryKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @NonNull
    public String getRegistryKey() {
        return registryKey;
    }
}
