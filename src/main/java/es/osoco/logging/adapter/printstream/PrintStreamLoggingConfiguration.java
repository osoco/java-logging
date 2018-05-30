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

import es.osoco.logging.config.LoggingConfiguration;

import java.io.PrintStream;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Logging configuration based on {@link PrintStream}s.
 */
@ToString
@EqualsAndHashCode
public class PrintStreamLoggingConfiguration
    implements LoggingConfiguration {

    /**
     * The configuration key.
     */
    private String key;

    /**
     * The print stream to use.
     */
    private PrintStream stream;

    /**
     * Creates a logging configuration for given {@link PrintStream}.
     * @param key the key.
     * @param stream such stream.
     */
    public PrintStreamLoggingConfiguration(final String key, final PrintStream stream) {
        immutableSetRegistryKey(key);
        immutableSetStream(stream);
    }

    /**
     * Specifies the registry key.
     * @param key such key.
     */
    protected final void immutableSetRegistryKey(final String key) {
        this.key = key;
    }

    /**
     * Specifies the registry key. Override me if necessary.
     * @param key such key.
     */
    @SuppressWarnings("unused")
    protected void setRegistryKey(final String key) {
        immutableSetRegistryKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRegistryKey() {
        return this.key;
    }

    /**
     * Sets the stream to use.
     * @param stream the stream.
     */
    protected final void immutableSetStream(final PrintStream stream) {
        this.stream = stream;
    }

    /**
     * Sets the stream to use. Override me if necessary.
     * @param stream the stream.
     */
    @SuppressWarnings("unused")
    protected void setStream(final PrintStream stream) {
        immutableSetStream(stream);
    }

    /**
     * Retrieves the {@link PrintStream}.
     * @return such instance.
     */
    protected final PrintStream immutableGetPrintStream() {
        return this.stream;
    }

    /**
     * Retrieves the {@link PrintStream}. Override me if necessary.
     * @return such instance.
     */
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
