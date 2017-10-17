package es.osoco.logging.adapter;

import es.osoco.logging.Logging;
import es.osoco.logging.config.LoggingConfiguration;

/**
 * Interface for all {@link Logging} adapters.
 * @param <LC> the type of {@link LoggingConfiguration} this adapter is associated to.
 */
public interface LoggingAdapter<LC extends LoggingConfiguration>
    extends Logging {
    /**
     * Retrieves the {@link LoggingConfiguration}.
     * @return such configuration.
     */
    LC getLoggingConfiguration();
}
