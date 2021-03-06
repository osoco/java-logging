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

import es.osoco.logging.adapter.AbstractLoggingAdapterBuilder;
import es.osoco.logging.adapter.LoggingAdapter;

import java.io.PrintStream;

/**
 * {@link LoggingAdapter} that delegates logging to a {@link PrintStream}.
 */
public class PrintStreamLoggingAdapterBuilder
    extends AbstractLoggingAdapterBuilder<PrintStreamLoggingConfiguration, PrintStreamLoggingAdapter> {

    /**
     * Creates a new {@code PrintStreamLoggingAdapterBuilder} for given key and {@link PrintStream}.
     * @param key the registry key.
     * @param config the {@link PrintStreamLoggingConfiguration}.
     */
    protected PrintStreamLoggingAdapterBuilder(final String key, final PrintStreamLoggingConfiguration config) {
        super(key, config);
    }

    @Override
    public PrintStreamLoggingAdapter build(final PrintStreamLoggingConfiguration config) {
        return new PrintStreamLoggingAdapter(config);
    }
}
