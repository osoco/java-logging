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
package es.osoco.logging.config.init

import es.osoco.logging.config.LoggingConfigurationRegistry
import spock.lang.Specification
import spock.util.mop.ConfineMetaClassChanges

@ConfineMetaClassChanges([System])
class LoggingConfigurationRegistryAutoInitializerSpecification
        extends Specification {

    def "The AutoInitializer publishes the System.out configuration" () {
        setup:
        LoggingConfigurationRegistry registry = LoggingConfigurationRegistry.getInstance()

        expect:
        registry.get("System.out") != null
    }

    def "The AutoInitializer publishes the System.err configuration" () {
        setup:
        LoggingConfigurationRegistry registry = LoggingConfigurationRegistry.getInstance()

        expect:
        registry.get("System.err") != null
    }
}
