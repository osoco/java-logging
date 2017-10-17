package es.osoco.logging.adapter;

import es.osoco.logging.config.LoggingConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for {@link LoggingAdapterBuilder}s.
 */
@SuppressWarnings("unused")
public class LoggingAdapterBuilderRegistry {
    /**
     * The underlying logging configuration map.
     */
    private Map<String, ? extends LoggingAdapterBuilder<?, ?>> map = new HashMap<>();

    /**
     * Singleton implementation to avoid double-check locking.
     */
    protected static final class LoggingAdapterBuilderRegistrySingletonContainer {
        public static final LoggingAdapterBuilderRegistry SINGLETON = new LoggingAdapterBuilderRegistry();
    }

    /**
     * Default constructor to avoid public instantiation.
     */
    protected LoggingAdapterBuilderRegistry() {
    }

    /**
     * Adds a new adapter builder.
     * @param builder the builder.
     */
    protected void registerBuilder(final LoggingAdapterBuilder<?, ?> builder) {
        immutableGetMap().put(builder.getRegistryKey(), builder);
    }

    /**
     * Retrieves the {@code LoggingAdapterBuilderRegistry}.
     * @return such instance.
     */
    public static LoggingAdapterBuilderRegistry getInstance() {
        return LoggingAdapterBuilderRegistrySingletonContainer.SINGLETON;
    }

    /**
     * Retrieves the underlying map.
     * @param <T> the type of the values.
     * @return such map.
     */
    @SuppressWarnings("unchecked")
    protected final <T extends LoggingAdapterBuilder<?, ?>> Map<String, T> immutableGetMap() {
        return (Map<String, T>) this.map;
    }

    /**
     * Retrieves the {@link LoggingAdapterBuilder} for given key.
     * @param key the key.
     * @param <T> the type of the values.
     * @return the associated configuration.
     */
    @SuppressWarnings("unchecked")
    public <T extends LoggingAdapterBuilder> T get(final String key) {
        return (T) immutableGetMap().get(key);
    }

    /**
     * Specifies a new {@link LoggingConfiguration}.
     * @param key the key.
     * @param builder the builder.
     * @param <T> the type of the builder.
     */
    @SuppressWarnings("unchecked")
    public <T extends LoggingAdapterBuilder> void put(final String key, final T builder) {
        immutableGetMap().put(key, builder);
    }
}
