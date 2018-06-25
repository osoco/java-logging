/*
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package es.osoco.logging.adapter;

import es.osoco.logging.LoggingContext;
import es.osoco.logging.config.LoggingConfiguration;
import es.osoco.logging.impl.ThreadLocalLoggingContext;
import me.nallar.whocalled.WhoCalled;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Base class for logging adapters.
 * @param <LC> the {@link LoggingConfiguration} type.
 */
@ToString
@EqualsAndHashCode
@Getter
public abstract class AbstractLoggingAdapter<LC extends LoggingConfiguration>
    implements LoggingAdapter<LC> {

    /**
     * The logging context.
     */
    @Nullable
    private LoggingContext loggingContext;

    /**
     * The logging configuration.
     */
    private LC configuration;

    /**
     * Whether "error" is enabled.
     */
    private boolean errorEnabled;

    /**
     * Whether "error" is enabled for a given category.
     */
    private Map<String, Boolean> errorEnabledForCategory = new HashMap<>();

    /**
     * Whether "warn" is enabled.
     */
    private boolean warnEnabled;

    /**
     * Whether "warn" is enabled for a given category.
     */
    private Map<String, Boolean> warnEnabledForCategory = new HashMap<>();

    /**
     * Whether "info" is enabled.
     */
    private boolean infoEnabled;

    /**
     * Whether "info" is enabled for a given category.
     */
    private Map<String, Boolean> infoEnabledForCategory = new HashMap<>();

    /**
     * Whether "debug" is enabled;
     */
    private boolean debugEnabled;

    /**
     * Whether "debug" is enabled for a given category.
     */
    private Map<String, Boolean> debugEnabledForCategory = new HashMap<>();

    /**
     * Whether "trace" is enabled;
     */
    private boolean traceEnabled;

    /**
     * Whether "trace" is enabled for a given category.
     */
    private Map<String, Boolean> traceEnabledForCategory = new HashMap<>();

    /**
     * Whether the log levels have been customized explicitly.
     */
    private boolean levelsCustomized;

    /**
     * Creates a new {@link LoggingAdapter} with given configuration.
     * @param config the {@link LoggingConfiguration}.
     */
    protected AbstractLoggingAdapter(@NonNull final LC config) {
        this.configuration = config;
        this.loggingContext = null;
    }

    /**
     * Specifies the {@link LoggingContext}.
     * @param ctx such context.
     */
    protected final void immutableSetLoggingContext(@Nullable final LoggingContext ctx) {
        this.loggingContext = ctx;
    }

    /**
     * Specifies the {@link LoggingContext}. Override me if necessary.
     * @param ctx such context.
     */
    protected void setLoggingContext(@Nullable final LoggingContext ctx) {
        immutableSetLoggingContext(ctx);
    }

    /**
     * Retrieves the {@link LoggingContext}.
     * @return such context.
     */
    @Nullable
    protected final LoggingContext immutableGetLoggingContext() {
        return this.loggingContext;
    }

    /**
     * Retrieves the {@link LoggingContext}. Override me if necessary.
     * @return such context.
     */
    @Override
    @NonNull
    public LoggingContext getLoggingContext() {
        @NonNull final LoggingContext result;

        @Nullable final LoggingContext aux = immutableGetLoggingContext();

        if (aux == null) {
            result = new ThreadLocalLoggingContext();
            setLoggingContext(result);
        } else {
            result = aux;
        }

        return result;
    }

    /**
     * Specifies the logging configuration.
     * @param config such instance.
     */
    protected final void immutableSetLoggingConfiguration(@NonNull final LC config) {
        this.configuration = config;
    }

    /**
     * Specifies the logging configuration. Override me if necessary.
     * @param config such instance.
     */
    @SuppressWarnings("unused")
    protected void setLoggingConfiguration(@NonNull final LC config) {
        immutableSetLoggingConfiguration(config);
    }

    /**
     * Retrieves the logging configuration.
     * @return such instance.
     */
    @NonNull
    protected final LC immutableGetLoggingConfiguration() {
        return this.configuration;
    }

    /**
     * Retrieves the logging configuration. Override me if necessary.
     * @return such instance.
     */
    @SuppressWarnings("unused")
    @NonNull
    public LC getLoggingConfiguration() {
        return immutableGetLoggingConfiguration();
    }

    @Override
    @SuppressWarnings("unused")
    public void setErrorEnabled(final boolean flag) {
        this.errorEnabled = flag;
    }

    @Override
    public boolean isErrorEnabled() {
        return isErrorEnabled(getLevelsCustomized(), getLoggingConfiguration());
    }

    @Override
    @SuppressWarnings("unused")
    public void setErrorEnabled(@NonNull final String category, final boolean flag) {
        this.errorEnabledForCategory.put(category, flag);
    }

    @Override
    public boolean isErrorEnabled(@NonNull final String category) {
        final boolean result;

        @Nullable final Boolean categoryEnabled = this.errorEnabledForCategory.get(category);

        if (categoryEnabled == null) {
            result = isErrorEnabled();
        } else {
            result = categoryEnabled;
        }

        return result;
    }

    /**
     * Checks whether the error level is enabled or not.
     * @param customized whether the log levels have been customized explicitly or not.
     * @param conf the {@link LoggingConfiguration}
     * @return such behavior.
     */
    protected boolean isErrorEnabled(final boolean customized, @NonNull final LoggingConfiguration conf) {
        return (this.errorEnabled && customized) || conf.isErrorEnabledByDefault();
    }

    /**
     * Specifies whether the "warn" is enabled.
     * @param flag the new setting.
     */
    @SuppressWarnings("unused")
    public void setWarnEnabled(final boolean flag) {
        this.warnEnabled = flag;
    }

    @Override
    @SuppressWarnings("unused")
    public void setWarnEnabled(@NonNull final String category, final boolean flag) {
        this.warnEnabledForCategory.put(category, flag);
    }

    @Override
    public boolean isWarnEnabled() {
        return isWarnEnabled(getLevelsCustomized(), getLoggingConfiguration());
    }

    @Override
    public boolean isWarnEnabled(@NonNull final String category) {
        final boolean result;

        @Nullable final Boolean categoryEnabled = this.warnEnabledForCategory.get(category);

        if (categoryEnabled == null) {
            result = isWarnEnabled();
        } else {
            result = categoryEnabled;
        }

        return result;
    }

    /**
     * Checks whether the warn level is enabled or not.
     * @param customized whether the log levels have been customized explicitly or not.
     * @param conf the {@link LoggingConfiguration}
     * @return such behavior.
     */
    protected boolean isWarnEnabled(final boolean customized, @NonNull final LoggingConfiguration conf) {
        return (this.warnEnabled && customized) || conf.isWarnEnabledByDefault();
    }

    /**
     * Specifies whether the "info" is enabled.
     * @param flag the new setting.
     */
    @SuppressWarnings("unused")
    @Override
    public void setInfoEnabled(final boolean flag) {
        this.infoEnabled = flag;
    }

    @Override
    @SuppressWarnings("unused")
    public void setInfoEnabled(@NonNull final String category, final boolean flag) {
        this.infoEnabledForCategory.put(category, flag);
    }

    @Override
    public boolean isInfoEnabled() {
        return isInfoEnabled(getLevelsCustomized(), getLoggingConfiguration());
    }

    @Override
    public boolean isInfoEnabled(@NonNull final String category) {
        final boolean result;

        @Nullable final Boolean categoryEnabled = this.infoEnabledForCategory.get(category);

        if (categoryEnabled == null) {
            result = isInfoEnabled();
        } else {
            result = categoryEnabled;
        }

        return result;
    }

    /**
     * Checks whether the info level is enabled or not.
     * @param customized whether the log levels have been customized explicitly or not.
     * @param conf the {@link LoggingConfiguration}
     * @return such behavior.
     */
    protected boolean isInfoEnabled(final boolean customized, @NonNull final LoggingConfiguration conf) {
        return (this.infoEnabled && customized) || conf.isInfoEnabledByDefault();
    }

    /**
     * Specifies whether the "debug" is enabled.
     * @param flag the new setting.
     */
    @SuppressWarnings("unused")
    @Override
    public void setDebugEnabled(final boolean flag) {
        this.debugEnabled = flag;
    }

    @Override
    @SuppressWarnings("unused")
    public void setDebugEnabled(@NonNull final String category, final boolean flag) {
        this.debugEnabledForCategory.put(category, flag);
    }

    @Override
    public boolean isDebugEnabled() {
        return isDebugEnabled(getLevelsCustomized(), getLoggingConfiguration());
    }

    @Override
    public boolean isDebugEnabled(@NonNull final String category) {
        final boolean result;

        @Nullable final Boolean categoryEnabled = this.debugEnabledForCategory.get(category);

        if (categoryEnabled == null) {
            result = isDebugEnabled();
        } else {
            result = categoryEnabled;
        }

        return result;
    }

    /**
     * Checks whether the debug level is enabled or not.
     * @param customized whether the log levels have been customized explicitly or not.
     * @param conf the {@link LoggingConfiguration}
     * @return such behavior.
     */
    protected boolean isDebugEnabled(final boolean customized, @NonNull final LoggingConfiguration conf) {
        return (this.debugEnabled && customized) || conf.isDebugEnabledByDefault();
    }

    /**
     * Specifies whether the "trace" is enabled.
     * @param flag the new setting.
     */
    @SuppressWarnings("unused")
    @Override
    public void setTraceEnabled(final boolean flag) {
        this.traceEnabled = flag;
    }

    @Override
    @SuppressWarnings("unused")
    public void setTraceEnabled(@NonNull final String category, final boolean flag) {
        this.traceEnabledForCategory.put(category, flag);
    }

    @Override
    public boolean isTraceEnabled() {
        return isTraceEnabled(getLevelsCustomized(), getLoggingConfiguration());
    }

    @Override
    public boolean isTraceEnabled(@NonNull final String category) {
        final boolean result;

        @Nullable final Boolean categoryEnabled = this.traceEnabledForCategory.get(category);

        if (categoryEnabled == null) {
            result = isTraceEnabled();
        } else {
            result = categoryEnabled;
        }

        return result;
    }

    /**
     * Checks whether the trace level is enabled or not.
     * @param customized whether the log levels have been customized explicitly or not.
     * @param conf the {@link LoggingConfiguration}
     * @return such behavior.
     */
    protected boolean isTraceEnabled(final boolean customized, @NonNull final LoggingConfiguration conf) {
        return (this.traceEnabled && customized) || conf.isTraceEnabledByDefault();
    }

    /**
     * Specifies whether the log levels have been customized or not.
     * @param flag such information.
     */
    protected void setLevelsCustomized(final boolean flag) {
        this.levelsCustomized = flag;
    }

    /**
     * Retrieves whether the log levels have been customized or not.
     * @return such information.
     */
    public boolean getLevelsCustomized() {
        return this.levelsCustomized;
    }

    /**
     * Retrieves the default category, based on the calling class.
     * @return the category.
     */
    @Nullable
    protected String retrieveDefaultCategory() {
        @Nullable String result = null;

        for (int i=1; ; i++) {
            try {
                @NonNull final String aux = WhoCalled.$.getCallingClass(i).getName();
                if (validCategory(aux)) {
                    result = aux;
                    break;
                }
            } catch (@NonNull final Throwable throwable) {
                break;
            }
        }
        return result;
    }

    /**
     * Checks whether given category is valid or should be discarded.
     * @param category the category to check.
     * @return {@code true} if the category is valid; {@code false} otherwise.
     */
    protected boolean validCategory(@NonNull final String category) {
        return (!category.startsWith("es.osoco.logging") && !category.startsWith("org.codehaus.groovy"));
    }

    @Override
    public void error(@NonNull final String msg) {
        if (isErrorEnabled()) {
            logError(retrieveDefaultCategory(), msg);
        }
    }

    @Override
    public void error(@NonNull final String category, @NonNull final String msg) {
        if (isErrorEnabled(category)) {
            logError(category, msg);
        }
    }

    @Override
    public void error(@NonNull final String msg, @NonNull final Throwable error) {
        if (isErrorEnabled()) {
            logError(retrieveDefaultCategory(), msg, error);
        }
    }

    @Override
    public void error(@NonNull final String category, @NonNull final String msg, @NonNull final Throwable error) {
        if (isErrorEnabled(category)) {
            logError(category, msg, error);
        }
    }

    /**
     * Logs an "error" message in given category.
     * @param category the category.
     * @param msg the message.
     */
    protected abstract void logError(@Nullable String category, @NonNull String msg);

    /**
     * Logs an "error" error in given category.
     * @param category the category.
     * @param msg the message.
     * @param error the error.
     */
    protected abstract void logError(@Nullable String category, @NonNull String msg, @NonNull Throwable error);

    @Override
    public void warn(@NonNull final String msg) {
        if (isWarnEnabled()) {
            logWarn(retrieveDefaultCategory(), msg);
        }
    }

    @Override
    public void warn(@NonNull final String category, @NonNull final String msg) {
        if (isWarnEnabled(category)) {
            logWarn(category, msg);
        }
    }

    @Override
    public void warn(@NonNull final String msg, @NonNull final Throwable error) {
        if (isWarnEnabled()) {
            logWarn(retrieveDefaultCategory(), msg, error);
        }
    }

    @Override
    public void warn(@NonNull final String category, @NonNull final String msg, @NonNull final Throwable error) {
        if (isWarnEnabled(category)) {
            logWarn(category, msg, error);
        }
    }

    /**
     * Logs a "warn" message in given category.
     * @param category the category.
     * @param msg the message.
     */
    protected abstract void logWarn(@Nullable String category, @NonNull String msg);

    /**
     * Logs a "warn" error in given category.
     * @param category the category.
     * @param msg the message.
     * @param error the error.
     */
    protected abstract void logWarn(@Nullable String category, @NonNull String msg, @NonNull Throwable error);

    @Override
    public void info(@NonNull final String msg) {
        if (isInfoEnabled()) {
            logInfo(retrieveDefaultCategory(), msg);
        }
    }

    @Override
    public void info(@NonNull final String category, @NonNull final String msg) {
        if (isInfoEnabled(category)) {
            logInfo(category, msg);
        }
    }

    @Override
    public void info(@NonNull final String msg, @NonNull final Throwable error) {
        if (isInfoEnabled()) {
            logInfo(retrieveDefaultCategory(), msg, error);
        }
    }

    @Override
    public void info(@NonNull final String category, @NonNull final String msg, @NonNull final Throwable error) {
        if (isInfoEnabled(category)) {
            logInfo(category, msg, error);
        }
    }

    /**
     * Logs an "info" message in given category.
     * @param category the category.
     * @param msg the message.
     */
    protected abstract void logInfo(@Nullable String category, @NonNull String msg);

    /**
     * Logs an "info" error in given category.
     * @param category the category.
     * @param msg the message.
     * @param error the error.
     */
    protected abstract void logInfo(@Nullable String category, @NonNull String msg, @NonNull final Throwable error);

    @Override
    public void debug(@NonNull final String msg) {
        if (isDebugEnabled()) {
            logDebug(retrieveDefaultCategory(), msg);
        }
    }

    @Override
    public void debug(@NonNull String category, @NonNull final String msg) {
        if (isDebugEnabled(category)) {
            logDebug(category, msg);
        }
    }

    @Override
    public void debug(@NonNull final String msg, @NonNull final Throwable error) {
        if (isDebugEnabled()) {
            logDebug(retrieveDefaultCategory(), msg, error);
        }
    }

    @Override
    public void debug(@NonNull String category, @NonNull final String msg, @NonNull final Throwable error) {
        if (isDebugEnabled(category)) {
            logDebug(category, msg, error);
        }
    }

    /**
     * Logs a "debug" message in given category.
     * @param category the category.
     * @param msg the message.
     */
    protected abstract void logDebug(@Nullable String category, @NonNull String msg);

    /**
     * Logs a "debug" error in given category.
     * @param category the category.
     * @param msg the message.
     * @param error the error.
     */
    protected abstract void logDebug(@Nullable String category, @NonNull String msg, @NonNull final Throwable error);

    @Override
    public void trace(@NonNull final String msg) {
        if (isTraceEnabled()) {
            logTrace(retrieveDefaultCategory(), msg);
        }
    }

    @Override
    public void trace(@NonNull final String category, @NonNull final String msg) {
        if (isTraceEnabled(category)) {
            logTrace(category, msg);
        }
    }

    @Override
    public void trace(@NonNull final String msg, @NonNull final Throwable error) {
        if (isTraceEnabled()) {
            logTrace(retrieveDefaultCategory(), msg, error);
        }
    }

    @Override
    public void trace(@NonNull final String category, @NonNull final String msg, @NonNull final Throwable error) {
        if (isTraceEnabled(category)) {
            logTrace(category, msg, error);
        }
    }

    /**
     * Logs a "trace" message in given category.
     * @param category the category.
     * @param msg the message.
     */
    protected abstract void logTrace(@Nullable String category, @NonNull String msg);

    /**
     * Logs a "trace" error in given category.
     * @param category the category.
     * @param msg the message.
     * @param error the error.
     */
    protected abstract void logTrace(@Nullable String category, @NonNull String msg, @NonNull Throwable error);

    /**
     * Builds a message prefix based on given category.
     * @param category the category.
     * @return the message prefix.
     * Example:
     * buildCategoryPrefix("com.foo.bar") -> "[com.foo.bar]:"
     * buildCategoryPrefix(null) -> ""
     */
    @NonNull
    protected String buildCategoryPrefix(@Nullable final String category) {
        return (category == null) ? "" : "[" + category + "]:";
    }

    /**
     * Converts given error to a string.
     * @param error the error.
     * @return the text.
     */
    @NonNull
    protected String toString(@NonNull final Throwable error) {
        @NonNull final StringBuilder result = new StringBuilder();

        @Nullable Throwable cause = error;

        do {
            for (@NonNull final StackTraceElement trace : cause.getStackTrace()) {
                result.append(trace.toString());
                result.append("\n");
            }
            cause = cause.getCause();
            if (cause != null) {
                result.append("Caused by:\n");
            }
        } while (cause != null);

        return result.toString();
    }
}
