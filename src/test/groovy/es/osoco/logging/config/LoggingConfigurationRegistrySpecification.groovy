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
package es.osoco.logging.config

import es.osoco.logging.adapter.LoggingAdapterBuilderRegistry
import es.osoco.logging.adapter.printstream.PrintStreamLoggingConfiguration
import spock.lang.Specification

class LoggingConfigurationRegistrySpecification
        extends Specification {

    def "LoggingConfigurationRegistry provides already-existing LoggingConfiguration instances"() {
        setup:
        def registry = LoggingConfigurationRegistry.getInstance()
        def config = new PrintStreamLoggingConfiguration("my-system-out", System.out)
        registry.put(config.getRegistryKey(), config)

        expect:
        registry.get(config.getRegistryKey()) == config
    }

    def "LoggingConfigurationRegistry notifies SystemOutLoggingAdapterBuilder when the System.out-LoggingConfiguration is published"() {
        setup:
        def configRegistry = LoggingConfigurationRegistry.getInstance()
        def config = new PrintStreamLoggingConfiguration("System.out", System.out)
        configRegistry.put(config.getRegistryKey(), config)
        def builderRegistry = LoggingAdapterBuilderRegistry.getInstance()

        expect:
        builderRegistry.get(config.getRegistryKey()) != null

    }
}
