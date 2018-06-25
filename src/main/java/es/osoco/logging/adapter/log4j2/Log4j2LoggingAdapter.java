package es.osoco.logging.adapter.log4j2;

import es.osoco.logging.adapter.AbstractLoggingAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Support for Log4J (2) logging.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class Log4j2LoggingAdapter
    extends AbstractLoggingAdapter<Log4j2LoggingConfiguration> {

    /**
     * The Log4J2 logger.
     * @param configuration the {@link Log4j2LoggingConfiguration configuration}.
     */
    public Log4j2LoggingAdapter(@NonNull final Log4j2LoggingConfiguration configuration) {
        super(configuration);
    }

    /**
     * Retrieves the {@link Logger} instance for given category.
     * @param category the logging category.
     * @return the Logger instance.
     */
    protected Logger retrieveLogger(@Nullable final String category) {
        return (category == null) ? LogManager.getLogger() : LogManager.getLogger(category);
    }

    @Override
    protected void logError(@Nullable final String category, @NonNull final String msg) {
        retrieveLogger(category).error(msg);
    }

    @Override
    protected void logError(@Nullable final String category, @NonNull final String msg, @NonNull final Throwable error) {
        retrieveLogger(category).error(msg, error);
    }

    @Override
    protected void logWarn(@Nullable final String category, @NonNull final String msg) {
        retrieveLogger(category).warn(msg);
    }

    @Override
    protected void logWarn(@Nullable final String category, @NonNull final String msg, @NonNull final Throwable error) {
        retrieveLogger(category).warn(msg, error);
    }

    @Override
    protected void logInfo(@Nullable final String category, @NonNull final String msg) {
        retrieveLogger(category).info(msg);
    }

    @Override
    protected void logInfo(@Nullable final String category, @NonNull final String msg, @NonNull final Throwable error) {
        retrieveLogger(category).info(msg, error);
    }

    @Override
    protected void logDebug(@Nullable final String category, @NonNull final String msg) {
        retrieveLogger(category).debug(msg);
    }

    @Override
    protected void logDebug(@Nullable final String category, @NonNull final String msg, @NonNull final Throwable error) {
        retrieveLogger(category).debug(msg, error);
    }

    @Override
    protected void logTrace(@Nullable final String category, @NonNull final String msg) {
        retrieveLogger(category).trace(msg);
    }

    @Override
    protected void logTrace(@Nullable final String category, @NonNull final String msg, @NonNull final Throwable error) {
        retrieveLogger(category).trace(msg, error);
    }
}
