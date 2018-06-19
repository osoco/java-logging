package es.osoco.logging.adapter.log4j2;

import es.osoco.logging.annotations.LoggingConfigurationProducer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Produces Log4J (2) configurations.
 */
@EqualsAndHashCode
@ToString
public class Log4j2LoggingConfigurationProducer {

    /**
     * Produces a {@link Log4j2LoggingConfiguration}.
     * @return such instance.
     */
    @LoggingConfigurationProducer
    @Nullable
    public Log4j2LoggingConfiguration produceConfiguration() {

        @Nullable Log4j2LoggingConfiguration result;

        try {
            Class.forName("org.apache.logging.log4j.Logger");
            result = new Log4j2LoggingConfiguration();
        } catch (@NonNull final Throwable throwable) {
            result = null;
        }

        return result;
    }
}
