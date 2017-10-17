package es.osoco.logging.adapter.printstream;

import es.osoco.logging.adapter.LoggingAdapterBuilderRegistry;
import es.osoco.logging.config.LoggingConfiguration;
import es.osoco.logging.config.LoggingConfigurationListener;

/**
 * Receives notifications of new {@link LoggingConfiguration}s. It creates
 * a builder if the configuration corresponds to {@code System.err}.
 */
@SuppressWarnings("unused")
public class SystemErrLoggingConfigurationListener
    implements LoggingConfigurationListener {

    @Override
    public void newLoggingConfigurationAvailable(final LoggingConfiguration config) {
        if (   (config instanceof PrintStreamLoggingConfiguration)
            && ("System.err".equals(config.getRegistryKey()))) {
            LoggingAdapterBuilderRegistry
                .getInstance()
                .put(
                    config.getRegistryKey(),
                    new SystemOutLoggingAdapterBuilder(
                        config.getRegistryKey(), (PrintStreamLoggingConfiguration) config));
        }
    }
}
