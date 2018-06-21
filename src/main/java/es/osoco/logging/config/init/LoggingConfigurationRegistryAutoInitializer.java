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
package es.osoco.logging.config.init;

import es.osoco.logging.adapter.jul.JulLoggingConfiguration;
import es.osoco.logging.adapter.printstream.PrintStreamLoggingConfiguration;
import es.osoco.logging.config.LoggingConfiguration;
import es.osoco.logging.annotations.LoggingConfigurationProducer;

/**
 * Automatically initializes System.err and System.out-based logging configurations.
 */
@SuppressWarnings("unused")
public class LoggingConfigurationRegistryAutoInitializer {

    /**
     * Required public constructor (gets instantiated via reflection).
     */
    public LoggingConfigurationRegistryAutoInitializer() {
        super();
    }

    @SuppressWarnings("unused")
    @LoggingConfigurationProducer(key = "System.out")
    public LoggingConfiguration produceSystemOutLoggingConfiguration() {
        return new PrintStreamLoggingConfiguration("System.out", System.out);
    }

    @SuppressWarnings("unused")
    @LoggingConfigurationProducer(key = "System.err")
    public LoggingConfiguration produceSystemErrLoggingConfiguration() {
        return new PrintStreamLoggingConfiguration("System.err", System.err);
    }

    @SuppressWarnings("unused")
    @LoggingConfigurationProducer(key = "java.util.logging")
    public LoggingConfiguration produceJdk4LoggingConfiguration() {
        return new JulLoggingConfiguration();
    }
}
