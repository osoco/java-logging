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
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * A {@link Logging} composed of some preferred logging mechanisms, and fallback mechanisms should they fail.
 */
public class CompositeLogging
    implements Logging {

    @NonNull
    private LoggingContext context = new ThreadLocalLoggingContext();

    @NonNull
    private List<LoggingAdapter> preferred;

    @NonNull
    private List<LoggingAdapter> fallback;

    /**
     * Creates a new composite logging.
     * @param preferred the preferred adapters.
     * @param fallback the fallback adapter
     */
    public CompositeLogging(@NonNull final List<LoggingAdapter> preferred, @NonNull final List<LoggingAdapter> fallback) {
        this.preferred = preferred;
        this.fallback = fallback;
    }

    /**
     * Specifies the preferred adapters.
     * @param preferred the preferred logging.
     */
    protected final void immutableSetPreferred(@NonNull final List<LoggingAdapter> preferred) {
        this.preferred = preferred;
    }

    /**
     * Specifies the preferred adapters. Override me if necessary.
     * @param preferred the preferred logging.
     */
    @SuppressWarnings("unused")
    protected void setPreferred(@NonNull final List<LoggingAdapter> preferred) {
        immutableSetPreferred(preferred);
    }

    /**
     * Retrieves the preferred logging adapters.
     * @return such adapters.
     */
    @NonNull
    public List<LoggingAdapter> getPreferred() {
        return this.preferred;
    }

    /**
     * Specifies the fallback adapters.
     * @param fallback the fallback logging.
     */
    protected final void immutableSetFallback(@NonNull final List<LoggingAdapter> fallback) {
        this.fallback = fallback;
    }

    /**
     * Specifies the fallback adapters. Override me if necessary.
     * @param fallback the fallback logging.
     */
    @SuppressWarnings("unused")
    protected void setFallback(@NonNull final List<LoggingAdapter> fallback) {
        immutableSetFallback(fallback);
    }

    /**
     * Retrieves the fallback logging adapters.
     * @return such adapters.
     */
    @NonNull
    public List<LoggingAdapter> getFallback() {
        return this.fallback;
    }

    /**
     * Logs given msg, using a function interface.
     * @param msg the message to log.
     * @param loggingCallable the callable function.
     */
    protected void log(@NonNull final String msg, @NonNull final LoggingCall loggingCallable) {
        boolean fallbackNeeded = false;

        for (@NonNull final LoggingAdapter preferred: getPreferred()) {
            try {
                loggingCallable.log(preferred, msg);
            } catch (final Throwable error) {
                fallbackNeeded = true;
            }
        }

        if (fallbackNeeded) {
            for (@NonNull final LoggingAdapter fallback : getFallback()) {
                try {
                    loggingCallable.log(fallback, msg);
                } catch (final Throwable error) {
                }
            }
        }
    }

    /**
     * Logs given msg, using a function interface.
     * @param category the category.
     * @param msg the message to log.
     * @param loggingCallable the callable function.
     */
    protected void log(
        @NonNull final String category, @NonNull final String msg, @NonNull final LoggingCall loggingCallable) {
        boolean fallbackNeeded = false;

        for (@NonNull final LoggingAdapter preferred: getPreferred()) {
            try {
                loggingCallable.log(preferred, category, msg);
            } catch (final Throwable error) {
                fallbackNeeded = true;
            }
        }

        if (fallbackNeeded) {
            for (@NonNull final LoggingAdapter fallback : getFallback()) {
                try {
                    loggingCallable.log(fallback, category, msg);
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
    protected boolean isEnabled(@NonNull final LoggingCall loggingCallable) {
        boolean result = true;

        boolean fallbackNeeded = false;

        for (@NonNull final LoggingAdapter preferred: getPreferred()) {
            try {
                result = result && loggingCallable.isEnabled(preferred);
            } catch (@NonNull final Throwable error) {
                fallbackNeeded = true;
            }
        }

        if (fallbackNeeded) {
            for (@NonNull final LoggingAdapter fallback : getFallback()) {
                try {
                    result = result && loggingCallable.isEnabled(fallback);
                } catch (@NonNull final Throwable error) {
                }
            }
        }

        return result;
    }

    /**
     * Delegates the check regarding whether a level is enabled to given callable.
     * @param loggingCallable the function pointer.
     * @param category the category.
     * @return the outcome of the check.
     */
    protected boolean isEnabled(@NonNull final LoggingCall loggingCallable, @NonNull final String category) {
        boolean result = true;

        boolean fallbackNeeded = false;

        for (final LoggingAdapter preferred: getPreferred()) {
            try {
                result = result && loggingCallable.isEnabled(preferred, category);
            } catch (final Throwable error) {
                fallbackNeeded = true;
            }
        }

        if (fallbackNeeded) {
            for (final LoggingAdapter fallback : getFallback()) {
                try {
                    result = result && loggingCallable.isEnabled(fallback, category);
                } catch (final Throwable error) {
                }
            }
        }

        return result;
    }

    @Override
    public void error(@NonNull final String msg) {
        log(msg, new LoggingErrorCall());
    }

    @Override
    public void error(@NonNull final String category, @NonNull final String msg) {
        log(category, msg, new LoggingErrorCall());
    }

    @Override
    public boolean isErrorEnabled() {
        return isEnabled(new LoggingErrorCall());
    }

    @Override
    public boolean isErrorEnabled(@NonNull final String category) {
        return isEnabled(new LoggingErrorCall(), category);
    }

    @Override
    public void setErrorEnabled(final boolean flag) {
        for (@NonNull final LoggingAdapter preferred: getPreferred()) {
            preferred.setErrorEnabled(flag);
        }
    }

    @Override
    public void setErrorEnabled(@NonNull final String category, final boolean flag) {
        for (@NonNull final LoggingAdapter preferred: getPreferred()) {
            preferred.setErrorEnabled(category, flag);
        }
    }

    @Override
    public void warn(@NonNull final String msg) {
        log(msg, new LoggingWarnCall());
    }

    @Override
    public void warn(@NonNull final String category, @NonNull final String msg) {
        log(category, msg, new LoggingWarnCall());
    }

    @Override
    public boolean isWarnEnabled() {
        return isEnabled(new LoggingWarnCall());
    }

    @Override
    public boolean isWarnEnabled(@NonNull final String category) {
        return isEnabled(new LoggingWarnCall(), category);
    }

    @Override
    public void setWarnEnabled(final boolean flag) {
        for (@NonNull final LoggingAdapter preferred: getPreferred()) {
            preferred.setWarnEnabled(flag);
        }
    }

    @Override
    public void setWarnEnabled(@NonNull final String category, final boolean flag) {
        for (@NonNull final LoggingAdapter preferred: getPreferred()) {
            preferred.setWarnEnabled(category, flag);
        }
    }

    @Override
    public void info(@NonNull final String msg) {
        log(msg, new LoggingInfoCall());
    }

    @Override
    public void info(@NonNull final String category, @NonNull final String msg) {
        log(category, msg, new LoggingInfoCall());
    }

    @Override
    public boolean isInfoEnabled() {
        return isEnabled(new LoggingInfoCall());
    }

    @Override
    public boolean isInfoEnabled(@NonNull final String category) {
        return isEnabled(new LoggingInfoCall(), category);
    }

    @Override
    public void setInfoEnabled(final boolean flag) {
        for (@NonNull final LoggingAdapter preferred: getPreferred()) {
            preferred.setInfoEnabled(flag);
        }
    }

    @Override
    public void setInfoEnabled(@NonNull final String category, final boolean flag) {
        for (@NonNull final LoggingAdapter preferred: getPreferred()) {
            preferred.setInfoEnabled(category, flag);
        }
    }

    @Override
    public void debug(@NonNull final String msg) {
        log(msg, new LoggingDebugCall());
    }

    @Override
    public void debug(@NonNull final String category, @NonNull final String msg) {
        log(category, msg, new LoggingDebugCall());
    }

    @Override
    public boolean isDebugEnabled() {
        return isEnabled(new LoggingDebugCall());
    }

    @Override
    public boolean isDebugEnabled(@NonNull final String category) {
        return isEnabled(new LoggingDebugCall(), category);
    }

    @Override
    public void setDebugEnabled(final boolean flag) {
        for (@NonNull final LoggingAdapter preferred: getPreferred()) {
            preferred.setDebugEnabled(flag);
        }
    }

    @Override
    public void setDebugEnabled(@NonNull final String category, final boolean flag) {
        for (@NonNull final LoggingAdapter preferred: getPreferred()) {
            preferred.setDebugEnabled(category, flag);
        }
    }

    @Override
    public void trace(@NonNull final String msg) {
        log(msg, new LoggingTraceCall());
    }

    @Override
    public void trace(@NonNull final String category, @NonNull final String msg) {
        log(category, msg, new LoggingTraceCall());
    }

    @Override
    public boolean isTraceEnabled() {
        return isEnabled(new LoggingTraceCall());
    }

    @Override
    public boolean isTraceEnabled(@NonNull final String category) {
        return isEnabled(new LoggingTraceCall(), category);
    }

    @Override
    public void setTraceEnabled(final boolean flag) {
        for (@NonNull final LoggingAdapter preferred: getPreferred()) {
            preferred.setTraceEnabled(flag);
        }
    }

    @Override
    public void setTraceEnabled(@NonNull final String category, final boolean flag) {
        for (@NonNull final LoggingAdapter preferred: getPreferred()) {
            preferred.setTraceEnabled(category, flag);
        }
    }

    @Override
    @NonNull
    public LoggingContext getLoggingContext() {
        return this.context;
    }
}
