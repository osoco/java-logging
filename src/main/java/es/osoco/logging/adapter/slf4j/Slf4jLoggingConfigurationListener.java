package es.osoco.logging.adapter.slf4j;

import es.osoco.logging.adapter.LoggingAdapterBuilderRegistry;
import es.osoco.logging.config.LoggingConfiguration;
import es.osoco.logging.config.LoggingConfigurationListener;
import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Listener for {@link Slf4jLoggingConfiguration}s.
 */
@EqualsAndHashCode
@ToString
public class Slf4jLoggingConfigurationListener
    implements LoggingConfigurationListener {

    @Override
    public void newLoggingConfigurationAvailable(@NonNull final LoggingConfiguration config) {
        if (config instanceof Slf4jLoggingConfiguration) {
            LoggingAdapterBuilderRegistry
                .getInstance()
                .put(
                    config.getRegistryKey(),
                    new Slf4jLoggingAdapterBuilder((Slf4jLoggingConfiguration) config));
        }
    }
}
