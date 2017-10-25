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
     * Whether "warn" is enabled.
     */
    private boolean warnEnabled;

    /**
     * Whether "info" is enabled.
     */
    private boolean infoEnabled;

    /**
     * Whether "debug" is enabled;
     */
    private boolean debugEnabled;

    /**
     * Whether "trace" is enabled;
     */
    private boolean traceEnabled;

    /**
     * Creates a new {@link LoggingAdapter} with given configuration.
     * @param config the {@link LoggingConfiguration}.
     */
    protected AbstractLoggingAdapter(final LC config) {
        immutableSetLoggingConfiguration(config);
    }

    /**
     * Specifies the {@link LoggingContext}.
     * @param ctx such context.
     */
    protected final void immutableSetLoggingContext(final LoggingContext ctx) {
        this.loggingContext = ctx;
    }

    /**
     * Specifies the {@link LoggingContext}. Override me if necessary.
     * @param ctx such context.
     */
    protected void setLoggingContext(final LoggingContext ctx) {
        immutableSetLoggingContext(ctx);
    }

    /**
     * Retrieves the {@link LoggingContext}.
     * @return such context.
     */
    protected final LoggingContext immutableGetLoggingContext() {
        return this.loggingContext;
    }

    /**
     * Retrieves the {@link LoggingContext}. Override me if necessary.
     * @return such context.
     */
    @Override
    public LoggingContext getLoggingContext() {
        final LoggingContext result;

        final LoggingContext aux = immutableGetLoggingContext();

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
    protected final void immutableSetLoggingConfiguration(final LC config) {
        this.configuration = config;
    }

    /**
     * Specifies the logging configuration. Override me if necessary.
     * @param config such instance.
     */
    @SuppressWarnings("unused")
    protected void setLoggingConfiguration(final LC config) {
        immutableSetLoggingConfiguration(config);
    }

    /**
     * Retrieves the logging configuration.
     * @return such instance.
     */
    protected final LC immutableGetLoggingConfiguration() {
        return this.configuration;
    }

    /**
     * Retrieves the logging configuration. Override me if necessary.
     * @return such instance.
     */
    @SuppressWarnings("unused")
    public LC getLoggingConfiguration() {
        return immutableGetLoggingConfiguration();
    }

    /**
     * Specifies whether the "error" is enabled.
     * @param flag the new setting.
     */
    @SuppressWarnings("unused")
    protected void setErrorEnabled(final boolean flag) {
        this.errorEnabled = flag;
    }

    @Override
    public boolean isErrorEnabled() {
        return this.errorEnabled;
    }

    /**
     * Specifies whether the "warn" is enabled.
     * @param flag the new setting.
     */
    @SuppressWarnings("unused")
    protected void setWarnEnabled(final boolean flag) {
        this.warnEnabled = flag;
    }

    @Override
    public boolean isWarnEnabled() {
        return this.warnEnabled;
    }

    /**
     * Specifies whether the "info" is enabled.
     * @param flag the new setting.
     */
    @SuppressWarnings("unused")
    protected void setInfoEnabled(final boolean flag) {
        this.infoEnabled = flag;
    }

    @Override
    public boolean isInfoEnabled() {
        return this.infoEnabled;
    }

    /**
     * Specifies whether the "debug" is enabled.
     * @param flag the new setting.
     */
    @SuppressWarnings("unused")
    protected void setDebugEnabled(final boolean flag) {
        this.debugEnabled = flag;
    }

    @Override
    public boolean isDebugEnabled() {
        return this.debugEnabled;
    }

    /**
     * Specifies whether the "trace" is enabled.
     * @param flag the new setting.
     */
    @SuppressWarnings("unused")
    protected void setTraceEnabled(final boolean flag) {
        this.traceEnabled = flag;
    }

    @Override
    public boolean isTraceEnabled() {
        return this.traceEnabled;
    }

    @Override
    public void error(final String msg) {
        if (isErrorEnabled()) {
            logError(msg);
        }
    }

    /**
     * Logs an "error" message.
     * @param msg the message.
     */
    protected abstract void logError(String msg);

    @Override
    public void warn(final String msg) {
        if (isWarnEnabled()) {
            logWarn(msg);
        }
    }

    /**
     * Logs a "warn" message.
     * @param msg the message.
     */
    protected abstract void logWarn(String msg);

    @Override
    public void info(final String msg) {
        if (isInfoEnabled()) {
            logInfo(msg);
        }
    }

    /**
     * Logs an "info" message.
     * @param msg the message.
     */
    protected abstract void logInfo(String msg);

    @Override
    public void debug(final String msg) {
        if (isDebugEnabled()) {
            logDebug(msg);
        }
    }

    /**
     * Logs a "debug" message.
     * @param msg the message.
     */
    protected abstract void logDebug(String msg);

    @Override
    public void trace(final String msg) {
        if (isTraceEnabled()) {
            logTrace(msg);
        }
    }

    /**
     * Logs a "trace" message.
     * @param msg the message.
     */
    protected abstract void logTrace(String msg);
}
