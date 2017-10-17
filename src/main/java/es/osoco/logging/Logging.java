package es.osoco.logging;

/**
 * Provides logging.
 */
public interface Logging {

    /**
     * Logs an "error" message.
     * @param msg the message.
     */
    void error(String msg);

    /**
     * Checks whether "error" logging is enabled.
     * @return such information.
     */
    boolean isErrorEnabled();

    /**
     * Logs a "warn" message.
     * @param msg the message.
     */
    void warn(String msg);

    /**
     * Checks whether "warn" logging is enabled.
     * @return such information.
     */
    boolean isWarnEnabled();

    /**
     * Logs an "info" message.
     * @param msg the message.
     */
    void info(String msg);

    /**
     * Checks whether "info" logging is enabled.
     * @return such information.
     */
    boolean isInfoEnabled();

    /**
     * Logs a "debug" message.
     * @param msg the message.
     */
    void debug(String msg);

    /**
     * Checks whether "debug" logging is enabled.
     * @return such information.
     */
    boolean isDebugEnabled();

    /**
     * Logs a "trace" message.
     * @param msg the message.
     */
    void trace(String msg);

    /**
     * Checks whether "trace" logging is enabled.
     * @return such information.
     */
    boolean isTraceEnabled();

    /**
     * Retrieves the {@link LoggingContext}.
     * @return such context.
     */
    LoggingContext getLoggingContext();
}
