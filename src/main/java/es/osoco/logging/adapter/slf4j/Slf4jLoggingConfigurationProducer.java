package es.osoco.logging.adapter.slf4j;

import es.osoco.logging.annotations.LoggingConfigurationProducer;
import org.checkerframework.checker.nullness.qual.NonNull;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Produces {@link Slf4jLoggingConfiguration} instances.
 */
@EqualsAndHashCode
@ToString
public class Slf4jLoggingConfigurationProducer {

    /**
     * Creates a new {@link Slf4jLoggingConfiguration} instance.
     * @return such instance.
     */
    @LoggingConfigurationProducer
    @NonNull
    @SuppressWarnings("unused")
    public Slf4jLoggingConfiguration produceConfiguration() {
        return new Slf4jLoggingConfiguration();
    }
}
