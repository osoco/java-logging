package es.osoco.logging.impl;

import es.osoco.logging.adapter.LoggingAdapter;

/**
 * Calls #error(msg) and #isErrorEnabled() on a given adapter.
 */
public class LoggingErrorCall
    implements LoggingCall {

    @Override
    public void log(LoggingAdapter adapter, String msg) {
        adapter.error(msg);
    }

    @Override
    public boolean isEnabled(final LoggingAdapter adapter) {
        return adapter.isErrorEnabled();
    }
}
