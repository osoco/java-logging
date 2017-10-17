package es.osoco.logging.impl;

import es.osoco.logging.adapter.LoggingAdapter;

/**
 * Calls #trace(msg) and #isTraceEnabled() on a given adapter.
 */
public class LoggingTraceCall
    implements LoggingCall {

    @Override
    public void log(final LoggingAdapter adapter, final String msg) {
        adapter.trace(msg);
    }

    @Override
    public boolean isEnabled(final LoggingAdapter adapter) {
        return adapter.isTraceEnabled();
    }
}
