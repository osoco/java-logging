package es.osoco.logging.adapter.printstream;

import es.osoco.logging.adapter.AbstractLoggingAdapter;
import es.osoco.logging.adapter.LoggingAdapter;

import java.io.PrintStream;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * {@link LoggingAdapter} for {@link PrintStream}s, such as {@code System.out}.
 */
@ToString
@EqualsAndHashCode(callSuper=true)
public class PrintStreamLoggingAdapter
    extends AbstractLoggingAdapter<PrintStreamLoggingConfiguration> {

    /**
     * Creates a logging configuration for given {@link PrintStreamLoggingConfiguration}.
     * @param config such configuration.
     */
    public PrintStreamLoggingAdapter(final PrintStreamLoggingConfiguration config) {
        super(config);
    }

    /**
     * Logs to the underlying {@link PrintStream}.
     * @param msg the message to log.
     */
    protected void logToPrintStream(final String msg) {
        getLoggingConfiguration().getPrintStream().println(msg);
    }

    @Override
    protected void logError(final String msg) {
        logToPrintStream(msg);
    }

    @Override
    protected void logWarn(final String msg) {
        logToPrintStream(msg);
    }

    @Override
    protected void logInfo(final String msg) {
        logToPrintStream(msg);
    }

    @Override
    protected void logDebug(final String msg) {
        logToPrintStream(msg);
    }

    @Override
    protected void logTrace(final String msg) {
        logToPrintStream(msg);
    }
}
