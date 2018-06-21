package es.osoco.logging.adapter.jul;

import es.osoco.logging.adapter.LoggingAdapterBuilderRegistry;
import es.osoco.logging.config.LoggingConfiguration;
import es.osoco.logging.config.LoggingConfigurationListener;
import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Listener for JDK4 logging support.
 */
@EqualsAndHashCode
@ToString
public class JulLoggingConfigurationListener
    implements LoggingConfigurationListener {

    @Override
    public void newLoggingConfigurationAvailable(@NonNull final LoggingConfiguration config) {
        if (config instanceof JulLoggingConfiguration) {
            LoggingAdapterBuilderRegistry
                .getInstance()
                .put(
                    config.getRegistryKey(),
                    new JulLoggingAdapterBuilder((JulLoggingConfiguration) config));
        }
    }
}
