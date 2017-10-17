package es.osoco.logging.adapter;

import es.osoco.logging.Logging;
import es.osoco.logging.config.LoggingConfiguration;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Base class for {@link Logging logging} {@link LoggingAdapter adapter} builders.
 * @param <C> the type of the {@link LoggingConfiguration}.
 * @param <L> the type of the {@link LoggingAdapter}.
 */
@ToString
@EqualsAndHashCode
public abstract class AbstractLoggingAdapterBuilder<C extends LoggingConfiguration, L extends LoggingAdapter>
    implements LoggingAdapterBuilder<C, L> {
    /**
     * The registry key.
     */
    private String registryKey;

    /**
     * The logging configuration.
     */
    private C loggingConfiguration;

    /**
     * Creates a new {@code AbstractLoggingAdapterBuilder}.
     * @param regKey the registry key.
     * @param config the {@link LoggingConfiguration}.
     */
    protected AbstractLoggingAdapterBuilder(final String regKey, final C config) {
        immutableSetRegistryKey(regKey);
        immutableSetLoggingConfiguration(config);
    }

    /**
     * Specifies the registry key.
     * @param key the key.
     */
    protected final void immutableSetRegistryKey(final String key) {
        this.registryKey = key;
    }

    /**
     * Specifies the registry key. Override this if necessary.
     * @param key the key.
     */
    @SuppressWarnings("unused")
    protected void setRegistryKey(final String key) {
        immutableSetRegistryKey(key);
    }

    /**
     * Retrieves the registry key.
     * @return such key.
     */
    protected final String immutableGetRegistryKey() {
        return registryKey;
    }

    /**
     * Retrieves the registry key. Override this if necessary.
     * @return such key.
     */
    @Override
    public String getRegistryKey() {
        return immutableGetRegistryKey();
    }

    /**
     * Specifies the {@link LoggingConfiguration} to use.
     * @param config such instance.
     */
    protected final void immutableSetLoggingConfiguration(final C config) {
        this.loggingConfiguration = config;
    }

    /**
     * Specifies the {@link LoggingConfiguration} to use.
     * @param config such instance.
     */
    @SuppressWarnings("unused")
    protected void setLoggingConfiguration(final C config) {
        immutableSetLoggingConfiguration(config);
    }

    /**
     * Retrieves the {@link LoggingConfiguration} to use.
     * @return such instance.
     */
    protected final C immutableGetLoggingConfiguration() {
        return this.loggingConfiguration;
    }

    /**
     * Retrieves the {@link LoggingConfiguration} to use. Override this if necessary.
     * @return such instance.
     */
    @Override
    public C getLoggingConfiguration() {
        return immutableGetLoggingConfiguration();
    }

    /**
     * Builds a new {@link Logging}.
     * @return such adapter.
     */
    @Override
    public L build() {
        return this.build(getLoggingConfiguration());
    }

    /**
     * Builds a new {@link Logging}.
     * @param config the {@link LoggingConfiguration} to use.
     * @return such adapter.
     */
    protected abstract L build(final C config);
}
