package es.osoco.logging.adapter.jdk4;

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
public class Jdk4LoggingConfigurationListener
    implements LoggingConfigurationListener {

    @Override
    public void newLoggingConfigurationAvailable(@NonNull final LoggingConfiguration config) {
        if (config instanceof Jdk4LoggingConfiguration) {
            LoggingAdapterBuilderRegistry
                .getInstance()
                .put(
                    config.getRegistryKey(),
                    new Jdk4LoggingAdapterBuilder((Jdk4LoggingConfiguration) config));
        }
    }
}
