package es.osoco.logging.adapter;

import es.osoco.logging.Logging;
import es.osoco.logging.config.LoggingConfiguration;

/**
 * Knows how to build specific {@link LoggingAdapter}s.
 * @param <C> the type of the associated {@link LoggingConfiguration}.
 * @param <L> the type of the {@link LoggingAdapter} this builder knows how to build.
 */
public interface LoggingAdapterBuilder<C extends LoggingConfiguration, L extends LoggingAdapter> {

    /**
     * Retrieves the registry key. Override this if necessary.
     * @return such key.
     */
    String getRegistryKey();

    /**
     * Retrieves the {@link LoggingConfiguration} to use. Override this if necessary.
     * @return such instance.
     */
    C getLoggingConfiguration();

    /**
     * Builds a new {@link Logging}.
     * @return such adapter.
     */
    L build();
}
