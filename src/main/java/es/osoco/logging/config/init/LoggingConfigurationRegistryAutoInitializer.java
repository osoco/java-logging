package es.osoco.logging.config.init;

import es.osoco.logging.adapter.printstream.PrintStreamLoggingConfiguration;
import es.osoco.logging.config.LoggingConfiguration;
import es.osoco.logging.annotations.LoggingConfigurationProducer;

/**
 * Automatically initializes System.err and System.out-based logging configurations.
 */
@SuppressWarnings("unused")
public class LoggingConfigurationRegistryAutoInitializer {

    /**
     * Required public constructor (gets instantiated via reflection).
     */
    public LoggingConfigurationRegistryAutoInitializer() {
        super();
    }

    @SuppressWarnings("unused")
    @LoggingConfigurationProducer(key = "System.out")
    public LoggingConfiguration produceSystemOutLoggingConfiguration() {
        return new PrintStreamLoggingConfiguration("System.out", System.out);
    }

    @SuppressWarnings("unused")
    @LoggingConfigurationProducer(key = "System.err")
    public LoggingConfiguration produceSystemErrLoggingConfiguration() {
        return new PrintStreamLoggingConfiguration("System.err", System.err);
    }
}
