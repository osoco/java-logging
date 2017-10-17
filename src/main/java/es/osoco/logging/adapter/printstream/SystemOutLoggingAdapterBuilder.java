package es.osoco.logging.adapter.printstream;

import es.osoco.logging.adapter.LoggingAdapterBuilder;

/**
 * {@link LoggingAdapterBuilder} for {@code System.out}.
 */
public class SystemOutLoggingAdapterBuilder
    extends PrintStreamLoggingAdapterBuilder {

    @SuppressWarnings("unused")
    public SystemOutLoggingAdapterBuilder() {
        this("System.out", new PrintStreamLoggingConfiguration("System.out", System.out));
    }

    public SystemOutLoggingAdapterBuilder(final String registryKey, final PrintStreamLoggingConfiguration config) {
        super(registryKey, config);
    }
}
