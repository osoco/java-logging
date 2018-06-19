package es.osoco.logging.adapter.slf4j;

import es.osoco.logging.adapter.AbstractLoggingConfiguration;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Logging configuration class for SLF4J.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class Slf4jLoggingConfiguration
    extends AbstractLoggingConfiguration {

    /**
     * Creates an empty instance.
     */
    public Slf4jLoggingConfiguration() {
        super("slf4j");
    }
}
