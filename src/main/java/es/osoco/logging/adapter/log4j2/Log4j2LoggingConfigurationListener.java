package es.osoco.logging.adapter.log4j2;

import es.osoco.logging.adapter.LoggingAdapterBuilderRegistry;
import es.osoco.logging.config.LoggingConfiguration;
import es.osoco.logging.config.LoggingConfigurationListener;
import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Listener for Log4J2 logging support.
 */
@EqualsAndHashCode
@ToString
public class Log4j2LoggingConfigurationListener
    implements LoggingConfigurationListener {

    @Override
    public void newLoggingConfigurationAvailable(@NonNull final LoggingConfiguration config) {
        if (config instanceof Log4j2LoggingConfiguration) {
            LoggingAdapterBuilderRegistry
                .getInstance()
                .put(
                    config.getRegistryKey(),
                    new Log4j2LoggingAdapterBuilder((Log4j2LoggingConfiguration) config));
        }
    }
}
