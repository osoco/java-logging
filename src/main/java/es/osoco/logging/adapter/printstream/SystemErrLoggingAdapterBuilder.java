package es.osoco.logging.adapter.printstream;

import es.osoco.logging.adapter.LoggingAdapterBuilder;

/**
 * {@link LoggingAdapterBuilder} for {@code System.err}.
 */
@SuppressWarnings("unused")
public class SystemErrLoggingAdapterBuilder
    extends PrintStreamLoggingAdapterBuilder {

    @SuppressWarnings("unused")
    public SystemErrLoggingAdapterBuilder() {
        this("System.err", new PrintStreamLoggingConfiguration("System.err", System.out));
    }

    public SystemErrLoggingAdapterBuilder(final String registryKey, final PrintStreamLoggingConfiguration config) {
        super(registryKey, config);
    }
}
