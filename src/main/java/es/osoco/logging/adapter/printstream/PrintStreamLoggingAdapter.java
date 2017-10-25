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
package es.osoco.logging.adapter.printstream;

import es.osoco.logging.adapter.AbstractLoggingAdapter;
import es.osoco.logging.adapter.LoggingAdapter;

import java.io.PrintStream;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * {@link LoggingAdapter} for {@link PrintStream}s, such as {@code System.out}.
 */
@ToString
@EqualsAndHashCode(callSuper=true)
public class PrintStreamLoggingAdapter
    extends AbstractLoggingAdapter<PrintStreamLoggingConfiguration> {

    /**
     * Creates a logging configuration for given {@link PrintStreamLoggingConfiguration}.
     * @param config such configuration.
     */
    public PrintStreamLoggingAdapter(final PrintStreamLoggingConfiguration config) {
        super(config);
    }

    /**
     * Logs to the underlying {@link PrintStream}.
     * @param msg the message to log.
     */
    protected void logToPrintStream(final String msg) {
        getLoggingConfiguration().getPrintStream().println(msg);
    }

    @Override
    protected void logError(final String msg) {
        logToPrintStream(msg);
    }

    @Override
    protected void logWarn(final String msg) {
        logToPrintStream(msg);
    }

    @Override
    protected void logInfo(final String msg) {
        logToPrintStream(msg);
    }

    @Override
    protected void logDebug(final String msg) {
        logToPrintStream(msg);
    }

    @Override
    protected void logTrace(final String msg) {
        logToPrintStream(msg);
    }
}
