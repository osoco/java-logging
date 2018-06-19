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

import es.osoco.logging.adapter.AbstractLoggingConfiguration;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.PrintStream;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Logging configuration based on {@link PrintStream}s.
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public class PrintStreamLoggingConfiguration
    extends AbstractLoggingConfiguration {

    /**
     * The print stream to use.
     */
    private PrintStream stream;

    /**
     * Creates a logging configuration for given {@link PrintStream}.
     * @param key the key.
     * @param stream such stream.
     */
    public PrintStreamLoggingConfiguration(@NonNull final String key, @NonNull final PrintStream stream) {
        super(key);
        this.stream = stream;
    }

    /**
     * Sets the stream to use.
     * @param stream the stream.
     */
    protected final void immutableSetStream(@NonNull final PrintStream stream) {
        this.stream = stream;
    }

    /**
     * Sets the stream to use. Override me if necessary.
     * @param stream the stream.
     */
    @SuppressWarnings("unused")
    protected void setStream(@NonNull final PrintStream stream) {
        immutableSetStream(stream);
    }

    /**
     * Retrieves the {@link PrintStream}.
     * @return such instance.
     */
    @NonNull
    protected final PrintStream immutableGetPrintStream() {
        return this.stream;
    }

    /**
     * Retrieves the {@link PrintStream}. Override me if necessary.
     * @return such instance.
     */
    @NonNull
    public PrintStream getPrintStream() {
        return immutableGetPrintStream();
    }

    @Override
    public boolean isWarnEnabledByDefault() {
        return (System.err == null) || (!System.err.equals(getPrintStream()));
    }

    @Override
    public boolean isInfoEnabledByDefault() {
        return (System.err == null) || (!System.err.equals(getPrintStream()));
    }
}
