package es.osoco.logging.impl;

import es.osoco.logging.adapter.LoggingAdapter;

/**
 * Calls #info(msg) and #isInfoEnabled() on a given adapter.
 */
public class LoggingInfoCall
   implements LoggingCall {

    @Override
    public void log(final LoggingAdapter adapter, final String msg) {
        adapter.info(msg);
    }

    @Override
    public boolean isEnabled(final LoggingAdapter adapter) {
        return adapter.isInfoEnabled();
    }
}
