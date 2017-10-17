package es.osoco.logging.adapter.printstream;

import es.osoco.logging.adapter.AbstractLoggingAdapterBuilder;
import es.osoco.logging.adapter.LoggingAdapter;

import java.io.PrintStream;

/**
 * {@link LoggingAdapter} that delegates logging to a {@link PrintStream}.
 */
public class PrintStreamLoggingAdapterBuilder
    extends AbstractLoggingAdapterBuilder<PrintStreamLoggingConfiguration, PrintStreamLoggingAdapter> {

    /**
     * Creates a new {@code PrintStreamLoggingAdapterBuilder} for given key and {@link PrintStream}.
     * @param key the registry key.
     * @param config the {@link PrintStreamLoggingConfiguration}.
     */
    protected PrintStreamLoggingAdapterBuilder(final String key, final PrintStreamLoggingConfiguration config) {
        super(key, config);
    }

    @Override
    public PrintStreamLoggingAdapter build(final PrintStreamLoggingConfiguration config) {
        return new PrintStreamLoggingAdapter(config);
    }
}
