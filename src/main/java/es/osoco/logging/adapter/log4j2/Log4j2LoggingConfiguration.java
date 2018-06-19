package es.osoco.logging.adapter.log4j2;

import es.osoco.logging.adapter.AbstractLoggingConfiguration;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Logging configuration for Log4J (2).
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class Log4j2LoggingConfiguration
    extends AbstractLoggingConfiguration {

    public Log4j2LoggingConfiguration() {
        super("log4j");
    }
}
