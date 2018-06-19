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
package es.osoco.logging;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides logging.
 */
public interface Logging {

    /**
     * Logs an "error" message.
     * @param msg the message.
     */
    void error(@NonNull String msg);

    /**
     * Logs an "error" message in given category.
     * @param category the category.
     * @param msg the message.
     */
    void error(@NonNull String category, @NonNull String msg);

    /**
     * Enables or disables the "error" level.
     * @param flag the flag.
     */
    void setErrorEnabled(boolean flag);

    /**
     * Enables or disables the "error" level for given category.
     * @param category the category.
     * @param flag the flag.
     */
    void setErrorEnabled(@NonNull final String category, boolean flag);

    /**
     * Checks whether "error" logging is enabled.
     * @return such information.
     */
    boolean isErrorEnabled();

    /**
     * Checks whether "error" logging is enabled for given category.
     * @param category the category.
     * @return such information.
     */
    boolean isErrorEnabled(@NonNull String category);

    /**
     * Logs a "warn" message.
     * @param msg the message.
     */
    void warn(@NonNull String msg);

    /**
     * Logs a "warn" message in given category.
     * @param category the category.
     * @param msg the message.
     */
    void warn(@NonNull String category, @NonNull String msg);

    /**
     * Enables or disables the "warn" level.
     * @param flag the flag.
     */
    void setWarnEnabled(boolean flag);

    /**
     * Enables or disables the "warn" level for given category.
     * @param category the category.
     * @param flag the flag.
     */
    void setWarnEnabled(@NonNull String category, boolean flag);

    /**
     * Checks whether "warn" logging is enabled.
     * @return such information.
     */
    boolean isWarnEnabled();

    /**
     * Checks whether "warn" logging is enabled for given category.
     * @param category the category.
     * @return such information.
     */
    boolean isWarnEnabled(@NonNull String category);

    /**
     * Logs an "info" message.
     * @param msg the message.
     */
    void info(@NonNull String msg);

    /**
     * Logs an "info" message in given category.
     * @param category the category.
     * @param msg the message.
     */
    void info(@NonNull String category, @NonNull String msg);

    /**
     * Enables or disables the "info" level.
     * @param flag the flag.
     */
    void setInfoEnabled(boolean flag);

    /**
     * Enables or disables the "info" level for given category.
     * @param category the category.
     * @param flag the flag.
     */
    void setInfoEnabled(@NonNull String category, boolean flag);

    /**
     * Checks whether "info" logging is enabled.
     * @return such information.
     */
    boolean isInfoEnabled();

    /**
     * Checks whether "info" logging is enabled for given category.
     * @param category the category.
     * @return such information.
     */
    boolean isInfoEnabled(@NonNull String category);

    /**
     * Logs a "debug" message.
     * @param msg the message.
     */
    void debug(@NonNull String msg);

    /**
     * Logs a "debug" message in given category.
     * @param category the category.
     * @param msg the message.
     */
    void debug(@NonNull String category, @NonNull String msg);

    /**
     * Enables or disables the "debug" level.
     * @param flag the flag.
     */
    void setDebugEnabled(boolean flag);

    /**
     * Enables or disables the "debug" level, for given category.
     * @param category the category.
     * @param flag the flag.
     */
    void setDebugEnabled(@NonNull String category, boolean flag);

    /**
     * Checks whether "debug" logging is enabled.
     * @return such information.
     */
    boolean isDebugEnabled();

    /**
     * Checks whether "debug" logging is enabled for given category.
     * @param category the category.
     * @return such information.
     */
    boolean isDebugEnabled(@NonNull String category);

    /**
     * Logs a "trace" message.
     * @param msg the message.
     */
    void trace(@NonNull String msg);

    /**
     * Logs a "trace" message in given category.
     * @param category the category.
     * @param msg the message.
     */
    void trace(@NonNull String category, @NonNull String msg);

    /**
     * Enables or disables the "trace" level.
     * @param flag the flag.
     */
    void setTraceEnabled(boolean flag);

    /**
     * Enables or disables the "trace" level for given category.
     * @param category the category.
     * @param flag the flag.
     */
    void setTraceEnabled(@NonNull String category, boolean flag);

    /**
     * Checks whether "trace" logging is enabled.
     * @return such information.
     */
    boolean isTraceEnabled();

    /**
     * Checks whether "trace" logging is enabled for given category.
     * @param category the category.
     * @return such information.
     */
    boolean isTraceEnabled(@NonNull String category);

    /**
     * Retrieves the {@link LoggingContext}.
     * @return such context.
     */
    @NonNull
    LoggingContext getLoggingContext();
}
