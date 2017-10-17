package es.osoco.logging.config;

/**
 * Listens to notifications of new {@link LoggingConfiguration} availability.
 */
@FunctionalInterface
public interface LoggingConfigurationListener {
    /**
     * Gets notified of a new {@link LoggingConfiguration}.
     * @param config the new configuration.
     */
    void newLoggingConfigurationAvailable(LoggingConfiguration config);
}
