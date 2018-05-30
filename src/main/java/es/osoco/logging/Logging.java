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
     * Enables or disables the "error" level.
     * @param flag the flag.
     */
    void setErrorEnabled(boolean flag);

    /**
     * Logs a "warn" message.
     * @param msg the message.
     */
    void warn(String msg);

    /**
     * Enables or disables the "warn" level.
     * @param flag the flag.
     */
    void setWarnEnabled(boolean flag);

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
     * Enables or disables the "info" level.
     * @param flag the flag.
     */
    void setInfoEnabled(boolean flag);

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
     * Enables or disables the "debug" level.
     * @param flag the flag.
     */
    void setDebugEnabled(boolean flag);

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
     * Enables or disables the "trace" level.
     * @param flag the flag.
     */
    void setTraceEnabled(boolean flag);

    /**
     * Retrieves the {@link LoggingContext}.
     * @return such context.
     */
    LoggingContext getLoggingContext();
}
