package es.osoco.logging.impl;

import es.osoco.logging.adapter.LoggingAdapter;

/**
 * Calls #debug(msg) and #isDebugEnabled() on a given adapter.
 */
public class LoggingDebugCall
    implements LoggingCall {

    @Override
    public void log(final LoggingAdapter adapter, final String msg) {
        adapter.debug(msg);
    }

    @Override
    public boolean isEnabled(final LoggingAdapter adapter) {
        return adapter.isDebugEnabled();
    }
}
