package es.osoco.logging.adapter.jul;

import es.osoco.logging.adapter.AbstractLoggingConfiguration;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Logging configuration for JDK4 logging.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class JulLoggingConfiguration
    extends AbstractLoggingConfiguration {

    public JulLoggingConfiguration() {
        super("java.util.logging");
    }
}
