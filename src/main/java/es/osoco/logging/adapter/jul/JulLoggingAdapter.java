package es.osoco.logging.adapter.jul;

import es.osoco.logging.adapter.AbstractLoggingAdapter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.logging.Level;
import java.util.logging.Logger;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Logging support for JDK4.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class JulLoggingAdapter
    extends AbstractLoggingAdapter<JulLoggingConfiguration> {

    public JulLoggingAdapter(@NonNull final JulLoggingConfiguration config) {
        super(config);
    }

    /**
     * Retrieves a {@link Logger}.
     * @param category the category.
     * @return the logger.
     */
    protected Logger retrieveLogger(@Nullable final String category) {
        return (category == null) ? Logger.getAnonymousLogger() : Logger.getLogger(category);
    }

    @Override
    public boolean isErrorEnabled(@Nullable final String category) {
        return retrieveLogger(category).isLoggable(Level.SEVERE);
    }

    @Override
    public void setErrorEnabled(@Nullable final String category, final boolean flag) {
    }

    @Override
    protected void logError(@Nullable final String category, @NonNull final String msg) {
        retrieveLogger(category).severe(msg);
    }

    @Override
    protected void logError(@Nullable final String category, @NonNull final String msg, @NonNull final Throwable error) {
        logError(category, msg);
        retrieveLogger(category).severe(toString(error));
    }

    @Override
    public boolean isWarnEnabled(@Nullable final String category) {
        return retrieveLogger(category).isLoggable(Level.WARNING);
    }

    @Override
    public void setWarnEnabled(@Nullable final String category, final boolean flag) {
    }

    @Override
    protected void logWarn(@Nullable final String category, @NonNull final String msg) {
        retrieveLogger(category).warning(msg);
    }

    @Override
    protected void logWarn(@Nullable final String category, @NonNull final String msg, @NonNull final Throwable error) {
        logWarn(category, msg);
        retrieveLogger(category).warning(toString(error));
    }

    @Override
    public void setInfoEnabled(@Nullable final String category, final boolean flag) {
    }

    @Override
    public boolean isInfoEnabled(@Nullable final String category) {
        return retrieveLogger(category).isLoggable(Level.INFO);
    }

    @Override
    protected void logInfo(@Nullable final String category, @NonNull final String msg) {
        retrieveLogger(category).info(msg);
    }

    @Override
    protected void logInfo(@Nullable final String category, @NonNull final String msg, @NonNull final Throwable error) {
        logInfo(category, msg);
        retrieveLogger(category).info(toString(error));
    }

    @Override
    public void setDebugEnabled(@Nullable final String category, final boolean flag) {
    }

    @Override
    public boolean isDebugEnabled(@Nullable final String category) {
        return retrieveLogger(category).isLoggable(Level.FINE);
    }

    @Override
    protected void logDebug(@Nullable final String category, @NonNull final String msg) {
        retrieveLogger(category).fine(msg);
    }

    @Override
    protected void logDebug(@Nullable final String category, @NonNull final String msg, @NonNull final Throwable error) {
        logDebug(category, msg);
        retrieveLogger(category).fine(toString(error));
    }

    @Override
    public void setTraceEnabled(@Nullable final String category, final boolean flag) {
    }

    @Override
    public boolean isTraceEnabled(@Nullable final String category) {
        return retrieveLogger(category).isLoggable(Level.FINER);
    }

    @Override
    protected void logTrace(@Nullable final String category, @NonNull final String msg) {
        retrieveLogger(category).finer(msg);
    }

    @Override
    protected void logTrace(@Nullable final String category, @NonNull final String msg, @NonNull final Throwable error) {
        logTrace(category, msg);
        retrieveLogger(category).finer(toString(error));
    }
}
