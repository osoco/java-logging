package es.osoco.logging.adapter.jdk4;

import es.osoco.logging.adapter.AbstractLoggingConfiguration;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Logging configuration for JDK4 logging.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class Jdk4LoggingConfiguration
    extends AbstractLoggingConfiguration {

    public Jdk4LoggingConfiguration() {
        super("java.util.logging");
    }
}
