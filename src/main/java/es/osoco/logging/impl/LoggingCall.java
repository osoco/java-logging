package es.osoco.logging.impl;

import es.osoco.logging.adapter.LoggingAdapter;

/**
 * Function implementations to delegate calls to given adapters.
 */
public interface LoggingCall {
    /**
     * Delegates the logging operation on given adapter.
     * @param adapter the adapter.
     * @param msg the message to log.
     */
    void log(LoggingAdapter adapter, String msg);

    /**
     * Delegates given adapter the responsibility to check whether a given log level is enabled.
     * @param adapter the adapter.
     * @return {@code true} if it's enabled, {@code false} otherwise.
     */
    boolean isEnabled(LoggingAdapter adapter);
}
