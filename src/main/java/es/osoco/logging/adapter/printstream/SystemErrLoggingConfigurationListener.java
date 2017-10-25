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

import es.osoco.logging.adapter.LoggingAdapterBuilderRegistry;
import es.osoco.logging.config.LoggingConfiguration;
import es.osoco.logging.config.LoggingConfigurationListener;

/**
 * Receives notifications of new {@link LoggingConfiguration}s. It creates
 * a builder if the configuration corresponds to {@code System.err}.
 */
@SuppressWarnings("unused")
public class SystemErrLoggingConfigurationListener
    implements LoggingConfigurationListener {

    @Override
    public void newLoggingConfigurationAvailable(final LoggingConfiguration config) {
        if (   (config instanceof PrintStreamLoggingConfiguration)
            && ("System.err".equals(config.getRegistryKey()))) {
            LoggingAdapterBuilderRegistry
                .getInstance()
                .put(
                    config.getRegistryKey(),
                    new SystemOutLoggingAdapterBuilder(
                        config.getRegistryKey(), (PrintStreamLoggingConfiguration) config));
        }
    }
}
