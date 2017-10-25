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
package es.osoco.logging.impl;

import es.osoco.logging.Logging;
import es.osoco.logging.LoggingContext;
import es.osoco.logging.adapter.LoggingAdapter;

import java.util.List;

/**
 * A {@link Logging} composed of some preferred logging mechanisms, and fallback mechanisms should they fail.
 */
public class CompositeLogging
    implements Logging {

    private LoggingContext context = new ThreadLocalLoggingContext();
    private List<LoggingAdapter> preferred;
    private List<LoggingAdapter> fallback;

    /**
     * Creates a new composite logging.
     * @param preferred the preferred adapters.
     * @param fallback the fallback adapter
     */
    public CompositeLogging(final List<LoggingAdapter> preferred, final List<LoggingAdapter> fallback) {
        immutableSetPreferred(preferred);
        immutableSetFallback(fallback);
    }

    /**
     * Specifies the preferred adapters.
     * @param preferred the preferred logging.
     */
    protected final void immutableSetPreferred(final List<LoggingAdapter> preferred) {
        this.preferred = preferred;
    }

    /**
     * Specifies the preferred adapters. Override me if necessary.
     * @param preferred the preferred logging.
     */
    @SuppressWarnings("unused")
    protected void setPreferred(final List<LoggingAdapter> preferred) {
        immutableSetPreferred(preferred);
    }

    /**
     * Retrieves the preferred logging adapters.
     * @return such adapters.
     */
    public List<LoggingAdapter> getPreferred() {
        return this.preferred;
    }

    /**
     * Specifies the fallback adapters.
     * @param fallback the fallback logging.
     */
    protected final void immutableSetFallback(final List<LoggingAdapter> fallback) {
        this.fallback = fallback;
    }

    /**
     * Specifies the fallback adapters. Override me if necessary.
     * @param fallback the fallback logging.
     */
    @SuppressWarnings("unused")
    protected void setFallback(final List<LoggingAdapter> fallback) {
        immutableSetFallback(fallback);
    }

    /**
     * Retrieves the fallback logging adapters.
     * @return such adapters.
     */
    public List<LoggingAdapter> getFallback() {
        return this.fallback;
    }

    /**
     * Logs given msg, using a function interface.
     * @param msg the message to log.
     * @param loggingCallable the callable function.
     */
    protected void log(final String msg, final LoggingCall loggingCallable) {
        boolean fallbackNeeded = false;

        for (LoggingAdapter preferred: getPreferred()) {
            try {
                loggingCallable.log(preferred, msg);
            } catch (final Throwable error) {
                fallbackNeeded = true;
            }
        }

        if (fallbackNeeded) {
            for (LoggingAdapter fallback : getFallback()) {
                try {
                    loggingCallable.log(fallback, msg);
                } catch (final Throwable error) {
                }
            }
        }
    }

    /**
     * Delegates the check regarding whether a level is enabled to given callable.
     * @param loggingCallable the function pointer.
     * @return the outcome of the check.
     */
    protected boolean isEnabled(final LoggingCall loggingCallable) {
        boolean result = true;

        boolean fallbackNeeded = false;

        for (LoggingAdapter preferred: getPreferred()) {
            try {
                result = result && loggingCallable.isEnabled(preferred);
            } catch (final Throwable error) {
                fallbackNeeded = true;
            }
        }

        if (fallbackNeeded) {
            for (LoggingAdapter fallback : getFallback()) {
                try {
                    result = result && loggingCallable.isEnabled(fallback);
                } catch (final Throwable error) {
                }
            }
        }

        return result;
    }

    @Override
    public void error(final String msg) {
        log(msg, new LoggingErrorCall());
    }

    @Override
    public boolean isErrorEnabled() {
        return isEnabled(new LoggingErrorCall());
    }

    @Override
    public void warn(final String msg) {
        log(msg, new LoggingWarnCall());
    }

    @Override
    public boolean isWarnEnabled() {
        return isEnabled(new LoggingWarnCall());
    }

    @Override
    public void info(final String msg) {
        log(msg, new LoggingInfoCall());
    }

    @Override
    public boolean isInfoEnabled() {
        return isEnabled(new LoggingInfoCall());
    }

    @Override
    public void debug(final String msg) {
        log(msg, new LoggingDebugCall());
    }

    @Override
    public boolean isDebugEnabled() {
        return isEnabled(new LoggingDebugCall());
    }

    @Override
    public void trace(final String msg) {
        log(msg, new LoggingTraceCall());
    }

    @Override
    public boolean isTraceEnabled() {
        return isEnabled(new LoggingTraceCall());
    }

    @Override
    public LoggingContext getLoggingContext() {
        return this.context;
    }
}
